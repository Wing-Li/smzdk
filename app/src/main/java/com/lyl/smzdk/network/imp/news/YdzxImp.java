package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.news.YdzxInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import retrofit2.Call;

/**
 * 一点资讯
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
     *
     * @param url
     * @return
     */
    public String getDetail(String url) {
        try {
            Document jsoup = Jsoup.connect(url).get();
            Element main = jsoup.select("div.main").first();
            Element content = main.select("div.left-wrapper").first();
            content.select("img").attr("class", "img-responsive");
            content.select("video").attr("class", "img-responsive");
            content.select("div.float-right.share").remove();
            content.select("div.interact").remove();
            content.select("div.comments").remove();

            Elements imgs = content.select("img");

            // 给每个图片链接前面都加个 http:
            for (int i = 0;i < imgs.size(); i ++) {
                // 创建一个正确的元素
                Element element = imgs.get(i);
                String imageUrl = "http:" + element.attr("src");
                element.attr("src", imageUrl);

                // 设置给原来的网页
                content.select("img").set(i, element);
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
