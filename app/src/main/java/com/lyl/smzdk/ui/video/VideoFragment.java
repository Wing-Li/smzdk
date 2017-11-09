package com.lyl.smzdk.ui.video;


import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseFragment;

public class VideoFragment extends BaseFragment {


    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_video;
    }

    @Override
    public void onStart() {
        super.onStart();
        setStatusBarColor(R.color.video_primary);
    }
}
