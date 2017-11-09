package com.lyl.smzdk.ui.news;


import android.support.v4.widget.NestedScrollView;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;
import com.youth.banner.Banner;

import butterknife.BindView;

public class MainFragment extends BaseFragment {


    @BindView(R.id.mian_banner)
    Banner mianBanner;
    @BindView(R.id.main_nestedscrollview)
    NestedScrollView mainNestedscrollview;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.main_primary);
    }
}
