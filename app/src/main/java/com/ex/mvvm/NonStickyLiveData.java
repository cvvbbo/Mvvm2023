package com.ex.mvvm;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 *
 *    通过这样的方式能够每次发送setvalue 方法的时候 ，修改这次
 *
 *
 * */
public class NonStickyLiveData<T> extends MutableLiveData<T> {

    //  这个值和下面的  compareAndSet 方法是有关的，  expect 和这里的值，如果相等才会修改成后面的值，
    //  ，则不会修改成后面的值。
    private AtomicBoolean mPending = new AtomicBoolean(false);
    private final Handler mHandler = new Handler(Looper.getMainLooper());


    // 通过这样的方式能够每次发送setvalue 方法的时候 ，修改这次
    private boolean mExpect = true;  // 预期值
    private boolean mpUdata = false;  // 原值

    @MainThread
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {
        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if (mPending.compareAndSet(mExpect, mpUdata)) {   // 通过传递进来的值
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(final T t) {
//        mPending.set(true);   // 如果这里设置了，外面的成员变量就不起作用了。 expect 总要和某个值做一个对比。
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                NonStickyLiveData.super.setValue(t);
            }
        });
    }


    //  这个方法没什么用。。。
//    @MainThread
//    public void call() {
//        setValue(null);
//    }


    public void setLastCompareAndSet(boolean expect, boolean updata) {
        mExpect = expect;
        mpUdata = updata;
    }
}
