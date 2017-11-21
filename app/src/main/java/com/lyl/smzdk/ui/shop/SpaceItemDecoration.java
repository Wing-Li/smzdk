package com.lyl.smzdk.ui.shop;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyl.smzdk.utils.LogUtils;

/**
 * Author: lyl
 * Date Created : 2017/11/16.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        LogUtils.d("当前位置：" + position);

        //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
//        if ((position + 1) % 2 == 1) {
//            outRect.left = 0;
//            outRect.bottom = space;
//        }else {
//            //不是第一个的格子都设一个左边和底部的间距
//            outRect.left = space;
//            outRect.bottom = space;
//        }

        outRect.left = space;
        outRect.right = space;
        if (position != 0 && position != 1) {
            outRect.top = 2 * space;
        } else {
            outRect.top = space;
        }
    }
}
