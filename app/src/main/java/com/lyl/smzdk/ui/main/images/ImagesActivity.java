package com.lyl.smzdk.ui.main.images;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.images.ImageMenu;
import com.lyl.smzdk.network.imp.news.GifImp;
import com.lyl.smzdk.network.imp.news.MvtImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.view.ActionBar;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ImagesActivity extends BaseActivity {

    public static final String IMG_TYPE = "IMG_TYPE";
    public static final int IMG_TYPE_SOGOU_IMG = 1001;
    public static final int IMG_TYPE_SOGOU_GIF = 1002;

    @BindView(R.id.images_tablayout)
    TabLayout imagesTablayout;
    @BindView(R.id.images_viewpager)
    ViewPager imagesViewpager;
    @BindView(R.id.actionbar)
    ActionBar actionbar;

    private int mType;
    private List<ImageMenu> mImageMenuList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_images);
        ButterKnife.bind(this);

        mType = getIntent().getIntExtra(IMG_TYPE, 1001);
        initView();
        initMenuData();
    }

    private void initView() {
        actionbar.setModelOnlyTitle(R.string.images_title);

        if (!MyApp.isWifi) {
            showToast(R.string.no_wifi_status);
        }
    }

    private void initMenuData() {
        Observable.create(new ObservableOnSubscribe<List<ImageMenu>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ImageMenu>> observableEmitter) throws Exception {
                List<ImageMenu> menu = new ArrayList<>();
                switch (mType) {
                    case IMG_TYPE_SOGOU_IMG:
                        MvtImp mvtImp = new MvtImp();
                        menu = mvtImp.getMenu();
                        break;
                    case IMG_TYPE_SOGOU_GIF:
                        GifImp gifImp = new GifImp();
                        menu = gifImp.getMenu();
                        break;
                }

                observableEmitter.onNext(menu);
            }
        })//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<List<ImageMenu>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(List<ImageMenu> imageMenus) {
                        mImageMenuList = imageMenus;
                        // 设置每个目录的页面
                        imagesViewpager.setAdapter(mFragmentPagerAdapter);

                        // 绑定 Tablayout 和 Viewpager
                        imagesTablayout.setupWithViewPager(imagesViewpager);
                    }

                    @Override
                    public void onError(final Throwable throwable) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast(R.string.net_error);
                                if (throwable != null) {
                                    CrashReport.postCatchedException(throwable);
                                }
                            }
                        });
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 设置目录
     */
    private FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return mImageMenuList.size() <= 0 ? 0 : mImageMenuList.size();
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            ImageMenu menuInfo = mImageMenuList.get(i);

            switch (mType) {
                case IMG_TYPE_SOGOU_IMG:
                    fragment = ImagesListFragment.newInstance(menuInfo.getType(), menuInfo.getName());
                    break;

                case IMG_TYPE_SOGOU_GIF:
                    fragment = GifListFragment.newInstance(menuInfo.getType(), menuInfo.getName());
                    break;
            }

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mImageMenuList.get(position).getName();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImgUtils.clearMemory(getApplicationContext());
    }
}
