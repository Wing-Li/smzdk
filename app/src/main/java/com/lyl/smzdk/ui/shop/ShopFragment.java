package com.lyl.smzdk.ui.shop;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;

public class ShopFragment extends BaseFragment {


    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_shop;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar.setModelOnlyTitle(R.string.shop_title);
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.shop_primary);
    }
}
