package com.lyl.smzdk.ui.images;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.images.ImageMenu;
import com.lyl.smzdk.network.imp.images.ImgsImp;
import com.lyl.smzdk.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImagesFragment extends BaseFragment {

    @BindView(R.id.images_tablayout)
    TabLayout imagesTablayout;
    @BindView(R.id.images_viewpager)
    ViewPager imagesViewpager;

    private List<ImageMenu> mImageMenuList = new ArrayList<>();


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_images;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onHiddenChanged(false);
        mActionBar.setModelOnlyTitle(R.string.images_title);

        initMenuData();
        setViewPager();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {// show
            setStatusBarColor(R.color.images_primary);
            if (imagesViewpager != null && imagesViewpager.getCurrentItem() != 0) {
                imagesViewpager.setCurrentItem(0, false);
            }
        }
    }

    private void initMenuData() {
        ImgsImp imgsImp = new ImgsImp();
        mImageMenuList = imgsImp.getMenu();
    }

    private void setViewPager() {
        // 设置每个目录的页面
        imagesViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return mImageMenuList.size() <= 0 ? 0 : mImageMenuList.size();
            }

            @Override
            public Fragment getItem(int i) {
                ImageMenu menuInfo = mImageMenuList.get(i);
                return ImagesListFragment.newInstance(menuInfo.getType(), menuInfo.getName());
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mImageMenuList.get(position).getName();
            }
        });

        // 绑定 Tablayout 和 Viewpager
        imagesTablayout.setupWithViewPager(imagesViewpager);
    }
}
