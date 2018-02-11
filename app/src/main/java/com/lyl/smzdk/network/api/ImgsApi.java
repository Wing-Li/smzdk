package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.images.ImgsCall;
import com.lyl.smzdk.network.entity.news.GifInfo;
import com.lyl.smzdk.network.entity.news.GifSummaryInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * SOGOU
 * Author: lyl
 * Date Created : 2017/12/21.
 */
public interface ImgsApi {

    /**
     * 获取图片列表
     */
    @GET("pics/channel/getAllRecomPicByTag.jsp?tag=%E5%85%A8%E9%83%A8")
    Call<ImgsCall> getImgs(@Query("category") String type, @Query("start") int start, @Query("len") int len);

    /**
     * 获取动态图列表
     * 大分类下的小目录 和 大分类的列表详情（通过搜索某个文字获得数据，需URL转码）
     */
    @GET("pics/gif/category.jsp")
    Call<GifInfo> getGifs(@Query("query") String query, @Query("start") int start, @Query("xml_len") int len);

    /**
     * 小目录的列表页
     */
    @GET("pics/gif/search.jsp?ie=utf8")
    Call<GifSummaryInfo> getGifSummarys(@Query("query") String query, @Query("start") int start, @Query("xml_len") int len);

}
