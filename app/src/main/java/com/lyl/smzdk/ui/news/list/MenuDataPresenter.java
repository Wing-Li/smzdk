package com.lyl.smzdk.ui.news.list;

import com.lyl.smzdk.network.entity.news.NewMenu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: lyl
 * Date Created : 2017/11/20.
 */
public class MenuDataPresenter implements MenuContract.Presenter {

    private MenuContract.View mView;

    private List<NewMenu> mNewMenuList;

    public MenuDataPresenter(MenuContract.View view) {
        mView = view;
    }

    @Override
    public void initMenuData(int type) {
        mNewMenuList = new ArrayList<>();

        // 初始化 目录的数据

        mView.setMenuTab(mNewMenuList);
    }
}
