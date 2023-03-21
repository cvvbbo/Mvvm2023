package com.ex.mvvm;


import androidx.lifecycle.MutableLiveData;

public class MyViewModel extends BaseViewModel {

    private MutableLiveData<String> data = new MutableLiveData<>();

    public MutableLiveData<String> getData() {
        return data;
    }

    public void loadData() {
        setIsLoading(true);
        // 执行数据加载逻辑
        // ...

        // 加载完成后更新data的值
        data.setValue("加载完成的数据");

        setIsLoading(false);
    }

        // 上面那种方法的 平替。(可以理解为模拟网络请求)
//    public void loadData() {
//        setIsLoading(true);
//
//        // Simulate loading data from network
//        new Handler().postDelayed(() -> {
//            data.setValue("Data loaded from network");
//            setIsLoading(false);
//        }, 3000);
//    }

    // 可以添加其他需要处理的LiveData对象和方法
}

