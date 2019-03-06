package com.lyl.smzdk.network.imp.bt;


import com.lyl.smzdk.network.entity.bt.BtInfo;
import com.lyl.smzdk.utils.LogUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/12/7.
 */
public class BtImp {

    private static int timeout = 60000;
    private static final String MAGNET_BASE = "magnet:?xt=urn:btih:";

    public static List<BtInfo> getList(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // https://btstation.com/search/all/%E8%8B%8D%E4%BA%95%E7%A9%BA/0
        String url = "https://btstation.com/search/all/" + content + "/" + (page - 1);
        try {
            LogUtils.d("BT-1:" + url);
            Connection connect = Jsoup.connect(url);
            connect.timeout(timeout);
            Document jsoup = connect.get();
            Elements list_group = jsoup.select("main div.section ul li");

            BtInfo info;
            for (Element element : list_group) {
                info = new BtInfo();
                // 标题
                Element title = element.select("div.title a").first();
                info.setName(title.text());

                // 大小
                Element size = element.select("div.size").first();
                info.setSize(size.text());
                // 时间
                // Element time = element.select("dd.text-time").first();
                // info.setTime(time.text());

                // 链接
                Element bt = element.select("div.details-info").get(0).select("a").first();
                info.setBtUrl(bt.text().replace("\"", ""));

                infoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoList;
    }

    // https://www.cilimao.cc/search?word=Gachi-1151&page=1
    private static final String BTDALU_BASE = "https://www.cilimao.cc";
    private static final String BTDALU_URL = BTDALU_BASE + "/search?word=%1$s&page=%2$s";

    public static List<BtInfo> getList2(String type, int p) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = String.format(BTDALU_URL, type, p);
        LogUtils.d("BT-3:" + url);
        try {
            Connection connect = Jsoup.connect(url).validateTLSCertificates(false);

            Document jsoup = connect.get();
            Elements post_list = jsoup.select("div.Search__result___2S94i");

            BtInfo info;
            for (Element element : post_list) {
                info = new BtInfo();
                // 标题
                Element title = element.select("a").first();
                info.setName(title.text());

                Element attr = element.select("div.Search__result_information___1qNVg").first();
                // 大小 文件数量 创建时间
                info.setSize(attr.text().replace("[磁力链接]", "").replace("文件", "").replace("创建", ""));
//                // 时间
//                Element time = bodys.get(2).select("span").first();
//                info.setTime(time.text());

                // 链接
                String btUrl = title.attr("href");
                int startIndex = btUrl.lastIndexOf("/");
                int endIndex = btUrl.lastIndexOf("?");
                btUrl = btUrl.substring(startIndex + 1, endIndex);
                info.setBtUrl(MAGNET_BASE + btUrl);

                infoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return infoList;
    }

    public static List<BtInfo> getList3(String type, int p) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        try {
            type = URLEncoder.encode(type,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // http://sosocili.org/soso/%1$s/%2$s-0-0.html
        String url = String.format("http://sosocili.org/soso/%1$s/%2$s-0-0.html", type, p);
        LogUtils.d("BT-2:" + url);
        try {
            Connection connect = Jsoup.connect(url);

            Document jsoup = connect.get();
            LogUtils.d(jsoup.html());
            Elements post_list = jsoup.select("div.search_list dl");

            BtInfo info;
            for (Element element : post_list) {
                info = new BtInfo();
                // 标题
                Element dt_a = element.select("dt a").first();
                info.setName(dt_a.text());

                Elements dd_info = element.select("dd.info span");
                // 大小
                info.setSize(dd_info.get(2).select("b").first().text());
                // 时间
                info.setTime(dd_info.get(0).select("b").first().text());
                // 链接
                Element aUrl = dd_info.get(5).select("a").first();
                info.setBtUrl(aUrl.attr("href"));

                infoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return infoList;
    }

    private static final String SHABIBA_BASE = "https://www.findcl.co";
    private static final String SHABIBA_URL = SHABIBA_BASE + "/list?q=%1$s&page=%2$s";

    /**
     * 傻逼吧
     */
    public static List<BtInfo> getList4(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = String.format(SHABIBA_URL, content, page);
        LogUtils.d("BT-4:" + url);
        try {
            Connection connect = Jsoup.connect(url);
            Document jsoup = connect.get();
            // div.list-container
            Elements list_group = jsoup.select("ul.list li");

            BtInfo info;
            for (Element element : list_group) {
                info = new BtInfo();
                // 标题
                String title = element.select("a").attr("title");
                info.setName(title);

                Elements attr = element.select("div.info div");
                // 大小
                Element size = attr.get(1).select("strong").first();
                info.setSize(size.text());
                // 时间
                Element time = attr.get(2).select("strong").first();
                info.setTime(time.text());

                // 链接
                String detailUrl = SHABIBA_BASE + element.select("a").attr("href");
                Element btUrl = Jsoup.connect(detailUrl).get().select("code.break-word").first();
                info.setBtUrl(btUrl.text());

                infoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoList;
    }
}
