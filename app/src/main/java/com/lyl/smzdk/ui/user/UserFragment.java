package com.lyl.smzdk.ui.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;

import butterknife.BindView;

public class UserFragment extends BaseFragment {


    @BindView(R.id.user_login)
    LinearLayout userLogin;
    @BindView(R.id.user_vip)
    LinearLayout userVip;
    @BindView(R.id.user_help)
    LinearLayout userHelp;
    @BindView(R.id.user_qq_group)
    LinearLayout userQqGroup;
    @BindView(R.id.user_share)
    LinearLayout userShare;
    @BindView(R.id.user_suggestion)
    LinearLayout userSuggestion;
    @BindView(R.id.user_update)
    LinearLayout userUpdate;
    @BindView(R.id.user_about)
    LinearLayout userAbout;

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
