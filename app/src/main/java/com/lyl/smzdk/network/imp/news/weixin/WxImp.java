package com.lyl.smzdk.network.imp.news.weixin;

import com.lyl.smzdk.network.entity.news.weixin.WxInfo;
import com.lyl.smzdk.network.entity.news.weixin.WxMenu;

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

    public List<WxMenu> getWxMenu() {
        List<WxMenu> wxMenuList = new ArrayList<>();

        try {
            Document jsoup = Jsoup.connect(WX_MENU).get();
            Elements fieed_box = jsoup.select("div[class=fieed-box]");
            Elements a = fieed_box.select("a");

            WxMenu wxMenu;
            for (Element element : a) {
                wxMenu = new WxMenu();
                // 获取 id ，根据 id 来获取详情页面
                String id = element.attr("id");
                wxMenu.setType(id);
                // 目录的标题
                String text = element.text();
                wxMenu.setName(text);
                wxMenuList.add(wxMenu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wxMenuList;
    }

    /**
     * 获取微信精选的列表。 第0页的页数就是 类型
     *
     * @param type 类型
     * @param p    页数
     * @return
     */
    public List<WxInfo> getWxList(String type, int p) {
        List<WxInfo> wxInfoList = new ArrayList<>();

        // 第0页的页数就是 类型
        String page = p == 0 ? type : String.valueOf(p);
        // 该类型的链接
        String url = String.format(WX_LIST_INFO, type, String.valueOf(page));
        try {
            Document jsoup = Jsoup.connect(url).get();
            Elements news_list = jsoup.select("li");

            WxInfo wxInfo;
            for (Element element : news_list) {
                wxInfo = new WxInfo();

                // 图片
                Element img = element.select("div[class=img-box] a img").first();
                String imgSrc = img.attr("src");
                wxInfo.setImg(imgSrc);

                Element txt_box = element.select("div[class=txt-box]").first();
                // 标题、链接
                Element a = txt_box.select("h3 a").first();
                String title = a.text();
                String href = a.attr("href");
                wxInfo.setTime(title);
                wxInfo.setHref(href);
                // 内容
                Element txt_info = txt_box.select("p[class=txt-info]").first();
                String msg = txt_info.text();
                wxInfo.setMsg(msg);
                // 作者
                Element sp = txt_box.select("div[class=s-p]").first();
                String author = sp.select("a").text();
                wxInfo.setAuthor(author);
                // 时间
                Element time = sp.select("span").first();
                wxInfo.setTime(time.text());

                wxInfoList.add(wxInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return wxInfoList;
    }
}
