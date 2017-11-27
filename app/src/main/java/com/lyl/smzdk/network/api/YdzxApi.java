package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.YdzxInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: lyl
 * Date Created : 2017/11/27.
 */
public interface YdzxApi {

    /**
     * 获取一点资讯的数据
     *
     * @param channel 频道类型
     * @param cstart  数据开始位置
     * @param cend    数据结束位置
     */
    @GET("home/q/news_list_for_channel?infinite=true&refresh=1&__from__=pc&multi=5&appid=app_yidian")
    Call<YdzxInfo> getInfoList(@Query("channel_id") String channel, @Query("cstart") int cstart, @Query("cend") int cend);
}
