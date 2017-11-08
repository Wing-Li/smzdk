package com.lyl.smzdk.ui.news;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;

public class MainFragment extends BaseFragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        mActionBar.setBackgroundColor(R.color.main_actionbar);
    }
}
