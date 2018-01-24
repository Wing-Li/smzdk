package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.video.XgComment;
import com.lyl.smzdk.network.entity.video.XgCommentReply;
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
     * http://m.ixigua.com/list/?tag=subv_voice&ac=wap&count=20&format=json_raw&as=A1D5EA83CB0A8A8&cp=5A3B9A186AC88E1
     * &max_behot_time=1513853714
     */
    @GET("list/?ac=wap&count=20&format=json_raw")
    Call<XgInfo> getInfoList(@Query("tag") String type, //
                             @Query("max_behot_time") long max_behot_time, //
                             @Query("as") String as, @Query("cp") String cp);

    /**
     * 获取视频 第一次
     * http://m.ixigua.com/list/?tag=subv_voice&ac=wap&count=20&format=json_raw&as=A165EAF37BEA881&cp=5A3B8AB818814E1
     * &min_behot_time=0
     */
    @GET("list/?ac=wap&count=20&format=json_raw")
    Call<XgInfo> getFirstInfoList(@Query("tag") String type,  //
                                  @Query("min_behot_time") long min_behot_time,  //
                                  @Query("as") String as,  //
                                  @Query("cp") String cp);

    /**
     * 获取视频的评论
     * https://www.ixigua.com/api/comment/list/?group_id=6498118025852486157&item_id=6498118025852486157&offset=0
     * &count=10
     */
    @GET("api/comment/list/")
    Call<XgComment> getCommentList(@Query("group_id") String group_id,  //
                                   @Query("item_id") String item_id, //
                                   @Query("offset") String offset, //
                                   @Query("count") String count);

    /**
     * 获取视频评论的评论
     * https://www.ixigua.com/api/comment/get_reply/?comment_id=1590379640268829&dongtai_id=1590379640268829&offset=0
     * &count=20
     */
    @GET("api/comment/get_reply/")
    Call<XgCommentReply> getCommentReplyList(@Query("comment_id") String comment_id, //
                                             @Query("dongtai_id") String dongtai_id, //
                                             @Query("offset") String offset, //
                                             @Query("count") String count);

}
