package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.images.ImageInfo;
import com.lyl.smzdk.network.entity.images.ImageMenu;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 美女图 xxxiao
 * Author: lyl
 * Date Created : 2018/1/25.
 */

public class MvtImp {

    private static String url = "http://m.xxxiao.com/new";

    public List<ImageMenu> getMenu() {
        List<ImageMenu> imageMenuList = new ArrayList<>();
        try {
            Connection connect = Jsoup.connect(url);
            Document jsoup = connect.get();
            Elements elements = jsoup.select("div[id=main] div[id=content] div.menu-cmw-container ul[id=menu-cmw] li");

            ImageMenu menu;
            for (Element item : elements) {
                item = item.select("a").first();
                menu = new ImageMenu();
                // 名称
                menu.setName(item.text());
                // 类型：链接
                menu.setType(item.attr("href"));
                imageMenuList.add(menu);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageMenuList;
    }

    public List<ImageInfo> getSummary(String type, int page) {
        List<ImageInfo> imageInfoList = new ArrayList<>();

        String url = type + "/page/" + page;
        try {
            Connection connect = Jsoup.connect(url);
            Document jsoup = connect.get();
            Elements elements = jsoup.select("div[id=main] div[id=content] article");

            ImageInfo imageInfo;
            for (Element item : elements) {
                imageInfo = new ImageInfo();

                Element titleElement = item.select("header h2 a").first();
                // 标题
                imageInfo.setTitle(titleElement.text());
                // 链接详情
                imageInfo.setDetial_url(titleElement.attr("href"));

                Element img = item.select("div.entry-thumbnail a img").first();
                // 原图 宽
                imageInfo.setWidth(Integer.parseInt(img.attr("width")));
                // 原图 高
                imageInfo.setHeight(Integer.parseInt(img.attr("height")));
                // 原图 链接
                String imgUrl = img.attr("data-src");
                String endType = imgUrl.substring(imgUrl.lastIndexOf("."));// 后缀名
                String imgStartUrl = imgUrl.substring(0, imgUrl.lastIndexOf("-"));// 原图前缀
                imageInfo.setPic_url(imgStartUrl + endType);

                Element imgMsg = item.select("div.entry-excerpt p").first();
                // 图集简介
                imageInfo.setImgMsg(imgMsg.text());

                imageInfoList.add(imageInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageInfoList;
    }

    public List<ImageInfo> getDetails(String detailUrl) {
        List<ImageInfo> imageInfoList = new ArrayList<>();

        try {
            Connection connect = Jsoup.connect(detailUrl);
            Document jsoup = connect.get();
            Elements elements = jsoup.select("div[id=main] div[id=content] article div.entry-content figure");

            ImageInfo imageInfo;
            for (Element item : elements) {
                try {
                    imageInfo = new ImageInfo();

                    item = item.select("a").first();
                    // 原图地址是 去掉缩略图 后面的尺寸
                    String pic_url = item.attr("href");
                    imageInfo.setPic_url(pic_url);

                    item = item.select("img").first();
                    // 缩略图 链接
                    String thumb_url = item.attr("src");

                    // 通过链接中的数值计算 宽高
                    int start = thumb_url.lastIndexOf("-");
                    int end = thumb_url.lastIndexOf(".");
                    String wh = thumb_url.substring(start + 1, end);
                    String[] split = wh.split("x");

                    // 缩略图 宽
                    imageInfo.setThumb_width(Integer.parseInt(split[0]));
                    // 缩略图 高
                    imageInfo.setThumb_height(Integer.parseInt(split[1]));
                    // 缩略图地址
                    imageInfo.setThumbUrl(thumb_url);

                    imageInfoList.add(imageInfo);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageInfoList;
    }
}
