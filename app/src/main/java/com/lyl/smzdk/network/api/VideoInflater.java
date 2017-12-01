package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.video.VideoInflaterInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: lyl
 * Date Created : 2017/11/30.
 */
public interface VideoInflater {
    /**
     * 获取视频
     * http://v.ranks.xin/video-parse.php?url=https%3A%2F%2Fwww.ixigua.com%2Fa6480422859939250701%2F
     */
    @GET("video-parse.php")
    Call<VideoInflaterInfo> getInfoList(@Query("url") String url);
}
