package com.lyl.smzdk.ui.main;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.R2;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.MainLoadDataEvent;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.main.images.GifWebActivity;
import com.lyl.smzdk.ui.main.images.ImagesActivity;
import com.lyl.smzdk.ui.main.news.menu.MenuListActivity;
import com.lyl.smzdk.ui.web.Html5Activity;
import com.lyl.smzdk.utils.DisplayUtil;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.view.layoutmanager.LinearLayoutManagerWrapper;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseFragment {


    @BindView(R2.id.main_content_list)
    RecyclerView mainContentListView;

    // 顶部控件
    Banner mianBanner;
    RecyclerView mainMenuListView;
    TextView mainNewNotice;

    private String[] images = {//
            "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=27&gp=0.jpg",//
            "http://upload-images.jianshu.io/upload_images/632860-b921edc6f8fa62e8.png?imageMogr2/auto-orient/strip" +
                    "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/1835526-de24e0123e56a526.jpg?imageMogr2/auto-orient/strip" +
                    "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/2041831-9824e97cc023b5ac" + "" + "" + "" + "" + "" + "" + "" +
                    ".jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50"};//
    private String[] titles = {//
            "7节异性沟通课，治愈你的尬聊单身症",//
            "问答老司机投稿须知",//
            "全职妈妈，你想成为下一个作家吗？",//
            "365挑战营与世间事联合征文 /简书那么大，我在哪里？"};//


    private List<NewMenu> mNewChannelList = new ArrayList<>();
    private MainMenuListAdapter mMenuListAdapter;

    private List<NewInfo> mNewInfoList = new ArrayList<>();
    private MainContentApadter mMainContentApadter;

    private int page = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onHiddenChanged(false);
        mActionBar.setModelOnlyTitle(R.string.home);

        initMenuData();
        setHeader();
        setContentListView();

        loadMoreData(new MainLoadDataEvent(page));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadMoreData(MainLoadDataEvent event) {
        NewInfo newInfo;
        for (int i = 0; i < 10; i++) {
            newInfo = new NewInfo();
            newInfo.setTitle(event.page + "." + i + "BRVAH是一个强大的RecyclerAdapter框架");
            if (mMainContentApadter.getLoadMoreViewPosition() % 3 == 0) {
                String[] images = {"http://s.go2yd.com/b/ilulgedc_7g00d1d1.jpg", //
                        "https://s.go2yd.com/b/j9dslc00_8a19b6b6.png", //
                        "https://s.go2yd.com/b/j7juj0ba_8708b6b6.jpg"};
                newInfo.setImages(Arrays.asList(images));
            } else {
                newInfo.setImage("http://s.go2yd.com/b/icms7905_7i00d1d1.jpg");
            }
            newInfo.setIntroduce("An Amazon Kinesis 应用程序是读取和处理来自 Amazon Kinesis 数据流数据" +//
                    "的数据使用器。您可以使用 Kinesis API 或 客户端库(KCL) 构建 Amazon Kinesis 应用程序。");
            newInfo.setUrl("http://www.jianshu.com/p/a43daa1e3d6e");
            mMainContentApadter.addData(newInfo);
        }
        mMainContentApadter.loadMoreComplete();
        page = ++event.page;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setStatusBarColor(R.color.main_primary);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

    /**
     * 设置目录列表
     */
    private void initMenuData() {
        // 如果是 新闻 类的，必须设置 setShowType
        NewMenu channel;
        channel = new NewMenu();
        channel.setName(getString(R.string.menu_weixin));
        channel.setImageRes(R.drawable.weixin_icon);
        channel.setType(Constans.NEWS_TYPE_WEIXIN);
        channel.setShowType(Constans.SHOW_ITEM_CONTENT_2);
        mNewChannelList.add(channel);

        channel = new NewMenu();
        channel.setName(getString(R.string.menu_zhihu));
        channel.setImageRes(R.drawable.zhihu_icon);
        channel.setType(Constans.NEWS_TYPE_ZHIHU);
        channel.setShowType(Constans.SHOW_ITEM_CONTENT_2);
        mNewChannelList.add(channel);

        channel = new NewMenu();
        channel.setName(getString(R.string.menu_xiandu));
        channel.setImageRes(R.drawable.xiandu_icon);
        channel.setType(Constans.NEWS_TYPE_XIANDU);
        channel.setShowType(Constans.SHOW_ITEM_CONTENT_4);
        mNewChannelList.add(channel);

        channel = new NewMenu();
        channel.setName(getString(R.string.menu_lengzhishi));
        channel.setImageRes(R.drawable.lengzhishi_icon);
        channel.setType(Constans.NEWS_TYPE_LENGZHISHI);
        channel.setShowType(Constans.SHOW_ITEM_CONTENT_2);
        mNewChannelList.add(channel);

        channel = new NewMenu();
        channel.setName(getString(R.string.menu_duzhe));
        channel.setImageRes(R.drawable.duzhe_icon);
        channel.setType(Constans.NEWS_TYPE_DUZHE);
        channel.setShowType(Constans.SHOW_ITEM_CONTENT_2);
        mNewChannelList.add(channel);

        channel = new NewMenu();
        channel.setName(getString(R.string.menu_neihan));
        channel.setImageRes(R.drawable.neihan_icon);
        channel.setType(Constans.NEWS_TYPE_XIUXIAN);
        channel.setShowType(Constans.SHOW_ITEM_CONTENT_2);
        mNewChannelList.add(channel);

        channel = new NewMenu();
        channel.setName(getString(R.string.menu_meinv));
        channel.setImageRes(R.drawable.girl_icon);
        channel.setType(Constans.NEWS_TYPE_MEINV);
        mNewChannelList.add(channel);

        channel = new NewMenu();
        channel.setName(getString(R.string.menu_gif));
        channel.setImageRes(R.drawable.gif_icon);
        channel.setType(Constans.NEWS_TYPE_GIF_WEB);
        mNewChannelList.add(channel);

    }

    /**
     * 设置目录的显示
     */
    private void setMenu() {
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

        // 目录点击事件
        mMenuListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NewMenu newMenu = mNewChannelList.get(i);
                Intent intent;
                if (Constans.NEWS_TYPE_MEINV.equals(newMenu.getType())) { // 美图
                    intent = new Intent(getHolder(), ImagesActivity.class);
                    intent.putExtra(ImagesActivity.IMG_TYPE, ImagesActivity.IMG_TYPE_SOGOU_IMG);
                    startActivity(intent);

                } else if (Constans.NEWS_TYPE_GIF.equals(newMenu.getType())) { // 有趣动图 api 版，暂时没有用
                    // TODO：下面这个的 api 版，暂时没有用
                    intent = new Intent(getHolder(), ImagesActivity.class);
                    intent.putExtra(ImagesActivity.IMG_TYPE, ImagesActivity.IMG_TYPE_SOGOU_GIF);
                    startActivity(intent);

                } else if (Constans.NEWS_TYPE_GIF_WEB.equals(newMenu.getType())) { // 有趣动图
                    intent = new Intent(getHolder(), GifWebActivity.class);
                    intent.putExtra(Constans.I_URL, Network.URL_IMG_SOGOU_GIF);
                    intent.putExtra(Constans.I_TITLE, newMenu.getName());
                    startActivity(intent);

                } else {// 微信、知乎、读者、闲读、冷知识、休闲娱乐
                    intent = new Intent(getHolder(), MenuListActivity.class);
                    intent.putExtra(Constans.I_CHANNEL_TYPE_TYPE, newMenu.getType());
                    intent.putExtra(Constans.I_LIST_ITEM_SHOW_TYPE, newMenu.getShowType());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 设置头部 Banner、目录、公告
     */
    private void setHeader() {
        mianBanner = rootView.findViewById(R.id.mian_banner);
        mainMenuListView = rootView.findViewById(R.id.main_menu_list);
        mainNewNotice = rootView.findViewById(R.id.main_new_notice);
        setBanner();
        setMenu();
    }

    /**
     * 设置底部 内容
     */
    private void setContentListView() {
        mMainContentApadter = new MainContentApadter(mNewInfoList);
        mMainContentApadter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        // 加载更多。  注意：默认第一次加载会进入回调
        mMainContentApadter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMoreData(new MainLoadDataEvent(page));
            }
        }, mainContentListView);
        // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
//        mMainContentApadter.setPreLoadNumber(3);

        mainContentListView.setLayoutManager(new LinearLayoutManagerWrapper(getHolder()));
        mainContentListView.addItemDecoration(new DividerItemDecoration(getHolder(), DividerItemDecoration.VERTICAL));
        mainContentListView.setAdapter(mMainContentApadter);
        mainContentListView.addOnScrollListener(mOnScrollHideBottombarListener);

        mMainContentApadter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NewInfo info = (NewInfo) baseQuickAdapter.getItem(i);
                if (info == null) {
                    showToast(getString(R.string.data_error));
                    return;
                }

                Intent intent = new Intent(getHolder(), Html5Activity.class);
                intent.putExtra(Constans.I_TITLE, info.getTitle());
                intent.putExtra(Constans.I_URL, info.getUrl());
                intent.putExtra(Constans.I_CHANNEL_TYPE_TYPE, Constans.NEWS_TYPE_WEIXIN);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    View titleView = view.findViewById(R.id.item_main_content_title);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), Pair.create
                            (titleView, "content_title"));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }

}
