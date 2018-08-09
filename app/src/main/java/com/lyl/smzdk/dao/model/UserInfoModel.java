package com.lyl.smzdk.dao.model;

import android.content.Context;

import com.google.gson.Gson;
import com.lyl.smzdk.network.entity.myapi.User;
import com.lyl.smzdk.utils.SPUtil;

/**
 * Author: lyl
 * Date Created : 2018/8/2.
 */
public class UserInfoModel {

    private final String USER_INFO_MODEL = "UserInfoModel";

    private Context mContext;

    public UserInfoModel(Context context) {
        mContext = context;
    }

    /**
     * 保存用户信息
     */
    public void save(User infoModel) {
        String infoJson = new Gson().toJson(infoModel);
        SPUtil.put(mContext, USER_INFO_MODEL, infoJson);
    }

    /**
     * 获取用户信息
     */
    public User get() {
        String infoJson = (String) SPUtil.get(mContext, USER_INFO_MODEL, "");
        return new Gson().fromJson(infoJson, User.class);
    }

}
