package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.news.LzsInfo;
import com.lyl.smzdk.network.entity.news.NewMenu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 百度百科 - 冷知识
 * Author: lyl
 * Date Created : 2018/7/10.
 */
public class LzsImp {

    private static final String LENGZHISHI_URL = "https://wapbaike.baidu.com/api/vbaike/knowledgeList?count=10&page=%2$s&keyWord=%1$s";

    public List<NewMenu> getLzsMenu() {
        List<NewMenu> newMenuList = new ArrayList<>();

        NewMenu menu = new NewMenu();
        menu.setName("全部");
        menu.setType("");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("生活");
        menu.setType("生活");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("社会");
        menu.setType("社会");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("健康");
        menu.setType("健康");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("人物");
        menu.setType("人物");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("科学");
        menu.setType("科学");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("历史");
        menu.setType("历史");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("文化");
        menu.setType("文化");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("娱乐");
        menu.setType("娱乐");
        newMenuList.add(menu);

        return newMenuList;
    }

    /**
     * 获取冷知识的列表
     */
    public Call<List<LzsInfo>> getInfo(String type, int page){
        return Network.getLzsApi().getList(type, page);
    }
}
