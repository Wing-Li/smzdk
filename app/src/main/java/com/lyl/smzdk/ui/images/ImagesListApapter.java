package com.lyl.smzdk.ui.images;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.images.ImageInfo;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/15.
 */
public class ImagesListApapter extends BaseQuickAdapter<ImageInfo, BaseViewHolder> {


    public ImagesListApapter(int layoutResId, @Nullable List<ImageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, ImageInfo shopInfo) {
        holder.setText(R.id.item_images_title, shopInfo.getTitle());

        ImgUtils.load(mContext, shopInfo.getSthumbUrl(), (ImageView) holder.getView(R.id.item_images_img));
    }
}
