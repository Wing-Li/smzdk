package com.lyl.smzdk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lyl.smzdk.R;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.view.ActionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dongjunkun on 2016/2/2.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    private BaseActivity holder;

    protected ActionBar mActionBar;
//    protected Subscription subscription;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            holder = (BaseActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        unbinder = ButterKnife.bind(this, rootView);

        mActionBar = rootView.findViewById(R.id.actionbar);
        return rootView;
    }

    protected abstract int getLayoutResource();


    public BaseActivity getHolder() {
        if (holder == null) {
            throw new IllegalArgumentException("the acticity must be extends BaseActivity");
        }
        return holder;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unsubscribe();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }

    protected void unsubscribe() {
//        if (subscription != null && !subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//            subscription = null;
//        }
    }

    protected void showToast(String str) {
        Toast.makeText(getHolder().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int res) {
        Toast.makeText(getHolder().getApplicationContext(), res, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置状态栏的颜色
     */
    protected void setStatusBarColor(int resId) {
        mActionBar.setBackgroundColor(ContextCompat.getColor(getHolder(), resId));

        ImmersionBar.with(this).transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .statusBarColor(resId)     //状态栏颜色，不写默认透明色
//                .navigationBarColor(resId) //导航栏颜色，不写默认黑色
//                .barColor(resId)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
//                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
//                .titleBarMarginTop(mActionBar)     //解决状态栏和布局重叠问题，任选其一
//                .statusBarView(mActionBar)  //解决状态栏和布局重叠问题，任选其一
                .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
                .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                .reset()  //重置所以沉浸式参数
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();  //必须调用方可沉浸式

    }

    /**
     * 当用户向上滑动时，隐藏底部的 bar 以得到更多的阅读空间
     */
    protected RecyclerView.OnScrollListener mOnScrollHideBottombarListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy >= 25) {// 手指向上滚动
                EventBus.getDefault().post(new HideBottombarEvent(true));
            } else if (dy <= -25) {// 手指向下滚动
                EventBus.getDefault().post(new HideBottombarEvent(false));
            }
            super.onScrolled(recyclerView, dx, dy);
        }
    };
}
