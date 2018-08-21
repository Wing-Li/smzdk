package com.lyl.smzdk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.dao.model.UserInfoModel;
import com.lyl.smzdk.utils.NetUtil;
import com.lyl.smzdk.view.TransitionHelper;
import com.lyl.smzdk.view.loading.LoadingDialog;

/**
 * Author: lyl
 * Date Created : 2017/11/8.
 */
public class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected Context mContext;
    protected LoadingDialog mLoadingDialog;
    protected UserInfoModel mUserInfoModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        mUserInfoModel = new UserInfoModel(mContext);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MyApp.isWifi = NetUtil.isWifi(getApplicationContext());
    }

    protected void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int res) {
        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转页面
     *
     * @param intent
     * @param includeStatusBar 如果是false，状态栏将不会被添加为过渡参与者
     */
    public void skipActivity(Intent intent, boolean includeStatusBar) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    /**
     * 显示加载圈
     */
    protected void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏加载进度圈
     */
    protected void hideDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 透明状态栏 和 底部按键透明
     */
    protected void translucentStatusAndNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 底部按键透明
     */
    protected void translucentNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void t(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 防止 String 为 null
     *
     * @param s
     * @return
     */
    public String FS(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        } else {
            return s;
        }
    }
}
