package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.entity.bt.BtInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: lyl
 * Date Created : 2017/12/7.
 */
public class BtImp {


    /**
     * 种子搜
     */
    public List<BtInfo> getList(String content, int page) {
        List<BtInfo> infoList = new ArrayList<BtInfo>();

        String url = "https://m.zhongziso.com/list/" + content + "/" + page;
        try {
            Document jsoup = Jsoup.connect(url).get();
            Elements list_group = jsoup.select("div.panel-body").select("ul.list-group");

            BtInfo info;
            for (Element element : list_group) {
                info = new BtInfo();
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
}
