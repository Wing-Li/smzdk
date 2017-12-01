package com.lyl.smzdk.network.imp.video;

import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.video.VideoInflaterInfo;
import com.lyl.smzdk.network.entity.video.VideoMenu;
import com.lyl.smzdk.network.entity.video.XgInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: lyl
 * Date Created : 2017/11/30.
 */
public class XgImp {

    public List<VideoMenu> getMenu() {
        List<VideoMenu> newMenus = new ArrayList<>();

        VideoMenu menu = new VideoMenu();
        menu.setName("推荐");
        menu.setType("video_new");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("音乐");
        menu.setType("subv_voice");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("搞笑");
        menu.setType("subv_funny");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("社会");
        menu.setType("subv_society");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("小品");
        menu.setType("subv_comedy");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("生活");
        menu.setType("subv_life");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("影视");
        menu.setType("subv_movie");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("娱乐");
        menu.setType("subv_entertainment");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("可爱");
        menu.setType("subv_cute");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("游戏");
        menu.setType("subv_game");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("原创");
        menu.setType("subv_boutique");
        newMenus.add(menu);

        menu = new VideoMenu();
        menu.setName("扩展");
        menu.setType("subv_broaden_view");
        newMenus.add(menu);

        return newMenus;
    }

    /**
     * 获取视频列表
     * 取到 group_id=6480422859939250701 ，来获取视频
     *
     * @param type 通过 getMenu() 获取的目录
     */
    public Call<XgInfo> getXgList(String type) {
        return Network.getXgApi().getInfoList(type);
    }

    /**
     * 获取某个链接的视频
     * 链接格式：https://www.ixigua.com/a6480422859939250701/
     * 参数为上面 getXgList 的 group_id
     *
     * @param group_id
     * @return
     */
    public Call<VideoInflaterInfo> getVideoUrl(String group_id) {
        try {
            String url = "https://www.ixigua.com/a%1$s/";
            url = String.format(url, group_id);
//            url = url.replace(":", "%3A");
//            url = url.replace("/", "%2F");
            return Network.getVideoInflaterApi().getInfoList(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
