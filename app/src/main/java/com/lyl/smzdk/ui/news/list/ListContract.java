package com.lyl.smzdk.ui.news.list;

import com.lyl.smzdk.network.entity.NewInfo;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class ListContract {

    interface View {
        /**
         * 将数据设置进列表
         */
        void setData(List<NewInfo> newInfos);

        /**
         * 加载目录时的进度圈
         */
        void setLoading();
    }

    interface Presenter {
        /**
         * 获取数据
         */
        void loadData(String type);
    }
}