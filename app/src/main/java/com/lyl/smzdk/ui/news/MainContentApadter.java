package com.lyl.smzdk.ui.news;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.NewInfo;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class MainContentApadter extends BaseQuickAdapter<NewInfo, BaseViewHolder> {
    public MainContentApadter(int layoutResId, @Nullable List<NewInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewInfo newInfo) {
        holder.setText(R.id.item_main_content_title, newInfo.getTitle());
        holder.setText(R.id.item_main_content_introduce, newInfo.getIntroduce());

        ImgUtils.load(mContext, newInfo.getImage(), (ImageView) holder.getView(R.id.item_main_content_image));
    }
}
