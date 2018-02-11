package com.lyl.smzdk.ui.main.images;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.news.GifInfo;
import com.lyl.smzdk.ui.main.news.list.MyBaseViewHolder;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/15.
 */
public class GifListSummaryApapter extends BaseQuickAdapter<GifInfo.CatesBean, MyBaseViewHolder> {


    public GifListSummaryApapter(int layoutResId, @Nullable List<GifInfo.CatesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final MyBaseViewHolder holder, GifInfo.CatesBean info) {
        String title = info.getName();
        if (TextUtils.isEmpty(title)){
            holder.setVisible(R.id.item_images_title, false);
        } else {
            holder.setText(R.id.item_images_title, title);
        }

        ImgUtils.loadGif(mContext, info.getIcon(), (ImageView) holder.getView(R.id.item_images_img));
    }
}
