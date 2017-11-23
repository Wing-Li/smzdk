package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.news.NewInfo;
import com.lyl.smzdk.network.entity.news.NewMenu;
import com.lyl.smzdk.utils.DateUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信精选
 * Author: lyl
 * Date Created : 2017/11/21.
 */
public class WxImp {

    private static final String WX_MENU = "http://weixin.sogou.com/";
    private static final String WX_LIST_INFO = "http://weixin.sogou.com/pcindex/pc/%1$s/%2$s.html";

    public List<NewMenu> getWxMenu() {
        List<NewMenu> newMenuList = new ArrayList<>();

        try {
            Document jsoup = Jsoup.connect(WX_MENU).get();
            Elements fieed_box = jsoup.select("div[class=fieed-box]");
            Elements a = fieed_box.select("a");

            NewMenu menu;
            for (Element element : a) {
                menu = new NewMenu();
                // 获取 id ，根据 id 来获取详情页面
                String id = element.attr("id");
                menu.setType(id);
                // 目录的标题
                String text = element.text();
                menu.setName(text);

                if ("更多".equals(text)) {
                    menu = null;
                    continue;
                }

                newMenuList.add(menu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newMenuList;
    }

    /**
     * 获取微信精选的列表。 第0页的页数就是 类型
     *
     * @param type 类型
     * @param p    页数
     * @return
     */
    public List<NewInfo> getWxList(String type, int p) {
        List<NewInfo> newInfoList = new ArrayList<>();

        // 第0页的页数就是 类型
        String page = p == 0 ? type : String.valueOf(p);
        // 该类型的链接
        String url = String.format(WX_LIST_INFO, type, String.valueOf(page));
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
                Element time = sp.select("span.s2").first();
                String t = time.attr("t");
                String s = DateUtils.translateDate(Long.parseLong(t), System.currentTimeMillis() / 1000);
                info.setTime(s);

                newInfoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newInfoList;
    }
}
