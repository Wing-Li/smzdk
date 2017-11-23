package com.lyl.smzdk.ui.news.list.list;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: lyl
 * Date Created : 2017/11/23.
 */
public class MyViewHolder extends BaseViewHolder {

    public MyViewHolder(View view) {
        super(view);
    }

    /**
     * 先判空，再赋值。
     * 这样只要 id 一样，不同的 item 都能赋值。缺点是:如果 id  写错了，不会报错
     */
    @Override
    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = this.getView(viewId);
        if (view != null) {
            view.setText(value);
        }
        return this;
    }

    @Override
    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = this.getView(viewId);
        if (view != null) view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
}
