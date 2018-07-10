package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lyl
 * Date Created : 2018/7/10.
 */
public class XdImp {

    private static final String DIANDU_URL = "http://gank.io/xiandu/%1$s/page/%2$s";

    public List<NewMenu> getXdMenu() {
        List<NewMenu> newMenuList = new ArrayList<>();

        NewMenu menu = new NewMenu();
        menu.setName("科技资讯");
        menu.setType("wow");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("趣味软件/游戏");
        menu.setType("apps");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("装备党");
        menu.setType("imrich");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("草根新闻");
        menu.setType("funny");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("创业新闻");
        menu.setType("diediedie");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("独立思想");
        menu.setType("thinking");
        newMenuList.add(menu);

//        menu = new NewMenu();
//        menu.setName("Android");
//        menu.setType("android");
//        newMenuList.add(menu);

//        menu = new NewMenu();
//        menu.setName("iOS");
//        menu.setType("iOS");
//        newMenuList.add(menu);

//        menu = new NewMenu();
//        menu.setName("团队博客");
//        menu.setType("teamblog");
//        newMenuList.add(menu);

        return newMenuList;
    }

    public List<NewInfo> getInfo(String type, int p) {
        List<NewInfo> newInfoList = new ArrayList<NewInfo>();

        String url = String.format(DIANDU_URL, type, p + 1);
        try {
            LogUtils.d("闲读：" + url);
            Document jsoup = Jsoup.connect(url).get();
            Elements post_list = jsoup.select("div.xiandu_items div.xiandu_item");

            NewInfo info;
            for (Element element : post_list) {
                info = new NewInfo();

                Element xiandu_left = element.select("div.xiandu_left").first();
                Elements content = xiandu_left.select("a.site-title");
                // 链接
                String href = content.attr("href");
                info.setUrl(href);
                // 标题
                info.setTitle(content.text().trim());
                // 时间
                Element time = xiandu_left.select("span small").first();
                info.setTime(time.text().trim());

                Element xiandu_right = element.select("div.xiandu_right").first();
                // 作者
                String author = xiandu_right.select("a").attr("title");
                info.setAuthor(author);
                // 作者 Icon
                String authorIcon = xiandu_right.select("img").attr("src");
                info.setAuthorIcon(authorIcon);

                newInfoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }
}
