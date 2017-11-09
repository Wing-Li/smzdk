package com.lyl.smzdk.ui.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.Arrays;

import butterknife.BindView;

public class MainFragment extends BaseFragment {


    @BindView(R.id.mian_banner)
    Banner mianBanner;
    @BindView(R.id.main_nestedscrollview)
    NestedScrollView mainNestedscrollview;

    private String[] images = {//
            "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=27&gp=0.jpg",//
            "http://upload-images.jianshu.io/upload_images/632860-b921edc6f8fa62e8.png?imageMogr2/auto-orient/strip"
                    + "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/1835526-de24e0123e56a526.jpg?imageMogr2/auto-orient/strip"
                    + "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/2041831-9824e97cc023b5ac" + "" + "" + "" + "" +
                    ".jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50"};//
    private String[] titles = {//
            "7节异性沟通课，治愈你的尬聊单身症",//
            "问答老司机投稿须知",//
            "全职妈妈，你想成为下一个作家吗？",//
            "365挑战营与世间事联合征文 /简书那么大，我在哪里？"};//


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBanner();
    }

    private void setBanner() {
        //设置banner样式
        mianBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mianBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mianBanner.setImageLoader(new GlideImageLoader());
        mianBanner.setImages(Arrays.asList(images));
        mianBanner.setBannerTitles(Arrays.asList(titles));
        mianBanner.setDelayTime(4000);
        mianBanner.start();

        mianBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int i) {
                showToast("点击了第 " + (i + 1) + " 张图");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.main_primary);
    }
}
