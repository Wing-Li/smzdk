package com.lyl.smzdk.ui.video;

import android.content.Intent;
import android.os.Build;
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
        ImgUtils.load(mContext, mThumbnail, videoPlay.thumbImageView);

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
//                XgImp xgImp = new XgImp();
//                String videoUrl = xgImp.getVideoUrl(mUrl);
//
//                observableEmitter.onNext(videoUrl);
//            }
//        })//
//                .subscribeOn(Schedulers.io())//
//                .observeOn(AndroidSchedulers.mainThread())//
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        videoPlay.setUp(s, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, mTitle);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

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
