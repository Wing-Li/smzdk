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
 * 挖吧
 * http://www.360wa.com
 * Author: lyl
 * Date Created : 2018/7/31
 */
public class WabaImp {

    private static final String WABA_BASE = "http://www.360wa.com";
    private static final String WABA_URL = WABA_BASE + "/%1$s/%2$s";

    public List<NewMenu> getMenu() {
        List<NewMenu> newMenuList = new ArrayList<>();

        NewMenu menu = new NewMenu();
//        menu.setName("推荐");
//        menu.setType("nearest");
//        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("段子");
        menu.setType("duanzi/latest");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("搞笑");
        menu.setType("gaoxiaotu/latest");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("内涵");
        menu.setType("wuduanzi/latest");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("动图");
        menu.setType("gaoxiaogif/latest");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("极品图");
        menu.setType("jipintu/latest");
        newMenuList.add(menu);

        return newMenuList;
    }

    public List<NewInfo> getInfo(String type, int p) {
        List<NewInfo> newInfoList = new ArrayList<NewInfo>();

        String url = String.format(WABA_URL, type, p + 1);
        try {
            Document jsoup = Jsoup.connect(url).get();
            Elements post_list = jsoup.select("div.body1 div.leftcol div[id=recent] div.p1");

            NewInfo info;
            for (Element element : post_list) {
                info = new NewInfo();

                Element body = element.select("div.p_left").first();

                Elements bodyA = body.select("a p");
                Element title = bodyA.get(0);
                // 标题
                if (title != null && title.text().length() > 0){
                    info.setTitle(title.text());
                }
                // 内容
                Element content = bodyA.get(1);
                if (content != null && content.text().length() > 0){
                    info.setIntroduce(content.text());
                }
                // 图片
                Element imgElement = content.select("img").first();
                if (imgElement != null){
                    info.setImage(imgElement.attr("src"));
                }

                // 作者
                Element authorName = body.select("div.web a").first();
                if (authorName != null && authorName.text().length() > 0){
                    info.setAuthor(authorName.text());
                }

                newInfoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }
}
