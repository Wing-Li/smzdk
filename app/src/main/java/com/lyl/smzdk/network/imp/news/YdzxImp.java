package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.news.YdzxInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import retrofit2.Call;

/**
 * Author: lyl
 * Date Created : 2017/11/27.
 */
public class YdzxImp {

    private int count = 10;


    /**
     * 获取一点资讯 内容
     *
     * @param channel 类型
     * @param page    页数，从 0 开始
     */
    public Call<YdzxInfo> getGaoXiaoList(String channel, int page) {
        int cstart = count * page;
        int cend = count * (page + 1);

        return Network.getYdzxApi().getInfoList(channel, cstart, cend);
    }

    /**
     * 获取 文章详情的网页， 并且修改指定内容
     * @param url
     * @return
     */
    public String getDetail(String url) {
        try {
            Document jsoup = Jsoup.connect(url).get();
            Element main = jsoup.select("div.main").first();
            Element content = main.select("div.left-wrapper").first();
            content.select("img").attr("class","img-responsive");
            content.select("video").attr("class","img-responsive");
            content.select("div.float-right.share").remove();
            content.select("div.interact").remove();
            content.select("div.comments").remove();

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
