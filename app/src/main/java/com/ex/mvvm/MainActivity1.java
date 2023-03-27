package com.ex.mvvm;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity1 extends AppCompatActivity {



    /****
     *
     *
     * 在这个示例中，我们创建了一个 NonStickyLiveData 实例，并通过 observe() 方法添加了一个观察者。
     * 然后，我们模拟数据更改，通过调用 setValue() 方法设置 LiveData 的值为 "Data 1"。由于 NonStickyLiveData 类的实现，
     * 这个值不会立即传递给观察者，而是在观察者重新处于活动状态时传递。
     * 我们使用 call() 方法来手动触发更新。最后，我们再次调用 setValue()
     * 方法来设置 LiveData 的值为 "Data 2"，并等待观察者接收该值。
     *
     * 当我们运行这个示例时，我们可以看到 "LiveData value: Data 1" 和 "LiveData value: Data 2" 分别在 2 秒和 3
     * 秒后输出到日志中。这表明 NonStickyLiveData 类已成功解决了数据倒灌问题，并正确传递 LiveData 的值给观察者。
     *
     *
     * */

    private NonStickyLiveData<String> mLiveData = new NonStickyLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("MainActivity", "LiveData value: " + s);
            }
        });


        mLiveData.observe(MainActivity1.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("MainActivity", "LiveData2 value: " + s);
            }
        });


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        },1000);

        // Simulate data changes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLiveData.setValue("Data 1");
            }
        }, 2000);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLiveData.call();
            }
        }, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLiveData.setValue("Data 2");
            }
        }, 8000);
    }
}

