package com.lyl.smzdk.ui.news.list;

import com.lyl.smzdk.network.entity.NewMenu;

import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class MenuContract {

    interface View {

        /**
         * 将目录设置进列表
         */
        void initMenueView(List<NewMenu> menuList);

        /**
         * 加载目录时的进度圈
         */
        void setLoading();
    }

    interface Presenter {

        /**
         * 获取目录数据
         */
        void initMenuData(int type);
    }
}
