package com.lyl.smzdk.ui.shop;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.ShopInfo;
import com.lyl.smzdk.utils.ImgUtils;

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
        holder.setText(R.id.item_shop_title, shopInfo.getTitle())// 标题
                .setText(R.id.item_shop_price, shopInfo.getPrice())// 折后售价
                .setText(R.id.item_shop_sale_price, mContext.getResources().getString(R.string.shop_sale_price_txt,
                        shopInfo.getSalePrice()))// 优惠的价格
                .setText(R.id.item_shop_original_price, shopInfo.getOriginalPrice())// 原价
                .setText(R.id.item_shop_salesVolume, mContext.getResources().getString(R.string.shop_salesvolume,
                        shopInfo.getSalePrice()))// 销量
        ;

        ImgUtils.load(mContext, shopInfo.getThumbs(), (ImageView) holder.getView(R.id.item_shop_img));
    }
}
