package com.ex.mvvm;

import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BActivity extends BaseActivity<MyViewModel> {

    private TextView textView;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my;
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        // todo 这里的初始化数据是自己的逻辑 和 别人无关  ( 重要)
       // viewModel.loadData();
    }

    @Override
    protected Class<MyViewModel> getViewModelClass() {
        return MyViewModel.class;
    }

    @Override
    protected void initViews() {
        textView = findViewById(R.id.text_view);
    }

    @Override
    protected void initObservers() {
        /***
         *    如果不在   initViewModel  方法中初始化， viewModel.loadData(); 下面的一行也不会走 。
         *
         *
         * */
        viewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String data) {
                textView.setText(data);
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {


                // 其实这里可以来一个类似loading的东西来代替下面那种  isLoading 的写法。 而这个 progressBar 可以是在 这个activty 中的xml布局文件中的。
//                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);


                if (isLoading) {
                    // 显示加载中视图
                } else {
                    // 隐藏加载中视图
                }
            }
        });
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                // 显示错误消息
                Log.e("aaa-->","error");
            }
        });
    }
}

