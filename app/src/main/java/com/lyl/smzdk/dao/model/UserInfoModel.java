package com.lyl.smzdk.dao.model;

import android.content.Context;

import com.google.gson.Gson;
import com.lyl.smzdk.utils.SPUtil;

import java.util.Date;

/**
 * Author: lyl
 * Date Created : 2018/8/2.
 */
public class UserInfoModel {

    private final String USER_INFO_MODEL = "UserInfoModel";


    public Long id;
    public String number;
    public String password = "";
    public String name; // 用户名
    public String icon = "";
    public String signature = "";
    public Integer sex = 0; // 性别
    public String birth = "";
    public String phone = "";
    public String email = "";
    public String province = "";
    public String city = "";
    public Integer vipGrade = 0;
    public Long integral = 0L;
    public Integer closeDays = 0;
    public Date createTime;
    public Date updateTime;

    private Context mContext;

    public UserInfoModel(Context context) {
        mContext = context;
    }

    /**
     * 保存用户信息
     */
    public void save(UserInfoModel infoModel) {
        String infoJson = new Gson().toJson(infoModel);
        SPUtil.put(mContext, USER_INFO_MODEL, infoJson);
    }

    /**
     * 获取用户信息
     */
    public UserInfoModel get() {
        String infoJson = (String) SPUtil.get(mContext, USER_INFO_MODEL, "");
        return new Gson().fromJson(infoJson, UserInfoModel.class);
    }

}
