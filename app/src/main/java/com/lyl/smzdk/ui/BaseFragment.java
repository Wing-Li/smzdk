package com.lyl.smzdk.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lyl.smzdk.R;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.view.ActionBar;
import com.lyl.smzdk.view.TransitionHelper;
import com.lyl.smzdk.view.loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dongjunkun on 2016/2/2.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected ActionBar mActionBar;
    protected LoadingDialog mLoadingDialog;

    private BaseActivity holder;
    private Unbinder unbinder;

    private ImmersionBar mImmersionBar;

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
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) mImmersionBar.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        // StatusBarCompat.compat(getHolder(), resId);

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
                .statusBarColor(resId)//
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
            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getHolder(), false);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getHolder(), pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
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

    /**
     * 显示加载圈
     */
    protected void showDialog(){
        if (mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(getHolder());
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

}
