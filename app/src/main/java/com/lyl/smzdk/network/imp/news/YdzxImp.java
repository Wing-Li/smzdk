package com.lyl.smzdk.network.imp.news;

import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.YdzxInfo;

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
}
