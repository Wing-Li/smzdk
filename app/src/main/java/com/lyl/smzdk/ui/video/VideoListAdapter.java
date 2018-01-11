package com.lyl.smzdk.ui.video;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.video.VideoInfo;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {


    public VideoListAdapter(int layoutResId, @Nullable List<VideoInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final VideoInfo info) {

        holder.setText(R.id.item_video_title, info.getTitle());
        holder.setText(R.id.item_video_date, info.getDatetime());
        holder.setText(R.id.item_video_wetch_count, info.getPlayCount());
        holder.setText(R.id.item_video_land_count, info.getLaudNum());

        ImgUtils.load(mContext, info.getImage(), (ImageView) holder.getView(R.id.item_video_thm));
    }

}
