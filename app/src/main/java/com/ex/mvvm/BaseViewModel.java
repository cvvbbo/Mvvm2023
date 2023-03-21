package com.ex.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    protected void setError(String error) {
        errorMessage.setValue(error);
    }

    protected void setError(Throwable error) {
        errorMessage.setValue(error.getMessage());
    }
}

