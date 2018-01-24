package com.lyl.smzdk.ui.video;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.video.VideoInflaterInfo;
import com.lyl.smzdk.network.imp.video.XgImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;
import com.lyl.smzdk.view.ActionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.video_play_layout)
    RelativeLayout videoPlayLayout;
    @BindView(R.id.video_layout)
    LinearLayout videoLayout;
    @BindView(R.id.actionbar)
    ActionBar actionbar;
    @BindView(R.id.video_comment_list)
    RecyclerView videoCommentList;

    private String mUrl;
    private String mTitle;
    private String mThumbnail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        getParameter();
        initView();
        setVideoPlay();
    }

    private void initView() {
        actionbar.setModelBack(mTitle, mActivity);
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

    @OnClick(R.id.video_play_layout)
    public void clickBg() {
        // 此方法会为每个 View 保存其当前的可见状态(Visibility)。
        TransitionManager.beginDelayedTransition(videoLayout);

        videoCommentList.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams layoutParams = videoPlayLayout.getLayoutParams();
        layoutParams.height = videoPlay.getMeasuredHeight();
        videoPlayLayout.setLayoutParams(layoutParams);

        videoPlayLayout.setBackgroundColor(0xFFFFFF);
        actionbar.setVisibility(View.VISIBLE);
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
