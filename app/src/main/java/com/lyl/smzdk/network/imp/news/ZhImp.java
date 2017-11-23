package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.news.NewInfo;
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

    private static final String ZH_MENU = "http://zhihu.sogou.com/";
    private static final String ZH_LIST_INFO = "http://zhihu.sogou.com/include/pc/3/%1$s/%2$s.html";

    public List<NewMenu> getMenu() {
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

    /**
     * 获取知乎精选的列表。
     *
     * @param type 类型
     * @param p    页数
     * @return
     */
    public List<NewInfo> getWxList(String type, int p) {
        List<NewInfo> newInfoList = new ArrayList<>();

        String url;
        if ("recommend".equals(type) || "hot".equals(type)) {
            // http://zhihu.sogou.com/include/pc/2/recommend/recommend1.html
            // http://zhihu.sogou.com/include/pc/3/hot/hot2.html
            url = String.format(ZH_LIST_INFO, type, type + p);
        } else {
            // http://zhihu.sogou.com/include/pc/3/topic/topic17_1.html
            url = String.format(ZH_LIST_INFO, "topic", "topic" + type + "_" + p);
        }

        try {
            Document jsoup = Jsoup.connect(url).get();
            Elements news_list = jsoup.select("li");

            NewInfo info;
            for (Element element : news_list) {
                info = new NewInfo();

                // 图片
                Element img = element.select("div[class=img-box] a img").first();
                String imgSrc = img.attr("src");
                info.setImage(imgSrc);

                Element txt_box = element.select("div[class=txt-box]").first();
                // 标题、链接
                Element a = txt_box.select("h3 a").first();
                String title = a.text();
                String href = a.attr("href");
                info.setTitle(title);
                info.setUrl(href);
                // 内容
                Element txt_info = txt_box.select("p[class=txt-info]").first();
                String msg = txt_info.text();
                info.setIntroduce(msg);
                // 作者
                Element sp = txt_box.select("div[class=s-p]").first();
                String author = sp.select("a").text();
                info.setAuthor(author);
                // 时间
                Element time = sp.select("span").first();
                info.setTime(time.text());

                newInfoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }

}
