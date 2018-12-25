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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.video.VideoInflaterInfo;
import com.lyl.smzdk.network.entity.video.XgComment;
import com.lyl.smzdk.network.imp.video.XgImp;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.LogUtils;
import com.lyl.smzdk.view.ActionBar;
import com.lyl.smzdk.view.layoutmanager.LinearLayoutManagerWrapper;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.List;

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
    @BindView(R.id.video_title)
    TextView videoTitle;
    @BindView(R.id.video_comment_layout)
    LinearLayout videoCommentLayout;
    @BindView(R.id.video_comment_list)
    RecyclerView videoCommentList;

    private String mUrl;
    private String mId;
    private String mTitle;
    private String mThumbnail;

    private VideoCommentAdapter mCommentAdapter;
    private int page = 0;
    private boolean hasMore = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        getParameter();
        setVideoPlay();
        initView();
    }

    private void initView() {
        actionbar.setModelLeft(mTitle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCommentAdapter = new VideoCommentAdapter();
        videoCommentList.setLayoutManager(new LinearLayoutManagerWrapper(mContext));
        videoCommentList.setAdapter(mCommentAdapter);
        mCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadCommentData();
            }
        }, videoCommentList);
    }

    private void setVideoPlay() {
        videoTitle.setText(mTitle);

        ImgUtils.load(mContext, mThumbnail, videoPlay.thumbImageView);
        videoPlay.bottomProgressBar.setBackgroundResource(R.color.black);

        videoPlay.titleTextView.setVisibility(View.GONE);

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
                            videoPlay.startButton.performClick();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoInflaterInfo> call, Throwable t) {
                t.getMessage();
            }
        });

        // 同时开始默默的加载评论
        loadCommentData();
    }

    public void getParameter() {
        Intent intent = getIntent();
        mId = intent.getStringExtra(Constans.I_ID);
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
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
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

    private void loadCommentData(){
        if (!hasMore) {
            showToast(R.string.not_more);
            return;
        }

        Call<XgComment> commentList = Network.getXgCommentApi().getCommentList(mId, mId, page * 20, 20);
        Call<XgComment> clone = commentList.clone();
        clone.enqueue(new Callback<XgComment>() {
            @Override
            public void onResponse(Call<XgComment> call, Response<XgComment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    XgComment body = response.body();
                    if (body != null && "success".equals(body.getMessage())){
                        hasMore = body.getData().isHas_more();

                        List<XgComment.DataBean.CommentsBean> commentsList = body.getData().getComments();
                        mCommentAdapter.addData(commentsList);
                        page ++;

                        if (hasMore){
                            mCommentAdapter.loadMoreComplete();
                        } else {
                            mCommentAdapter.loadMoreEnd();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<XgComment> call, Throwable throwable) {
                showToast(R.string.net_error);
                if (throwable != null) {
                    CrashReport.postCatchedException(throwable);
                }
            }
        });

    }
}
