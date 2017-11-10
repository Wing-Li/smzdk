package com.lyl.smzdk.ui.news;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.NewChannel;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class MainMenuListAdapter extends BaseQuickAdapter<NewChannel, BaseViewHolder> {
    public MainMenuListAdapter(int layoutResId, @Nullable List<NewChannel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewChannel newChannel) {
        holder.setText(R.id.item_main_menu_title, newChannel.getName());

        ImgUtils.loadCircle(mContext, newChannel.getImage(), (ImageView) holder.getView(R.id.item_main_menu_img));
    }
}
