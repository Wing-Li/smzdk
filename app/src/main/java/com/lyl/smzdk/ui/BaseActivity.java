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
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.R;
import com.lyl.smzdk.utils.NetUtil;
import com.lyl.smzdk.view.TransitionHelper;
import com.lyl.smzdk.view.loading.LoadingDialog;

/**
 * Author: lyl
 * Date Created : 2017/11/8.
 */
public class BaseActivity extends AppCompatActivity {

    public Activity mActivity;
    public Context mContext;
    protected LoadingDialog mLoadingDialog;

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        setStatusBar();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) mImmersionBar.destroy(); //必须调用该方法，防止内存泄漏
    }

    private void setStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .statusBarColor(R.color.bg_gary)//
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
                .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                .reset()  //重置所以沉浸式参数
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();  //必须调用方可沉浸式
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
    protected void showDialog(){
        if (mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(mContext);
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏加载进度圈
     */
    protected void hideDialog(){
        if (mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
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
