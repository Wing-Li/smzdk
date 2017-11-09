package com.lyl.smzdk.ui.user;


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
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.user_primary);
    }
}
