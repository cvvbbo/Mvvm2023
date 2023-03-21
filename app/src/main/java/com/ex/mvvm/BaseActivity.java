package com.ex.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    protected T viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        initViewModel();
        initViews();
        initObservers();
    }

    protected abstract int getLayoutResourceId();

    protected abstract Class<T> getViewModelClass();

    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
    }

    protected abstract void initViews();

    protected abstract void initObservers();
}
