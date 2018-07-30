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

    public static int timeout = 20000;

    /**
     * 种子搜
     */
    public static List<BtInfo> getList(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://m.zhongziso.com/list_ctime/" + content + "/" + page;
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

    private static final String IDOPE_BASE = "https://idope.se";
    private static final String IDOPE_URL = IDOPE_BASE + "/torrent-list/%1$s/?p=%2$s";
    private static final String MAGNET_BASE = "magnet:?xt=urn:btih:";


    /**
     * idope
     */
    public static List<BtInfo> getList2(String type, int p) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = String.format(IDOPE_URL, type, p + 1);
        try {
            Connection connect = Jsoup.connect(url);

            Document jsoup = connect.get();
            Elements post_list = jsoup.select("div[id=div2] div[id=div2child] div.resultdiv");

            BtInfo info;
            for (Element element : post_list) {
                info = new BtInfo();
                // 标题
                Element title = element.select("div.resultdivtop div.resultdivtopname").first();
                info.setName(title.text());

                Element attr = element.select("div.resultdivbotton").first();
                // 大小
                Element size = attr.select("div.resultdivbottonlength").first();
                info.setSize(size.text());
                // 时间
                Element time = attr.select("div.resultdivbottontime").first();
                info.setTime(time.text());

                // 链接
                Element btUrl = attr.select("div.hideinfohash").first();
                info.setBtUrl(MAGNET_BASE + btUrl.text());

                infoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return infoList;
    }

    // https://www.btdalu.com/list/%E8%8B%8D%E4%BA%95%E7%A9%BA/1/2/0
    private static final String BTDALU_BASE = "https://www.btdalu.com";
    /**
     * %1$s ：内容
     * %2$s ：页数
     * 第三个数字：结果排名方式：1.相关性；2.热度；3.大小；4.最近
     * 第四个数字：1：必须包含所有关键字； 0：不必包含所有
     */
    private static final String BTDALU_URL = BTDALU_BASE + "/list/%1$s/%2$s/2/1";

    /**
     * BT大陆
     */
    public static List<BtInfo> getList3(String type, int p) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = String.format(BTDALU_URL, type, p + 1);
        try {
            Connection connect = Jsoup.connect(url).validateTLSCertificates(false);

            Document jsoup = connect.get();
            Elements post_list = jsoup.select("div.list-group div.container div.panel-info");

            BtInfo info;
            for (Element element : post_list) {
                info = new BtInfo();
                // 标题
                Element title = element.select("h3.panel-title").first();
                info.setName(title.text());

                Element attr = element.select("div.panel-body").first();
                Elements bodys = attr.select("div.col-xs-12");
                // 大小
                Element size = bodys.get(0).select("span").first();
                info.setSize(size.text());
                // 时间
                Element time = bodys.get(2).select("span").first();
                info.setTime(time.text());

                // 链接
                String btUrl = bodys.get(3).select("a").attr("onclick");
                ;
                btUrl = btUrl.substring(44, 84);
                info.setBtUrl(MAGNET_BASE + btUrl);

                infoList.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return infoList;
    }

    private static final String SHABIBA_BASE = "http://findcl.com";
    private static final String SHABIBA_URL = SHABIBA_BASE + "/list?q=%1$s&page=%1$s";

    /**
     * 傻逼吧
     */
    public static List<BtInfo> getList4(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = String.format(SHABIBA_URL, content, page + 1);
        try {
            Connection connect = Jsoup.connect(url);
            Document jsoup = connect.get();
            Elements list_group = jsoup.select("div.list-main ul.list li");

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
