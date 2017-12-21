package com.lyl.smzdk.network.imp.images;

import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.images.ImageMenu;
import com.lyl.smzdk.network.entity.images.ImgsCall;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Author: lyl
 * Date Created : 2017/12/21.
 */
public class ImgsImp {

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
        menu.setName("壁纸");
        menu.setType("壁纸");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("明星");
        menu.setType("明星");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("汽车");
        menu.setType("汽车");
        imageMenus.add(menu);

        menu = new ImageMenu();
        menu.setName("LOFTER");
        menu.setType("LOFTER");
        imageMenus.add(menu);

        return imageMenus;
    }

    public Call<ImgsCall> getImages(String type, int start, int len) {
        return Network.getImgsApi().getImgs(type, start, len);
    }
}
