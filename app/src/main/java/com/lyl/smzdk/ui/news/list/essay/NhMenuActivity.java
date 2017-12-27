package com.lyl.smzdk.ui.news.list.essay;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 各种类别的目录列表页
 */
public class NhMenuActivity extends BaseActivity {

    @BindView(R.id.actionbar_img_left)
    ImageView actionbarImgLeft;
    @BindView(R.id.actionbar_title)
    TextView actionbarTitle;
    @BindView(R.id.actionbar_img_right)
    ImageView actionbarImgRight;

    @BindView(R.id.menu_list_tablayout)
    TabLayout menuListTablayout;
    @BindView(R.id.menu_list_viewpager)
    ViewPager menuListViewpager;


    private List<NewMenu> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        ButterKnife.bind(this);

        initMenuData();
        initMenuView();
    }

    private void initMenuData() {
        menuList = new ArrayList<>();
        // 内涵段子
        NewMenu menu = new NewMenu();
        menu.setName(getString(R.string.menu_neihan_eassay));
        menu.setType(NhEassayListFragment.CONTENT_TYPE_ESSAY);
        menuList.add(menu);

        // 一点资讯-搞笑
        menu = new NewMenu();
        menu.setName(getString(R.string.menu_ydzx_gif));
        menu.setType(NhEassayListFragment.CONTENT_TYPE_YDZX_GIF);
        menuList.add(menu);

        // 内涵图片
        menu = new NewMenu();
        menu.setName(getString(R.string.menu_neihan_eassay_image));
        menu.setType(NhEassayListFragment.CONTENT_TYPE_IMAGE);
        menuList.add(menu);

        // 一点资讯-搞笑GIF
        menu = new NewMenu();
        menu.setName(getString(R.string.menu_ydzx_gif2));
        menu.setType(NhEassayListFragment.CONTENT_TYPE_YDZX_GIF2);
        menuList.add(menu);
        // 一点资讯-每天GIF
        menu = new NewMenu();
        menu.setName(getString(R.string.menu_ydzx_gif3));
        menu.setType(NhEassayListFragment.CONTENT_TYPE_YDZX_GIF3);
        menuList.add(menu);

    }

    public void initMenuView() {
        // 设置返回按钮
        actionbarImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 设置列表页面
        menuListViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                NewMenu menu = menuList.get(i);
                return NhEassayListFragment.newInstance(menu.getType());
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
        menuListTablayout.setupWithViewPager(menuListViewpager);

        // 设置顶部 Actionbar
        actionbarImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        actionbarTitle.setText(R.string.menu_neihan);
        actionbarImgRight.setVisibility(View.GONE);
    }
}
