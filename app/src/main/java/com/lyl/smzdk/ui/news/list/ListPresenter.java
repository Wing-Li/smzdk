package com.lyl.smzdk.ui.news.list;

import com.lyl.smzdk.network.entity.NewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class ListPresenter implements ListContract.Presenter {

    private ListContract.View mView;
    private List<NewInfo> mInfoList;

    public ListPresenter(ListContract.View view) {
        mView = view;
    }

    @Override
    public void loadData(String type) {
        mInfoList = new ArrayList<>();

        // 获取数据


        mView.setData(mInfoList);
    }
}
