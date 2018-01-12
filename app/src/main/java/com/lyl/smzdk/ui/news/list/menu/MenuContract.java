package com.lyl.smzdk.ui.news.list.menu;

import com.lyl.smzdk.network.entity.news.NewMenu;

import java.util.List;

/**
 * 目录页面 的抽象
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class MenuContract {

    interface View {

        /**
         * 将目录设置进列表
         */
        void setMenuTab(List<NewMenu> menuList);
    }

    interface Presenter {

        /**
         * 获取目录数据
         */
        void initMenuData(String type);
    }
}
