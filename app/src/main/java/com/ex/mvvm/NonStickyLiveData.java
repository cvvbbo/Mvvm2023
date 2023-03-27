package com.ex.mvvm;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class NonStickyLiveData<T> extends MutableLiveData<T> {

    private AtomicBoolean mPending = new AtomicBoolean(false);
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @MainThread
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {

        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if (mPending.compareAndSet(true, false)) {
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

    @MainThread
    public void call() {
        setValue(null);
    }
}
