package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.myapi.Announcement;
import com.lyl.smzdk.network.entity.myapi.BaseCallBack;
import com.lyl.smzdk.network.entity.myapi.Feedback;
import com.lyl.smzdk.network.entity.myapi.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: lyl
 * Date Created : 2018/8/3.
 */
public interface MyApi {

//    // method Url, headers, body参数都可以动态外部传入
//    @POST("{url}")
//    Observable<ResponseBody> executePost(@Path("url") String url, @FieldMap Map<String, String> maps);

    // ========================================================↓用户相关↓========================================================================

    /**
     * 创建用户
     */
    @POST("createUser")
    Observable<BaseCallBack<User>> createUser(@Query("number") String number, @Query("password") String password, @Query("name") String name,
                                              @Query("sex") Integer sex);

    /**
     * 更新用户
     * 红色的参数都是可以写在 Map 里面的
     *
     * @param userId    用户 id
     * @param name      用户名
     * @param icon      用户图标
     * @param signature 签名
     * @param sex       性别 1 0
     * @param birth     生日
     * @param phone     手机
     * @param email     邮箱
     * @param province  省会
     * @param city      城市
     * @return
     */
    @POST("updateUser")
    Observable<BaseCallBack<User>> updateUser(@Query("userId") Long userId, @FieldMap Map<String, String> maps);

    /**
     * 登陆
     */
    @POST("login")
    Observable<BaseCallBack<User>> login(@Query("userName") String userName, @Query("password") String password);

    /**
     * 获取指定用户的信息
     */
    @POST("getUser")
    Observable<BaseCallBack<User>> getUser(@Query("userId") Long userId);

    /**
     * 会员充值
     *
     * @param userId   用户id
     * @param money    花了多少钱
     * @param vipGrade 会员等级
     * @param duration 充值时长
     * @param from     充值来源：1 充值 2 赠送 3 官方赠送
     * @return
     */
    @POST("addVipRecharge")
    Observable<BaseCallBack<User>> addVipRecharge(@Query("userId") Long userId, @Query("money") Double money, @Query("vipGrade") Integer
            vipGrade, @Query("duration") Integer duration, @Query("from") Integer from);


    // ========================================================↓搜索↓========================================================================

    /**
     * BT 搜索
     *
     * @param userId  用户id
     * @param uuid    uuid 唯一标示
     * @param content 搜索内容
     * @return
     */
    @POST("btSearch")
    Observable<BaseCallBack<Integer>> btSearch(@Query("userId") Long userId, @Query("uuid") String uuid, @Query("content") String content);


    // ========================================================↓公告↓========================================================================

    /**
     * 获取最新的公告
     */
    @POST("getLastAnnouncement")
    Observable<BaseCallBack<Announcement>> getLastAnnouncement();

    /**
     * 获取所有公告
     */
    @POST("getAllAnnouncement")
    Observable<BaseCallBack<List<Announcement>>> getAllAnnouncement();


    // ========================================================↓建议反馈↓========================================================================

    /**
     * 建议反馈
     *
     * @param userId   用户id
     * @param userName 用户名称
     * @param title    标题
     * @param content  内容
     */
    @POST("addFeedBack")
    Observable<BaseCallBack<String>> addFeedBack(@Query("userId") Long userId, @Query("userName") String userName, @Query("title") String
            title, @Query("content") String content);

    /**
     * 建议反馈
     *
     * @param feedbackId 建议的 id
     * @param reply      管理员的回复
     */
    @POST("addFeedBackReply")
    Observable<BaseCallBack<String>> addFeedBackReply(@Query("feedbackId") Long feedbackId, @Query("reply") String reply);

    /**
     * 获取所有建议
     */
    @POST("getAllFeedback")
    Observable<BaseCallBack<List<Feedback>>> getAllFeedback();
}
