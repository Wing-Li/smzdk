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

    public static int timeout = 10000;

    /**
     * 种子搜
     */
    public static List<BtInfo> getList(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        try {
            content = URLEncoder.encode(content,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://m.zhongziso.com/list/" + content + "/" + page;
        try {
            LogUtils.d("BT-1:" + url);
            Connection connect = Jsoup.connect(url);
            connect.timeout(timeout);
            Document jsoup = connect.get();
            Elements list_group = jsoup.select("div.panel-body").select("ul.list-group");

            BtInfo info;
            for (Element element : list_group) {
                info = new BtInfo();
                // 标题
                Element title = element.select("a.text-success").first();
                info.setName(title.text());

                Element dl = element.select("dl.list-code").first();
                // 大小
                Element size = dl.select("dd.text-size").first();
                info.setSize(size.text());
                // 时间
                Element time = dl.select("dd.text-time").first();
                info.setTime(time.text());

                // 链接
                Element bt = element.select("dl.down a").first();
                info.setBtUrl(bt.attr("href"));

                infoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoList;
    }

    /**
     * 屌丝搜
     */
    public static List<BtInfo> getList2(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = "https://www.diaosisou.org/list/" + content + "/" + page + "/rala_d";
        try {
            LogUtils.d("BT-2:" + url);
            Connection connect = Jsoup.connect(url);
            // http://www.diaosisou.org/list/%E7%BE%8E%E9%A3%9F/1/rala_d
//            Upgrade-Insecure-Requests:1
//            User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
// Chrome/62.0.3202.94 Safari/537.36
            connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            connect.timeout(timeout);
            connect.ignoreContentType(true).ignoreHttpErrors(true);
            Document jsoup = connect.get();
            Elements list_group = jsoup.select("div.main").select("ul.mlist").select("li");

            BtInfo info;
            for (Element element : list_group) {
                info = new BtInfo();
                // 标题
                Element title = element.select("div.T1 a").first();
                info.setName(title.text());

                Element dl = element.select("dl.BotInfo").first();
                // 大小
                Element size = dl.select("dt span").first();
                info.setSize(size.text());
                // 时间
                Element time = dl.select("dt span").get(2);
                info.setTime(time.text());

                // 链接
                Element bt = element.select("div.dInfo a").first();
                info.setBtUrl(bt.attr("href"));

                infoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoList;
    }

    /**
     * runbt
     */
    public static List<BtInfo> getList3(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = "https://www.runbt.co/list/" + content + "/" + page;
        try {
            LogUtils.d("BT-3:" + url);
            Connection connect = Jsoup.connect(url);
            connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            connect.timeout(timeout);
            connect.ignoreContentType(true).ignoreHttpErrors(true);
            Document jsoup = connect.get();
            Elements list_group = jsoup.select("div.main").select("ul.mlist").select("li");

            BtInfo info;
            for (Element element : list_group) {
                info = new BtInfo();
                // 标题
                Element title = element.select("div.T1 a").first();
                info.setName(title.text());

                Element dl = element.select("dl.BotInfo").first();
                // 大小
                Element size = dl.select("dt span").first();
                info.setSize(size.text());
                // 时间
                Element time = dl.select("dt span").get(2);
                info.setTime(time.text());

                // 链接
                Element bt = element.select("div.dInfo a").first();
                info.setBtUrl(bt.attr("href"));

                infoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoList;
    }

    /**
     * 种子搜
     */
    public static List<BtInfo> getList4(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = "http://zhongzicili.me/zhongzi/" + content + "/" + page + "-0-2.html";
        try {
            LogUtils.d("BT-4:" + url);
            Connection connect = Jsoup.connect(url);
            connect.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            connect.timeout(timeout);
            Document jsoup = connect.get();
            Elements list_group = jsoup.select("div.content").select("div.list-area").select("dl");

            BtInfo info;
            for (Element element : list_group) {
                info = new BtInfo();
                // 标题
                Element title = element.select("dt a").first();
                info.setName(title.text());

                Element attr = element.select("dd.attr").first();
                // 大小
                Element size = attr.select("span").get(1).select("b").first();
                info.setSize(size.text());
                // 时间
                Element time = attr.select("span").first().select("b").first();
                info.setTime(time.text());

                // 链接
                Element bt = attr.select("span").last().select("a").first();
                info.setBtUrl(bt.attr("href"));

                infoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return infoList;
    }
}
