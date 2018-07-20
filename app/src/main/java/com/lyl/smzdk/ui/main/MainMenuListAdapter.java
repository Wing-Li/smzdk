package com.lyl.smzdk.ui.main;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.utils.ImgUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class MainMenuListAdapter extends BaseQuickAdapter<NewMenu, BaseViewHolder> {
    public MainMenuListAdapter(int layoutResId, @Nullable List<NewMenu> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewMenu newChannel) {
        holder.setText(R.id.item_main_menu_title, newChannel.getName());

        if (newChannel.getImageRes() != 0) {
            holder.setImageResource(R.id.item_main_menu_img, newChannel.getImageRes());
        } else {
            ImgUtils.loadRound(mContext, newChannel.getImage(), (ImageView) holder.getView(R.id.item_main_menu_img));
        }
    }
}
