package com.lyl.smzdk.ui.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;

import butterknife.BindView;

public class SearchFragment extends BaseFragment {


    @BindView(R.id.search_actionbar_img)
    ImageView searchActionbarImg;
    @BindView(R.id.search_actionbar_edt)
    EditText searchActionbarEdt;
    @BindView(R.id.search_actionbar_btn)
    TextView searchActionbarBtn;
    @BindView(R.id.search_tablayout)
    TabLayout searchTablayout;
    @BindView(R.id.search_viewpager)
    ViewPager searchViewpager;

    private String mSearchStr;
    private String[] mSearchMenu = {"网盘1","网盘2","网盘3","网盘4"};

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setViewPager();
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.search_primary);
    }

    private void setViewPager() {
        searchViewpager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return SearchListFragment.newInstance("10001");
            }

            @Override
            public int getCount() {
                return mSearchMenu.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mSearchMenu[position];
            }
        });
        searchTablayout.setupWithViewPager(searchViewpager);
    }
}
