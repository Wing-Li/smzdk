package com.lyl.smzdk.ui.main.news.list;

import com.lyl.smzdk.network.entity.news.NewInfo;

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
    }

    interface Presenter {
        void reLoadData(String channelType, String type);

        /**
         * 获取数据
         */
        void loadData(String channelType, String type);
    }
}
