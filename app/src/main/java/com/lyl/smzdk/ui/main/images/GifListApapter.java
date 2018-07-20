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
public class GifListApapter extends BaseQuickAdapter<GifInfo.ItemsBean, MyBaseViewHolder> {


    public GifListApapter(int layoutResId, @Nullable List<GifInfo.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final MyBaseViewHolder holder, GifInfo.ItemsBean shopInfo) {
        String title = shopInfo.getTitle();
        if (TextUtils.isEmpty(title)) {
            holder.setVisible(R.id.item_images_title, false);
        } else {
            holder.setText(R.id.item_images_title, title);
        }

//        int w = shopInfo.getWidth();
//        int h = shopInfo.getHeight();
//
//        if (h > 1280) {
//            w = 1280 * w / h;
//            h = 1280;
//        }

        ImgUtils.load(mContext, shopInfo.getPicUrl(), (ImageView) holder.getView(R.id.item_images_img));
    }
}
