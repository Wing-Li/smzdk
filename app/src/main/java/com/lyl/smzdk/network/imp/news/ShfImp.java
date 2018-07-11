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
 * 神回复
 * Author: lyl
 * Date Created : 2018/7/11.
 */
public class ShfImp {

    private static final String SHENHUIFU_BASE = "http://m.shenhuif.com";
    private static final String SHENHUIFU_URL = SHENHUIFU_BASE + "/%1$s_%2$s.html";

    public List<NewMenu> getShfMenu() {
        List<NewMenu> newMenuList = new ArrayList<>();

        NewMenu menu = new NewMenu();
        menu.setName("段子");
        menu.setType("text");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("趣图");
        menu.setType("pic");
        newMenuList.add(menu);

        menu = new NewMenu();
        menu.setName("动图");
        menu.setType("gif");
        newMenuList.add(menu);

        return newMenuList;
    }

    public List<NewInfo> getInfo(String type, int p) {
        List<NewInfo> newInfoList = new ArrayList<NewInfo>();

        String url = String.format(SHENHUIFU_URL, type, p + 1);
        try {
            LogUtils.d("神回复：" + url);
            Document jsoup = Jsoup.connect(url).get();
            Elements post_list = jsoup.select("div.warp li.joke-view");

            NewInfo info;
            for (Element element : post_list) {
                info = new NewInfo();

                Element u_info = element.select("div.u-info").first();
                // 作者头像
                String authorIcon = u_info.select("a.j-u-avatar img").attr("src");
                info.setAuthorIcon(authorIcon);
                // 作者
                String authorName = u_info.select("p.j-u-name a").first().text();
                info.setAuthor(authorName.substring(1, authorName.indexOf("\"")));

                // 标题
                Element title = u_info.select("p.j-u-name2 a").first();
                info.setTitle(title.text());
                // 内容
                Element content_txt = u_info.select("div.j-content div.content-txt").first();
                info.setIntroduce(content_txt.text());
                // 链接
                String href = title.attr("href");
                info.setUrl(SHENHUIFU_BASE + href);

                // 图片
                Element j_content = element.select("div.j-content").first();
                if (j_content != null) {
                    Element img = j_content.select("img").first();
                    info.setImage(SHENHUIFU_BASE + img.attr("src"));
                }

                newInfoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }
}
