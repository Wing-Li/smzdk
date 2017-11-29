package com.lyl.smzdk.ui.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Author: lyl
 * Date Created : 2017/11/29.
 */
public class VideoPlayerActivity extends BaseActivity {

    @BindView(R.id.video_play)
    JZVideoPlayerStandard videoPlay;

    private String mUrl;
    private String mTitle;
    private String mThumbnail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        getParameter();
        setVideoPlay();
    }

    private void setVideoPlay() {
        videoPlay.setUp(mUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, mTitle);
        ImgUtils.load(mContext, mThumbnail, videoPlay.thumbImageView);
    }

    public void getParameter() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constans.I_URL);
        mTitle = intent.getStringExtra(Constans.I_TITLE);
        mThumbnail = intent.getStringExtra(Constans.I_IMAGE);
        LogUtils.d("视频地址：" + mUrl);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
