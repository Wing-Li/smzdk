package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读者
 * Author: lyl
 * Date Created : 2017/11/27.
 */
public class DzImp {

    private static final String DUZHE_BASE = "http://www.duzhe.com/";
    private static final String DUZHE = DUZHE_BASE + "index.php?v=listing&cid=%1$s&page=%2$s";

    public List<NewMenu> getDzMenu() {
        List<NewMenu> newMenuList = new ArrayList<>();

        NewMenu menu = new NewMenu();
        menu.setName("文章");
        menu.setType("39");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("图书");
        menu.setType("38");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("话题");
        menu.setType("42");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("游戏");
        menu.setType("41");
        newMenuList.add(menu);

        return newMenuList;
    }

    public List<NewInfo> getInfo(String type, int p) {
        List<NewInfo> newInfoList = new ArrayList<NewInfo>();

        String url = String.format(DUZHE, type, p);
        try {
            LogUtils.d("读者：" + url);
            Document jsoup = Jsoup.connect(url).get();
            Elements post_list = jsoup.select("ul.post_list li");

            NewInfo info;
            for (Element element : post_list) {
                info = new NewInfo();
                // 图片
                Element img = element.select("div.img_warp img").first();
                if (img != null) {
                    info.setImage(img.attr("src"));
                }

                Element con_top = element.select("div.con_top").first();
                Element h3 = con_top.select("h3").first();
                // 标题
                info.setTitle(h3.text());
                // 链接
                String href = h3.select("a").first().attr("href");
                info.setUrl(DUZHE_BASE + href);
                // 时间
                Element time = con_top.select("div.info").first();
                info.setTime(time.text().trim());
                // 简介
                Element text = con_top.select("div.text").first();
                info.setIntroduce(text.text());

                newInfoList.add(info);
            }

            for (NewInfo i : newInfoList) {
                System.out.println(i.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }

    public String getDetail(String url) {
        try {
            Document jsoup = Jsoup.connect(url).get();
            Element left_p = jsoup.select("div.left_p").first();
            return left_p.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
