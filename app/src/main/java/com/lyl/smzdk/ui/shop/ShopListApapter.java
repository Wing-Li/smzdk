package com.lyl.smzdk.ui.shop;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.ShopInfo;
import com.lyl.smzdk.utils.ImgUtils;
import com.lyl.smzdk.utils.MyUtils;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/15.
 */
public class ShopListApapter extends BaseQuickAdapter<ShopInfo, BaseViewHolder> {

    public ShopListApapter(int layoutResId, @Nullable List<ShopInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, ShopInfo shopInfo) {
        holder.setText(R.id.item_shop_title, shopInfo.getTitle())//
                .setText(R.id.item_shop_price, shopInfo.getSalePrice());
        ImgUtils.load(mContext, shopInfo.getThumbs(), (ImageView) holder.getView(R.id.item_shop_img));

        holder.getView(R.id.item_shop_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.showT(mContext, "购买" + holder.getLayoutPosition());
            }
        });
    }
}
