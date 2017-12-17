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
    @GET("list/?ac=wap&count=20&format=json_raw")
    Call<XgInfo> getInfoList(@Query("tag") String type, @Query("max_behot_time") String max_behot_time, @Query("as")
            String as, @Query("cp") String cp);
}
