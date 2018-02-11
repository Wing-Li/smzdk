package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.images.ImageMenu;
import com.lyl.smzdk.network.entity.news.GifInfo;
import com.lyl.smzdk.network.entity.news.GifSummaryInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: lyl
 * Date Created : 2018/2/11.
 */
public class GifImp {

    public List<ImageMenu> getMenu() {
        List<ImageMenu> imageMenus = new ArrayList<>();

        ImageMenu menu = new ImageMenu();
        menu.setName("搞笑");
        menu.setType("搞笑");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("美女");
        menu.setType("美女");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("影视");
        menu.setType("影视");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("明星");
        menu.setType("明星");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("萌宠");
        menu.setType("萌宠");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("表情");
        menu.setType("表情");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("动漫");
        menu.setType("动漫");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("美食");
        menu.setType("美食");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("综艺");
        menu.setType("综艺");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("健身");
        menu.setType("健身");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("日常");
        menu.setType("日常");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("节日");
        menu.setType("节日");
        imageMenus.add(menu);

        return imageMenus;
    }

    /**
     * 获取 Gif 列表，含有子目录
     */
    public Call<GifInfo> getGifs(String type, int start, int len) {
        return Network.getImgsApi().getGifs(type, start, len);
    }

    /**
     * 获取子目录的列表
     */
    public Call<GifSummaryInfo> getGifSummarys(String type, int start, int len) {
        return Network.getImgsApi().getGifSummarys(type, start, len);
    }
}
