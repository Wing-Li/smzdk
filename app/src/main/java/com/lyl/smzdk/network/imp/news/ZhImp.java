package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.utils.LogUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 知乎精选
 * Author: lyl
 * Date Created : 2017/11/22.
 */
public class ZhImp {

    private static final String ZH_MENU = "http://zhihu.sogou.com/";
    private static final String ZH_LIST_INFO = "http://zhihu.sogou.com/include/pc/3/%1$s/%2$s.html";

    public List<NewMenu> getZhMenu() {
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
            Elements a = jsoup.select("div[class=main-list-box] div[id=topic_assort] a");
            for (Element element : a) {
                menu = new NewMenu();
                // 目录的标题
                String text = element.text();
                menu.setName(text);
                if ("展开".equals(text) || "收起".equals(text)) {
                    continue;
                }

                // 获取 position ，根据 position 来获取详情页面
                String id = element.attr("position");
                menu.setType(id);
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
    public List<NewInfo> getZhList(String type, int p) {
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
            LogUtils.d("请求链接：" + url);
            Document jsoup = Jsoup.connect(url).get();
            Elements news_list = jsoup.select("li");

            NewInfo info;
            for (Element element : news_list) {
                info = new NewInfo();

                Element title = element.select("p.tit a").first();
                // 标题
                info.setTitle(title.text());
                // 链接
                info.setUrl(title.attr("href"));

                Element txt_box = element.select("div.td-b div.txt-box").first();
                // 点赞数
                Element laud = txt_box.select("p.p1 span").first();
                info.setLaudNum(laud.text());
                // 作者
                Element author = txt_box.select("p.p1 a").first();
                info.setAuthor(author.ownText());
                // 作者链接
                info.setAuthorUrl(author.attr("href"));
                // 作者签名
                Element authorSgin = txt_box.select("p.p1").first();
                info.setAuthorSignature(authorSgin.ownText());

                // 简介
                Element msg = txt_box.select("p.p2 a").first();
                info.setIntroduce(msg.text());

                // 如果有图片的话，获取图片
                Element img = element.select("div.td-b div.img-box img").first();
                if (img != null) {
                    info.setImage(img.attr("src"));
                }

                newInfoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }

    /**
     * 获取 文章详情的网页， 并且修改指定内容
     * @param url
     * @return
     */
    public String getDetail(String url) {
        try {
            Connection connect = Jsoup.connect(url);
            connect.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            connect.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            Document jsoup = connect.get();

            return jsoup.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
