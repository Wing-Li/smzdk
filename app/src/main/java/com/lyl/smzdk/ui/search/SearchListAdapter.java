package com.lyl.smzdk.ui.search;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    protected void convert(final BaseViewHolder holder, final BtInfo info) {
        holder.setText(R.id.item_search_title, info.getName());
        holder.setText(R.id.item_search_size, info.getSize());
        holder.setText(R.id.item_search_time, info.getTime());

        holder.getView(R.id.item_search_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context
                        .CLIPBOARD_SERVICE);
                if (clipboardManager != null) {
                    ClipData clipData = ClipData.newPlainText("test", info.getBtUrl());
                    clipboardManager.setPrimaryClip(clipData);

                    Toast.makeText(mContext.getApplicationContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext.getApplicationContext(), "粘贴板获取错误，请尝试直接下载", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.getView(R.id.item_search_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(info.getBtUrl()));
                mContext.startActivity(intent);
            }
        });
    }
}
