package com.lyl.smzdk.ui.search;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

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
    protected void convert(final BaseViewHolder holder, BtInfo info) {
        holder.setText(R.id.item_search_title, info.getName());

        holder.getView(R.id.item_search_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext.getApplicationContext(), "复制了" + holder.getAdapterPosition() + "个", Toast
                        .LENGTH_SHORT).show();
            }
        });
    }
}
