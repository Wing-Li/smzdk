package com.lyl.smzdk.ui.images;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.MyApp;
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

        if (MyApp.isWifi) {
            int w = shopInfo.getSthumb_width();
            int h = shopInfo.getSthumb_height();

            if (h > 2000) {
                h = 1920;
            }

            ImgUtils.load(mContext, shopInfo.getSthumbUrl(), (ImageView) holder.getView(R.id.item_images_img), w, h);
        } else {
            int w = shopInfo.getBthumb_width();
            int h = shopInfo.getBthumb_height();

            if (h > 2000) {
                h = 1920;
            }

            ImgUtils.load(mContext, shopInfo.getBthumbUrl(), (ImageView) holder.getView(R.id.item_images_img), w, h);
        }
    }
}
