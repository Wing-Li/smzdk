package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.images.ImgsCall;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: lyl
 * Date Created : 2017/12/21.
 */
public interface ImgsApi {

    @GET("pics/channel/getAllRecomPicByTag.jsp?tag=%E5%85%A8%E9%83%A8")
    Call<ImgsCall> getImgs(@Query("category") String type, @Query("start") int start, @Query("len") int len);
}
