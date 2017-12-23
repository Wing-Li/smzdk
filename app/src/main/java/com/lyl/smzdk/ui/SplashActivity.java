package com.lyl.smzdk.ui;

import android.content.Intent;
import android.os.Bundle;

import com.lyl.smzdk.utils.MyUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!MyUtils.isDev()){
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 注意, 这里并没有setContentView, 单纯只是用来跳转到相应的Activity.
        // 目的是减少首屏渲染
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
