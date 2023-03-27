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

    private AtomicBoolean mPending = new AtomicBoolean(true);
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
                if (mPending.compareAndSet(mExpect, mpUdata)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(final T t) {
        mPending.set(true);
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
