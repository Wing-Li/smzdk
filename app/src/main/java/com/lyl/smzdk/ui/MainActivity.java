package com.lyl.smzdk.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lyl.smzdk.R;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.event.MainLoadDataEvent;
import com.lyl.smzdk.ui.news.MainFragment;
import com.lyl.smzdk.ui.search.SearchFragment;
import com.lyl.smzdk.ui.shop.ShopFragment;
import com.lyl.smzdk.ui.user.UserFragment;
import com.lyl.smzdk.ui.video.VideoFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jzvd.JZVideoPlayer;

/**
 * Author: lyl
 * Date Created : 2017/11/8.
 */
public class MainActivity extends BaseActivity {

    BottomBar mainBottombar;

    private MainFragment mainFragment;
    private VideoFragment videoFragment;
    private SearchFragment searchFragment;
    private ShopFragment shopFragment;
    private UserFragment userFragment;

    private Fragment oldFragment;

    /**
     * 是否第一次进入页面。
     * 因为，Bottombar.setOnTabSelectListener 第一次就会被执行。
     */
    private boolean isFristInPage = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initStatusbar();
        initMainContent();
        initBottombar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);//订阅
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    private void initStatusbar() {
        ImmersionBar.with(this).init();
    }

    private void initMainContent() {
        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, mainFragment).commit();
        oldFragment = mainFragment;
    }

    //  —————————————————— ↓底部 Bar 处理↓ —————————————————————————

    @SuppressLint("ClickableViewAccessibility")
    private void initBottombar() {
        mainBottombar = findViewById(R.id.main_bottombar);
        mainBottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (isFristInPage) {
                    isFristInPage = false;
                    return;
                }
                switch (tabId) {
                    case R.id.tab_news: { // 资讯
                        if (mainFragment == null) {
                            mainFragment = new MainFragment();
                        }
                        toFragment(mainFragment);
                        break;
                    }
                    case R.id.tab_video: { // 视频
                        if (videoFragment == null) {
                            videoFragment = new VideoFragment();
                        }
                        toFragment(videoFragment);
                        break;
                    }
                    case R.id.tab_search: { // 搜索
                        if (searchFragment == null) {
                            searchFragment = new SearchFragment();
                        }
                        toFragment(searchFragment);
                        break;
                    }
                    case R.id.tab_shop: { // 好物
                        if (shopFragment == null) {
                            shopFragment = new ShopFragment();
                        }
                        toFragment(shopFragment);
                        break;
                    }
                    case R.id.tab_user: { // 个人
                        if (userFragment == null) {
                            userFragment = new UserFragment();
                        }
                        toFragment(userFragment);
                        break;
                    }
                }
            }
        });

        mainBottombar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_news: {
                        // 在 MainFragment 中 刷新数据
                        EventBus.getDefault().post(new MainLoadDataEvent(1));
                        break;
                    }
                }
            }
        });
    }

    /**
     * 切换Fragment
     *
     * @param to 下一个Fragment页面
     */
    private void toFragment(Fragment to) {
        if (to == oldFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android
                .R.anim.fade_in, android.R.anim.fade_out);
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(oldFragment).add(R.id.main_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(oldFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        oldFragment = to;
    }

    /**
     * 当用户向上滑动时，隐藏底部的 bar 以得到更多的阅读空间
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideBottombar(HideBottombarEvent event) {
        if (mainBottombar.getVisibility() == View.VISIBLE && event.isHide == true) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_top);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mainBottombar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mainBottombar.startAnimation(animation);
        } else if (mainBottombar.getVisibility() == View.GONE && event.isHide == false) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_bottom);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mainBottombar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mainBottombar.startAnimation(animation);
        }
    }

    //  —————————————————— ↑底部 Bar 处理↑ —————————————————————————

    @Override
    protected void onPause() {
        super.onPause();
        // 屏幕关闭时，停止所有正在播放的视频
        JZVideoPlayer.releaseAllVideos();
    }

    private long time = 0;

    @Override
    public void onBackPressed() {
        // 如果视频正在全屏播放，则退出全屏
        if (JZVideoPlayer.backPress()) {
            return;
        }

        // 双击 返回键 退出 App
        if (System.currentTimeMillis() - time <= 2000) {
            finish();
        } else {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), R.string.exit_app, Toast.LENGTH_SHORT).show();
            return;
        }

        super.onBackPressed();
    }
}
