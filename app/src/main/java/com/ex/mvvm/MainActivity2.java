package com.ex.mvvm;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MainActivity2 extends AppCompatActivity {


    // 没有做任何处理的 livedata
     MutableLiveData<String> datas = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setValue 和 postValue  之间的区别
//         setValue 是存在于主线程的，   postvalue 是子线程中的

        //


        /***
         *     这里只有一个observe ，所以不会导致  数据倒灌问题。
         * */
        datas.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("this data is -->",s);
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.observe(MainActivity2.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Log.e("this data2 is -->", s);
                    }
                });
            }
        }, 1200);


      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              datas.setValue("first send！！");    // set 和 post 之间的区别，
          }
      },5000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.setValue("second send！！");
            }
        },8000);



    }


    @Override
    protected void onResume() {
        super.onResume();
        datas.setValue("send 0！！");
    }
}
