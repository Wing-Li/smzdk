package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.news.NewMenu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/11/22.
 */
public class ZhImp {

    private static final String ZH_MENU = "http://weixin.sogou.com/";
    private static final String ZH_LIST_INFO = "http://weixin.sogou.com/pcindex/pc/%1$s/%2$s.html";

    public static List<NewMenu> getMenu(){
        List<NewMenu> newMenuList = new ArrayList<>();

        try {
            NewMenu menu;

            Document jsoup = Jsoup.connect(ZH_MENU).get();
            Elements tab_box = jsoup.select("div[class=tab-box]");
            Element recommend_tab = tab_box.select("span[id=recommend_tab]").first();
            // 编辑推荐 -- recommend
            // http://zhihu.sogou.com/include/pc/2/recommend/recommend1.html
            menu = new NewMenu();
            menu.setType("recommend");
            menu.setName(recommend_tab.text());
            newMenuList.add(menu);
            // 今日最热 -- hot
            // http://zhihu.sogou.com/include/pc/3/hot/hot1.html
            Element hot_tab = tab_box.select("span[id=hot_tab]").first();
            menu = new NewMenu();
            menu.setType("hot");
            menu.setName(hot_tab.text());
            newMenuList.add(menu);
            // 话题 -- topic
            // http://zhihu.sogou.com/include/pc/3/topic/topic17_1.html
            Elements a = jsoup.select("div[class=main-list-box] div[class=tab-lv2] a");
            for (Element element : a) {
                menu = new NewMenu();
                // 获取 position ，根据 position 来获取详情页面
                String id = element.attr("position");
                menu.setType(id);
                // 目录的标题
                String text = element.text();
                menu.setName(text);
                newMenuList.add(menu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newMenuList;
    }

}
