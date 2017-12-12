package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.video.XgInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: lyl
 * Date Created : 2017/11/27.
 */
public interface XgApi {

    /**
     * 获取视频
     */
    @GET("api/pc/feed/?utm_source=toutiao&widen=1&tadrequire=true")
    Call<XgInfo> getInfoList(@Query("category") String type, @Query("max_behot_time") long max_behot_time, @Query
            ("as") String as, @Query("cp") String cp);
}
