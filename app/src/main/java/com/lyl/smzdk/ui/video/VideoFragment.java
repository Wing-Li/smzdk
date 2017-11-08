package com.lyl.smzdk.ui.video;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;

import static com.lyl.smzdk.R.color.video_actionbar;

public class VideoFragment extends BaseFragment {


    public VideoFragment() {
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
        mActionBar.setBackgroundColor(video_actionbar);
    }
}
