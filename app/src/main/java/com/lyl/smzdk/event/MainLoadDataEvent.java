package com.lyl.smzdk.event;

/**
 * 重复点击主页的 BottomBar 上的按钮，刷新主页面的数据
 * Author: lyl
 * Date Created : 2017/11/10.
 */
public class MainLoadDataEvent {
    public int page;

    public MainLoadDataEvent(int page) {
        this.page = page;
    }
}
