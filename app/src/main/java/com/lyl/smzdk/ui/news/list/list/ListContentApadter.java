package com.lyl.smzdk.ui.news.list.list;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class ListContentApadter extends BaseQuickAdapter<NewInfo, MyViewHolder> {


    public ListContentApadter(@Nullable List<NewInfo> data, final int itemType) {
        super(data);

        setMultiTypeDelegate(new MultiTypeDelegate<NewInfo>() {
            @Override
            protected int getItemType(NewInfo newInfo) {
                return itemType;
            }
        });

        getMultiTypeDelegate()//
                .registerItemType(Constans.SHOW_ITEM_CONTENT_1, R.layout.item_main_content_1)//
                .registerItemType(Constans.SHOW_ITEM_CONTENT_2, R.layout.item_main_content_2)//
                .registerItemType(Constans.SHOW_ITEM_CONTENT_3, R.layout.item_main_content_3);
    }

    @Override
    protected void convert(MyViewHolder holder, NewInfo newInfo) {
        holder.setText(R.id.item_main_content_title, newInfo.getTitle());
        holder.setText(R.id.item_main_content_introduce, newInfo.getIntroduce());

        holder.setText(R.id.item_main_content_author, newInfo.getAuthor());
        holder.setText(R.id.item_main_content_time, newInfo.getTime());

        if (!TextUtils.isEmpty(newInfo.getImage())) { // 单图
            ImgUtils.load(mContext, newInfo.getImage(), (ImageView) holder.getView(R.id.item_main_content_image));
        } else if (newInfo.getImages() != null && newInfo.getImages().size() == 3) { // 3 张图
            ImgUtils.load(mContext, newInfo.getImages().get(0), (ImageView) holder.getView(R.id
                    .item_main_content_image1));
            ImgUtils.load(mContext, newInfo.getImages().get(1), (ImageView) holder.getView(R.id
                    .item_main_content_image2));
            ImgUtils.load(mContext, newInfo.getImages().get(2), (ImageView) holder.getView(R.id
                    .item_main_content_image3));
        } else {
            // 图片都是空，则隐藏图片
            holder.setVisible(R.id.item_main_content_image, false);
        }
    }
}
