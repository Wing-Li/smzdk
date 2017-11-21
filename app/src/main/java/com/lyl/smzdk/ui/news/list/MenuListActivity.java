package com.lyl.smzdk.ui.news.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.ui.BaseActivity;

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
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        ButterKnife.bind(this);

        getPremter();

        mDataPresenter = new MenuDataPresenter(this);
        mDataPresenter.initMenuData(mType);
    }

    private void getPremter() {
        Intent intent = getIntent();
        mType = intent.getIntExtra(Constans.I_MENU_LIST_TYPE, 0);
    }

    @Override
    public void setMenuTab(final List<NewMenu> menuList) {
        menuListViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return ListFragment.newInstance(menuList.get(i).getType());
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
    }

    @Override
    public void setLoading() {

    }
}
