package com.lyl.smzdk.ui.video;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.video.VideoInflaterInfo;
import com.lyl.smzdk.network.imp.video.XgImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ImgUtils.load(mContext, mThumbnail, videoPlay.thumbImageView);
        videoPlay.bottomProgressBar.setBackgroundResource(R.color.black);


        XgImp xgImp = new XgImp();
        Call<VideoInflaterInfo> inflaterInfoCall = xgImp.getVideoUrl(mUrl);
        Call<VideoInflaterInfo> clone = inflaterInfoCall.clone();
        clone.enqueue(new Callback<VideoInflaterInfo>() {
            @Override
            public void onResponse(Call<VideoInflaterInfo> call, Response<VideoInflaterInfo> response) {
                if (response.isSuccessful()) {
                    VideoInflaterInfo body = response.body();
                    if (body != null) {
                        if (body.getData() != null && body.getData().size() > 0) {
                            videoPlay.setUp(body.getData().get(0).getUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST, mTitle);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoInflaterInfo> call, Throwable t) {

            }
        });

    }

    public void getParameter() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(Constans.I_TITLE);
        mThumbnail = intent.getStringExtra(Constans.I_IMAGE);
        mUrl = intent.getStringExtra(Constans.I_URL);
        LogUtils.d("视频地址：" + mUrl);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
