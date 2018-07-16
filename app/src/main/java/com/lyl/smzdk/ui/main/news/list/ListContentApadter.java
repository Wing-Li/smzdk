package com.lyl.smzdk.ui.main.news.list;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.lyl.smzdk.R;
import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.greendao.imp.HistoryImp;
import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class ListContentApadter extends BaseQuickAdapter<NewInfo, MyBaseViewHolder> {


    public ListContentApadter(@Nullable List<NewInfo> data, final int itemType) {
        super(data);

        setMultiTypeDelegate(new MultiTypeDelegate<NewInfo>() {
            @Override
            protected int getItemType(NewInfo newInfo) {
                return itemType;
            }
        });

        getMultiTypeDelegate()//
                .registerItemType(Constans.SHOW_ITEM_CONTENT_1, R.layout.item_main_news_content_1)//
                .registerItemType(Constans.SHOW_ITEM_CONTENT_2, R.layout.item_main_news_content_2)//
                .registerItemType(Constans.SHOW_ITEM_CONTENT_3, R.layout.item_main_news_content_3)//
                .registerItemType(Constans.SHOW_ITEM_CONTENT_4, R.layout.item_main_news_content_4);
    }

    @Override
    protected void convert(MyBaseViewHolder holder, NewInfo newInfo) {
        // 设置 标题 和 时间，理论上任何信息应该都有这两个
        holder.setText(R.id.item_main_content_title, newInfo.getTitle());
        holder.setText(R.id.item_main_content_time, newInfo.getTime());

        // 如果 简介 不是空，就设置简介
        if (!TextUtils.isEmpty(newInfo.getIntroduce())){
            holder.setText(R.id.item_main_content_introduce, newInfo.getIntroduce());
        }

        // 如果历史记录已经已经有了，就显示灰色
        if (HistoryImp.isHistoryExist(newInfo.getTitle(), newInfo.getUrl())) {
            holder.setTextColor(R.id.item_main_content_title, ContextCompat.getColor(mContext, R.color.black_flee_two));

            View view = holder.getView(R.id.item_main_content_introduce);
            if (null != view){
                holder.setTextColor(R.id.item_main_content_introduce, ContextCompat.getColor(mContext, R.color.black_flee_three));
            }
        }

        // 如果作者为空，则隐藏作者
        if (TextUtils.isEmpty(newInfo.getAuthor())) {
            holder.setVisible(R.id.item_main_content_author, false);
        } else {
            holder.setText(R.id.item_main_content_author, newInfo.getAuthor());
        }

        // 如果作者Icon为空，则隐藏
        if (TextUtils.isEmpty(newInfo.getAuthorIcon())){
            holder.setVisible(R.id.item_main_content_author_icon, false);
        } else {
            ImgUtils.loadCircle(mContext, newInfo.getAuthorIcon(), (ImageView) holder.getView(R.id.item_main_content_author_icon));
        }

        // 根据图片的存在与否，显示相应的图片
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
