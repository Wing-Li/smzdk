package com.lyl.smzdk.ui.video;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.VideoMenuInfo;
import com.lyl.smzdk.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;

public class VideoFragment extends BaseFragment {


    @BindView(R.id.video_tablayout)
    TabLayout videoTablayout;
    @BindView(R.id.video_viewpager)
    ViewPager videoViewpager;

    private List<VideoMenuInfo> mVideoMenuInfos = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActionBar.setModelOnlyTitle(R.string.video_title);
        initMenuData();
        setViewPager();
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.video_primary);
    }

    private void initMenuData() {
        VideoMenuInfo menuInfo;
        for (int i = 0; i < 6; i++) {
            menuInfo = new VideoMenuInfo();
            menuInfo.setType("type" + i);
            menuInfo.setTitle("科技" + i);
            mVideoMenuInfos.add(menuInfo);
        }
    }

    private void setViewPager() {
        // 设置每个目录的页面
        videoViewpager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public int getCount() {
                return mVideoMenuInfos.size() <= 0 ? 0 : mVideoMenuInfos.size();
            }

            @Override
            public Fragment getItem(int i) {
                VideoMenuInfo menuInfo = mVideoMenuInfos.get(i);
                VideoListFragment fragment = VideoListFragment.newInstance(menuInfo.getType(), menuInfo.getTitle());
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mVideoMenuInfos.get(position).getTitle();
            }
        });

        // 切换tab事件
        videoViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                JZVideoPlayer.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 绑定 Tablayout 和 Viewpager
        videoTablayout.setupWithViewPager(videoViewpager);
    }

}
