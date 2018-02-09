package com.lyl.smzdk.ui.main.news.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.ui.main.news.list.ListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 各种类别的目录列表页
 */
public class MenuListActivity extends BaseActivity implements MenuContract.View {

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

    private MenuContract.Presenter mDataPresenter;
    private String mChannelType;
    private int mListItemShowType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        ButterKnife.bind(this);

        getPremter();
        if (TextUtils.isEmpty(mChannelType)) {
            showToast(R.string.data_error);
            finish();
        }

        // 显示加载进度
        showDialog();
        // 初始化目录数据
        mDataPresenter = new MenuDataPresenter(this);
        mDataPresenter.initMenuData(mChannelType);

        initView();
    }

    private void initView() {
        actionbarImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        int title;
        switch (mChannelType) {
            case Constans.NEWS_TYPE_WEIXIN:
                title = R.string.menu_weixin;
                break;
            case Constans.NEWS_TYPE_ZHIHU:
                title = R.string.menu_zhihu;
                break;
            case Constans.NEWS_TYPE_DUZHE:
                title = R.string.menu_duzhe;
                break;
            default:
                title = R.string.app_name;
                break;
        }

        actionbarTitle.setText(title);
    }

    private void getPremter() {
        Intent intent = getIntent();
        mChannelType = intent.getStringExtra(Constans.I_CHANNEL_TYPE_TYPE);
        mListItemShowType = intent.getIntExtra(Constans.I_LIST_ITEM_SHOW_TYPE, Constans.SHOW_ITEM_CONTENT_1);
    }

    @Override
    public void setMenuTab(final List<NewMenu> menuList) {
        if (menuList.size() <= 4) {
            menuListTablayout.setTabMode(TabLayout.MODE_FIXED);
        }else {
            menuListTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        menuListViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                NewMenu menu = menuList.get(i);
                return ListFragment.newInstance(mChannelType, menu.getType(), mListItemShowType);
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

        // 隐藏加载进度
        hideDialog();
    }
}