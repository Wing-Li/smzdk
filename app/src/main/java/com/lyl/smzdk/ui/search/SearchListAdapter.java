package com.lyl.smzdk.ui.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.entity.bt.BtInfo;

import java.util.List;

public class SearchListAdapter extends BaseQuickAdapter<BtInfo, BaseViewHolder> {


    public SearchListAdapter(int layoutResId, @Nullable List<BtInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final BtInfo info) {
        holder.setText(R.id.item_search_title, info.getName())//
                .setText(R.id.item_search_size, info.getSize())//
                .setText(R.id.item_search_time, info.getTime())//
                .addOnClickListener(R.id.item_search_copy)//
                .addOnClickListener(R.id.item_search_download)//
                .addOnClickListener(R.id.item_search_share);
    }
}
