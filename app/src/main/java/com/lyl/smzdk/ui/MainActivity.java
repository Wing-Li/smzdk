package com.lyl.smzdk.ui;

import android.os.Bundle;

import com.lyl.smzdk.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMyActionBar(R.string.home);
    }
}
