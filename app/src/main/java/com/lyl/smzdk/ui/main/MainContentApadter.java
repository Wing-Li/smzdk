package com.lyl.smzdk.ui.main;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class MainContentApadter extends BaseQuickAdapter<NewInfo, BaseViewHolder> {

    private final int IMAGE_1 = 10001;
    private final int IMAGE_3 = 10002;

    public MainContentApadter(@Nullable List<NewInfo> data) {
        super(data);

        setMultiTypeDelegate(new MultiTypeDelegate<NewInfo>() {
            @Override
            protected int getItemType(NewInfo newInfo) {
                if (newInfo.getImages() != null && newInfo.getImages().size() > 1) {
                    return IMAGE_3;
                } else {
                    return IMAGE_1;
                }
            }
        });

        getMultiTypeDelegate()//
                .registerItemType(IMAGE_3, R.layout.item_main_content_3)//
                .registerItemType(IMAGE_1, R.layout.item_main_content_1);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewInfo newInfo) {
        switch (holder.getItemViewType()) {
            case IMAGE_1: {
                holder.setText(R.id.item_main_content_title, newInfo.getTitle());
                holder.setText(R.id.item_main_content_introduce, newInfo.getIntroduce());

                ImgUtils.load(mContext, newInfo.getImage(), (ImageView) holder.getView(R.id.item_main_content_image));
                break;
            }
            case IMAGE_3: {
                holder.setText(R.id.item_main_content_title, newInfo.getTitle());
                holder.setText(R.id.item_main_content_introduce, newInfo.getIntroduce());

                ImgUtils.load(mContext, newInfo.getImages().get(0), (ImageView) holder.getView(R.id
                        .item_main_content_image1));
                ImgUtils.load(mContext, newInfo.getImages().get(1), (ImageView) holder.getView(R.id
                        .item_main_content_image2));
                ImgUtils.load(mContext, newInfo.getImages().get(2), (ImageView) holder.getView(R.id
                        .item_main_content_image3));
                break;
            }
        }

    }
}
