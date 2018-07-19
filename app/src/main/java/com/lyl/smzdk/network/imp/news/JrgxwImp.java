package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.entity.news.NewMenu;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * 居然搞笑网
 * Author: lyl
 * Date Created : 2018/7/11.
 */
public class JrgxwImp {

//    笑话段子
//    https://www.zbjuran.com/wenzixiaohua/list_1_2.html
//
//    搞笑图片
//    https://www.zbjuran.com/quweitupian/list_2_2.html
//
//    Gif动图
//    https://www.zbjuran.com/dongtai/list_4_2.html
//
//    内涵图片
//    https://www.zbjuran.com/xiegif/list_18_2.html

    private static final String SHENHUIFU_BASE = "https://www.zbjuran.com/";
    private static final String SHENHUIFU_URL = SHENHUIFU_BASE + "%1$s_%2$s.html";

    public List<NewMenu> getMenu() {
        List<NewMenu> newMenuList = new ArrayList<>();

        NewMenu menu = new NewMenu();
        menu.setName("段子");
        menu.setType("wenzixiaohua/list_1");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("趣图");
        menu.setType("quweitupian/list_2");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("动图");
        menu.setType("dongtai/list_4");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("内涵图");
        menu.setType("xiegif/list_18");
        newMenuList.add(menu);

        return newMenuList;
    }

    public List<NewInfo> getInfo(String type, int p) {
        List<NewInfo> newInfoList = new ArrayList<NewInfo>();

        String url = String.format(SHENHUIFU_URL, type, p + 1);
        try {
            Connection connect = Jsoup.connect(url).validateTLSCertificates(false);

            Document jsoup = connect.get();
            Elements post_list = jsoup.select("div.wrapper div.main div.item");

            NewInfo info;
            for (Element element : post_list) {
                info = new NewInfo();

                // 标题
                Element title = element.select("h3 a").first();
                if (title == null) continue;
                info.setTitle(title.text());
                // 日期
                Element date = element.select("span").first();
                info.setTime(date.text());

                // 内容
                Element content = element.select("div.text p span").first();
                if (content != null)
                    info.setIntroduce(content.text());

                // 图片
                Element img = element.select("div.text p img").first();
                if (img != null)
                    info.setImage(SHENHUIFU_BASE + img.attr("src"));

                newInfoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }
}
