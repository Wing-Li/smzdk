package com.lyl.smzdk.ui.video;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.video.VideoInflaterInfo;
import com.lyl.smzdk.network.entity.video.VideoInfo;
import com.lyl.smzdk.network.imp.video.XgImp;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {


    public VideoListAdapter(int layoutResId, @Nullable List<VideoInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final VideoInfo info) {
        JZVideoPlayer.releaseAllVideos();

        final JZVideoPlayerStandard player = holder.getView(R.id.item_video_player);

        XgImp xgImp = new XgImp();
        Call<VideoInflaterInfo> inflaterInfoCall = xgImp.getVideoUrl(info.getGroup_id());
        Call<VideoInflaterInfo> clone = inflaterInfoCall.clone();
        clone.enqueue(new Callback<VideoInflaterInfo>() {
            @Override
            public void onResponse(Call<VideoInflaterInfo> call, Response<VideoInflaterInfo> response) {
                if (response.isSuccessful()){
                    VideoInflaterInfo body = response.body();
                    VideoInflaterInfo.DataBean dataBean = body.getData().get(1);

                    player.setUp( dataBean.getUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST, info.getTitle());
                }
            }

            @Override
            public void onFailure(Call<VideoInflaterInfo> call, Throwable t) {

            }
        });


        ImgUtils.load(mContext, info.getImage(), player.thumbImageView);
        player.positionInList = holder.getLayoutPosition();
    }
}
