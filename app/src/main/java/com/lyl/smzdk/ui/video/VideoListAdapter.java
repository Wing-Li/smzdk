package com.lyl.smzdk.ui.video;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.VideoInfo;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo,BaseViewHolder>{


    public VideoListAdapter(int layoutResId, @Nullable List<VideoInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, VideoInfo info) {
        JZVideoPlayer.releaseAllVideos();

        JZVideoPlayerStandard player = holder.getView(R.id.item_list_videoplayer);
        player.setUp(info.getUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST, info.getTitle());
        player.positionInList = holder.getLayoutPosition();
    }
}
