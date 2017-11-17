package com.lyl.smzdk.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Author: lyl
 * Date Created : 2017/11/8.
 */
public class BaseActivity extends AppCompatActivity {

    public Activity mActivity;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
    }

    protected void showToast(String str) {
        Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int res) {
        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
    }
}
