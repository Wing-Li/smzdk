package com.lyl.smzdk.ui.user;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;

public class UserFragment extends BaseFragment {


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar.setModelOnlyTitle(R.string.user_title);
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.user_primary);
    }
}
