package com.lyl.smzdk.ui.news;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.NewChannel;
import com.lyl.smzdk.network.entity.NewInfo;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.utils.DisplayUtil;
import com.lyl.smzdk.utils.ImgUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseFragment {


    @BindView(R.id.mian_banner)
    Banner mianBanner;
    @BindView(R.id.main_nestedscrollview)
    NestedScrollView mainNestedscrollview;
    @BindView(R.id.main_menu_list)
    RecyclerView mainMenuListView;
    @BindView(R.id.main_new_notice)
    TextView mainNewNotice;
    @BindView(R.id.main_content_list)
    RecyclerView mainContentListView;

    private String[] images = {//
            "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=27&gp=0.jpg",//
            "http://upload-images.jianshu.io/upload_images/632860-b921edc6f8fa62e8.png?imageMogr2/auto-orient/strip"
                    + "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/1835526-de24e0123e56a526.jpg?imageMogr2/auto-orient/strip"
                    + "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/2041831-9824e97cc023b5ac" + "" + "" + "" + "" + "" + "" +
                    ".jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50"};//
    private String[] titles = {//
            "7节异性沟通课，治愈你的尬聊单身症",//
            "问答老司机投稿须知",//
            "全职妈妈，你想成为下一个作家吗？",//
            "365挑战营与世间事联合征文 /简书那么大，我在哪里？"};//


    private List<NewChannel> mNewChannelList;
    private MainMenuListAdapter mMenuListAdapter;

    private List<NewInfo> mNewInfoList;
    private MainContentApadter mMainContentApadter;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        setBanner();
        setMenu();
        setContentListView();
        loadMoreData();
    }

    private void initData() {
        mNewChannelList = new ArrayList<>();
        NewChannel channel;
        for (int i = 0; i < 8; i++) {
            channel = new NewChannel();
            channel.setName("互联网" + i);
            channel.setImage("http://s.go2yd.com/b/iclolrmr_bu00d1d1.jpg");
            mNewChannelList.add(channel);
        }
    }

    private void loadMoreData() {
        mNewInfoList = new ArrayList<>();
        NewInfo newInfo;
        for (int i = 0; i < 10; i++) {
            newInfo = new NewInfo();
            newInfo.setTitle("BRVAH是一个强大的RecyclerAdapter框架" + i);
            newInfo.setImage("http://s.go2yd.com/b/icms7905_7i00d1d1.jpg");
            newInfo.setIntroduce("An Amazon Kinesis 应用程序是读取和处理来自 Amazon Kinesis 数据流数据的数据使用器。您可以使用 Kinesis API 或 客户端库 " +
                    "" + "(KCL) 构建 Amazon Kinesis 应用程序。");
            mMainContentApadter.addData(newInfo);
        }
        mMainContentApadter.loadMoreComplete();
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.main_primary);
    }

    /**
     * 设置轮播图
     */
    private void setBanner() {
        //设置banner样式
        mianBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mianBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mianBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object o, ImageView imageView) {
                ImgUtils.load(context, (String) o, imageView);
            }
        });
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

    private void setMenu() {
//        WrappingLinearLayoutManager layoutManager = new WrappingLinearLayoutManager(getHolder());
//        layoutManager.setAutoMeasureEnabled(false);
//        mainMenuListView.setLayoutManager(layoutManager);

        mMenuListAdapter = new MainMenuListAdapter(R.layout.item_main_menu, mNewChannelList);
        mainMenuListView.setLayoutManager(new GridLayoutManager(getHolder(), 4));
        mainMenuListView.addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view) < 4) {
                    outRect.top = 0;
                } else {
                    outRect.top = DisplayUtil.dip2px(getHolder(), 8);
                }
            }
        });
        mainMenuListView.setAdapter(mMenuListAdapter);

        mMenuListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                showToast("点击了第 " + (i + 1) + " 张图");
            }
        });
    }

    private void setContentListView() {
        mMainContentApadter = new MainContentApadter(R.layout.item_main_content, mNewInfoList);
        mMainContentApadter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        // 加载更多。  注意：默认第一次加载会进入回调
        mMainContentApadter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                loadMoreData();
            }
        }, mainContentListView);
        // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
        mMainContentApadter.setPreLoadNumber(3);

//        WrappingLinearLayoutManager layoutManager = new WrappingLinearLayoutManager(getHolder());
//        layoutManager.setAutoMeasureEnabled(false);
        mainContentListView.setLayoutManager(new LinearLayoutManager(getHolder()));

        mainContentListView.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));
        mainContentListView.setAdapter(mMainContentApadter);


    }

}
