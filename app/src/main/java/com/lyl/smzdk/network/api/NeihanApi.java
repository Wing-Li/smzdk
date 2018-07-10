package com.lyl.smzdk.network.api;


import com.lyl.smzdk.network.entity.video.NhCommentReply;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lyl on 2017/5/9.
 */

public interface NeihanApi {

    /**
     * 西瓜视频
     * 获取热门评论 的二级评论
     *
     * @param id
     * @param count
     * @param offset
     * @return
     */
    @GET("2/comment/v2/reply_list/?ac=wifi&channel=360&aid=7&app_name=joke_essay&device_platform=android&ssmix=a")
    Call<NhCommentReply> getNhCommentReply(@Query("id") String id, //
                                           @Query("count") int count, //
                                           @Query("offset") int offset, //
                                           @Query("iid") String iid, //
                                           @Query("device_id") String device_id, //
                                           @Query("version_code") String version_code, //
                                           @Query("version_name") String version_name, //
                                           @Query("device_type") String device_type, //
                                           @Query("device_brand") String device_brand, //
                                           @Query("os_api") int os_api, //
                                           @Query("os_version") String os_version, //
                                           @Query("uuid") String uuid, //
                                           @Query("openudid") String openudid, //
                                           @Query("manifest_version_code") String manifest_version_code, //
                                           @Query("resolution") String resolution, //
                                           @Query("dpi") int dpi, //
                                           @Query("update_version_code") String update_version_code);

}
