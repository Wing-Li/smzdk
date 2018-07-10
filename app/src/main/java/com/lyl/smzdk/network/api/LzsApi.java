package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.news.LzsInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: lyl
 * Date Created : 2018/7/10.
 */
public interface LzsApi {

    /**
     * 获取列表
     * https://wapbaike.baidu.com/api/vbaike/knowledgeList?count=10&page=%2$s&keyWord=%1$s
     */
    @GET("vbaike/knowledgeList?count=10")
    Call<LzsInfo> getList(@Query("page") String page, @Query("keyWord") String type);

}
