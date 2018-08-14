package com.lyl.smzdk.ui.main;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.R2;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.event.MainLoadDataEvent;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.ui.BaseFragment;
import com.lyl.smzdk.ui.main.announce.AnnounceDetailsActivity;
import com.lyl.smzdk.ui.main.images.GifWebActivity;
import com.lyl.smzdk.ui.main.images.ImagesActivity;
import com.lyl.smzdk.ui.main.news.list.ListFragment;
import com.lyl.smzdk.ui.main.news.menu.MenuContract;
import com.lyl.smzdk.ui.main.news.menu.MenuDataPresenter;
import com.lyl.smzdk.ui.main.news.menu.MenuListActivity;
import com.lyl.smzdk.utils.DisplayUtil;
import com.lyl.smzdk.utils.ImgUtils;
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

public class MainFragment extends BaseFragment implements MenuContract.View {


    @BindView(R2.id.main_root_layout)
    CoordinatorLayout mainRootLayout;
    @BindView(R2.id.main_tablayout)
    TabLayout mainTablayout;
    @BindView(R2.id.main_viewpager)
    ViewPager mainViewpager;

    // 顶部控件
    @BindView(R2.id.mian_banner)
    Banner mianBanner;
    @BindView(R2.id.main_menu_list)
    RecyclerView mainMenuListView;
    @BindView(R2.id.main_new_notice)
    TextView mainNewNotice;
    @BindView(R2.id.main_new_notice_layout)
    LinearLayout mainNewNoticeLayout;

    private String[] images = {//
            "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=27&gp=0.jpg",//
            "http://upload-images.jianshu.io/upload_images/632860-b921edc6f8fa62e8.png?imageMogr2/auto-orient/strip" +
                    "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/1835526-de24e0123e56a526.jpg?imageMogr2/auto-orient/strip" +
                    "%7CimageView2/2/w/1240",//
            "http://upload-images.jianshu.io/upload_images/2041831-9824e97cc023b5ac.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50"};//
    private String[] titles = {//
            "7节异性沟通课，治愈你的尬聊单身症",//
            "问答老司机投稿须知",//
            "全职妈妈，你想成为下一个作家吗？",//
            "365挑战营与世间事联合征文 /简书那么大，我在哪里？"};//


    // 中部目录的列表 和 适配器
    private List<NewMenu> mNewChannelList = new ArrayList<>();
    private MainMenuListAdapter mMenuListAdapter;

    // 底部内容列表 的 tab
    private MenuContract.Presenter mDataPresenter;
    // 底部内容的类型
    private String mChannelType = Constans.NEWS_TYPE_WABA;
    // 底部内容的显示类型
    private int mListItemShowType = Constans.SHOW_ITEM_CONTENT_6;

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
        setHeaderView();

        setContentListView();
        loadMoreData(new MainLoadDataEvent(1));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadMoreData(MainLoadDataEvent event) {

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
                    outRect.top = DisplayUtil.dip2px(getHolder(), 4);
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

                }
//                else if (Constans.NEWS_TYPE_GIF.equals(newMenu.getType())) { // 有趣动图 api 版，暂时没有用
//                    // TODO：下面这个的 api 版，暂时没有用
//                    intent = new Intent(getHolder(), ImagesActivity.class);
//                    intent.putExtra(ImagesActivity.IMG_TYPE, ImagesActivity.IMG_TYPE_SOGOU_GIF);
//                    startActivity(intent);
//                }
                else if (Constans.NEWS_TYPE_GIF_WEB.equals(newMenu.getType())) { // 有趣动图
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
     * 设置公告
     */
    private void setAnnounce() {
        mainNewNoticeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getHolder(), AnnounceDetailsActivity.class));
            }
        });
    }

    /**
     * 设置头部 Banner、目录、公告
     */
    private void setHeaderView() {
        setBanner();
        setMenu();
        setAnnounce();
    }

    /**
     * 设置底部 内容
     */
    private void setContentListView() {
        // 初始化目录数据
        mDataPresenter = new MenuDataPresenter(this);
        mDataPresenter.initMenuData(mChannelType);
    }

    /**
     * 底部内容页，tab 的设置
     */
    @Override
    public void setMenuTab(final List<NewMenu> menuList) {
        // 设置顶部 tab 列表，设置点击 tab 显示 Fragment
        mainViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                NewMenu menu = menuList.get(i);

                return ListFragment.newInstance(mChannelType, menu.getType(), mListItemShowType, false);
            }

            @Override
            public int getCount() {
                return menuList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return menuList.get(position).getName();
            }
        });
        mainTablayout.setupWithViewPager(mainViewpager);
    }
}
