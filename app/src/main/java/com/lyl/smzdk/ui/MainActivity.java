package com.lyl.smzdk.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lyl.smzdk.BuildConfig;
import com.lyl.smzdk.R;
import com.lyl.smzdk.dao.model.UserInfoModel;
import com.lyl.smzdk.event.HideBottombarEvent;
import com.lyl.smzdk.event.MainLoadDataEvent;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.UploadFileUtils;
import com.lyl.smzdk.network.entity.myapi.BaseCallBack;
import com.lyl.smzdk.network.entity.myapi.User;
import com.lyl.smzdk.network.imp.MyApiImp;
import com.lyl.smzdk.ui.images.ImagesFragment;
import com.lyl.smzdk.ui.main.MainFragment;
import com.lyl.smzdk.ui.search.SearchFragment;
import com.lyl.smzdk.ui.user.UserFragment;
import com.lyl.smzdk.ui.video.VideoFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * Author: lyl
 * Date Created : 2017/11/8.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.main_bottombar)
    PageNavigationView mainBottombar;

    private MainFragment mainFragment;
    private VideoFragment videoFragment;
    private SearchFragment searchFragment;
    private ImagesFragment imageFragment;
    private UserFragment userFragment;

    private Fragment oldFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initMainContent();
        initBottombar();
        updateUserInfo();
        uploadToken();
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

    /**
     * 设置主页面的布局
     */
    private void initMainContent() {
        // 设置空过状态栏
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainContent.getLayoutParams();
        layoutParams.setMargins(0, result, 0, 0);
        mainContent.setLayoutParams(layoutParams);

        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, mainFragment).commit();
        oldFragment = mainFragment;
    }

    //  —————————————————— ↓底部 Bar 处理↓ —————————————————————————

    private PageNavigationView.MaterialBuilder addBottombarItem(PageNavigationView.MaterialBuilder materialBuilder,//
                                                                int icon, int title, int color) {
        return materialBuilder.addItem(icon, getString(title), ContextCompat.getColor(mContext, color));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initBottombar() {
        PageNavigationView.MaterialBuilder material = mainBottombar.material();
        material.setDefaultColor(ContextCompat.getColor(mContext, R.color.gary));

        addBottombarItem(material, R.drawable.ic_description_black_24dp, R.string.news_title, R.color.main_primary);
        addBottombarItem(material, R.drawable.ic_video_library_black_24dp, R.string.video_title, R.color.video_primary);
        addBottombarItem(material, R.drawable.ic_link_black_24dp, R.string.search_title, R.color.search_primary);
        addBottombarItem(material, R.drawable.ic_shopping_cart_black_24dp, R.string.images_title, R.color.images_accent);
        addBottombarItem(material, R.drawable.ic_account_box_black_24dp, R.string.user_title, R.color.user_accent);

        NavigationController mNavigationController = material.setMode(MaterialMode.CHANGE_BACKGROUND_COLOR)
                //这里可以设置样式模式，总共可以组合出4种效果
                .build();

        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                switch (index) {
                    case 0: // 咨询
                        if (mainFragment == null) {
                            mainFragment = new MainFragment();
                        }
                        toFragment(mainFragment);
                        break;
                    case 1: // 视频
                        if (videoFragment == null) {
                            videoFragment = new VideoFragment();
                        }
                        toFragment(videoFragment);
                        break;
                    case 2: // 搜索
                        if (searchFragment == null) {
                            searchFragment = new SearchFragment();
                        }
                        toFragment(searchFragment);
                        break;
                    case 3: // 图集
                        if (imageFragment == null) {
                            imageFragment = new ImagesFragment();
                        }
                        toFragment(imageFragment);
                        break;
                    case 4: // 个人
                        if (userFragment == null) {
                            userFragment = new UserFragment();
                        }
                        toFragment(userFragment);
                        break;
                }
            }

            @Override
            public void onRepeat(int index) {
                switch (index) {
                    case 0: // 咨询，连续点击两下
                        // 在 MainFragment 中 刷新数据
                        EventBus.getDefault().post(new MainLoadDataEvent(1));
                        break;
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

        FragmentTransaction transaction = getSupportFragmentManager()//
                .beginTransaction();//
//                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
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
        if (mainBottombar.getVisibility() == View.VISIBLE && event.isHide == true) {// 必须写 == true，否则每次都会出现隐藏
            hideBar();
        } else if (mainBottombar.getVisibility() == View.GONE && event.isHide == false) {
            showBar();
        }
    }

    /**
     * 隐藏底部 Bar
     */
    private void hideBar() {
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
    }

    /**
     * 显示底部 Bar
     */
    private void showBar() {
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

    //  —————————————————— ↑底部 Bar 处理↑ —————————————————————————

    //  —————————————————— ↓每次登陆更新用户信息↓ —————————————————————————

    private void updateUserInfo() {
        long id = new UserInfoModel(mContext).getId();
        if (0 != id) {
            Observable<BaseCallBack<User>> user = Network.getMyApi().getUser(id);
            new MyApiImp<User>().request(user, new MyApiImp.NetWorkCallBack<User>() {
                @Override
                public void onSuccess(User obj) {
                    // 保存用户信息到配置文件
                    UserInfoModel userInfoModel = new UserInfoModel(getApplicationContext());
                    userInfoModel.save(obj);
                }

                @Override
                public void onFail(int code, String msg) {
                }
            });
        }
    }

    //  —————————————————— ↑每次登陆更新用户信息↑ —————————————————————————

    private void uploadToken() {
        Observable<String> token = Network.getMyApi().token(BuildConfig.QiniuAK, BuildConfig.QiniuSK, BuildConfig.QiniuBucket);
        token.subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int status = jsonObject.getInt("status");
                            if (200 == status) {
                                String token = jsonObject.getString("token");
                                UploadFileUtils.getInstants().setToken(token);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

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

        // 点击返回时，如果底部的 Bar 是隐藏的，就让它显示出来
        if (mainBottombar.getVisibility() == View.GONE) {
            showBar();
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
