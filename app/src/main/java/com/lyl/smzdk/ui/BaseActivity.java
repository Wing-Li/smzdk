package com.lyl.smzdk.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lyl.smzdk.R;
import com.lyl.smzdk.view.ActionBar;

/**
 * Author: lyl
 * Date Created : 2017/11/2.
 */
public class BaseActivity extends AppCompatActivity {

    public Context mContent;
    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = this;
        mActivity = this;

    }

    public void setMyActionBar(int resId) {
        ActionBar actionBar = findViewById(R.id.actionbar);
        actionBar.setModelBack(resId, mActivity);
    }

    public void showT(int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public void showT(String resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

}
