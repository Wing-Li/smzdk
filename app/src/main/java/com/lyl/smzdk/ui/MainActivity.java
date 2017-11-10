package com.lyl.smzdk.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.lyl.smzdk.R;
import com.lyl.smzdk.event.MainEvent;
import com.lyl.smzdk.ui.news.MainFragment;
import com.lyl.smzdk.ui.search.SearchFragment;
import com.lyl.smzdk.ui.shop.ShopFragment;
import com.lyl.smzdk.ui.user.UserFragment;
import com.lyl.smzdk.ui.video.VideoFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;

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

    private void initStatusbar() {
        ImmersionBar.with(this).init();
    }

    private void initMainContent() {
        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, mainFragment).commit();
        oldFragment = mainFragment;
    }

    //  —————————————————— ↓底部 Bar 处理↓ —————————————————————————

    private void initBottombar() {
        mainBottombar = findViewById(R.id.main_bottombar);
        mainBottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (isFristInPage){
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
                switch (tabId){
                    case R.id.tab_news:{
                        // 在 MainFragment 中 刷新数据
                        EventBus.getDefault().post(new MainEvent(1));
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

    //  —————————————————— ↑底部 Bar 处理↑ —————————————————————————

    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time <= 2000) {
                finish();
            } else {
                time = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), R.string.exit_app, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
