package com.lyl.smzdk.dao.model;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lyl.smzdk.network.entity.myapi.User;
import com.lyl.smzdk.utils.AppUtils;
import com.lyl.smzdk.utils.SPUtil;

/**
 * Author: lyl
 * Date Created : 2018/8/2.
 */
public class UserInfoModel {

    private final String USER_INFO_MODEL = "UserInfoModel";

    private final String USERINFO_ID = "USERINFO_ID";
    private final String USERINFO_NUMBER = "USERINFO_NUMBER";
    private final String USERINFO_NAME = "USERINFO_NAME";
    private final String USERINFO_ICON = "USERINFO_ICON";
    private final String USERINFO_SIGNATURE = "USERINFO_SIGNATURE";
    private final String USERINFO_SEX = "USERINFO_SEX";
    private final String USERINFO_VIPGRADE = "USERINFO_VIPGRADE";


    /**
     * 当没有用户的时候，搜索时，使用这个。
     * 这个不能被清理
     */
    private final String UUID = "UUID";

    private Context mContext;


    public UserInfoModel(Context context) {
        mContext = context;
    }

    /**
     * 保存用户信息
     */
    public void save(User infoModel) {
        if (infoModel != null) {
            String infoJson = new Gson().toJson(infoModel);
            SPUtil.put(mContext, USER_INFO_MODEL, infoJson);

            setId(infoModel.getId());
            setNumber(infoModel.getNumber());
            setName(infoModel.getName());
            setIcon(infoModel.getIcon());
            setSignature(infoModel.getSignature());
            setSex(infoModel.getSex());
            setVipGrade(infoModel.getVipGrade());
        }
    }

    /**
     * 获取用户信息
     */
    public User get() {
        String infoJson = (String) SPUtil.get(mContext, USER_INFO_MODEL, "");
        return new Gson().fromJson(infoJson, User.class);
    }

    /**
     * 退出登录
     * 清理记录的用户信息
     */
    public void clear() {
        SPUtil.remove(mContext, USER_INFO_MODEL);

        SPUtil.remove(mContext, USERINFO_ID);
        SPUtil.remove(mContext, USERINFO_NUMBER);
        SPUtil.remove(mContext, USERINFO_NAME);
        SPUtil.remove(mContext, USERINFO_ICON);
        SPUtil.remove(mContext, USERINFO_SIGNATURE);
        SPUtil.remove(mContext, USERINFO_SEX);
        SPUtil.remove(mContext, USERINFO_VIPGRADE);
    }


    public long getId() {
        return (long) SPUtil.get(mContext, USERINFO_ID, 0L);
    }

    public void setId(long id) {
        SPUtil.put(mContext, USERINFO_ID, id);
    }

    public String getNumber() {
        return (String) SPUtil.get(mContext, USERINFO_NUMBER, "");
    }

    public void setNumber(String number) {
        SPUtil.put(mContext, USERINFO_NUMBER, number);
    }

    public String getName() {
        return (String) SPUtil.get(mContext, USERINFO_NAME, "");
    }

    public void setName(String name) {
        SPUtil.put(mContext, USERINFO_NAME, name);
    }

    public String getIcon() {
        return (String) SPUtil.get(mContext, USERINFO_ICON, "");
    }

    public void setIcon(String icon) {
        SPUtil.put(mContext, USERINFO_ICON, icon);
    }

    public String getSignature() {
        return (String) SPUtil.get(mContext, USERINFO_SIGNATURE, "");
    }

    public void setSignature(String signature) {
        SPUtil.put(mContext, USERINFO_SIGNATURE, signature);
    }

    public int getSex() {
        return (int) SPUtil.get(mContext, USERINFO_SEX, 0);
    }

    public void setSex(int sex) {
        SPUtil.put(mContext, USERINFO_SEX, sex);
    }

    public int getVipGrade() {
        return (int) SPUtil.get(mContext, USERINFO_VIPGRADE, 0);
    }

    public void setVipGrade(int vipGrade) {
        SPUtil.put(mContext, USERINFO_VIPGRADE, vipGrade);
    }

    public String getUUID() {
        String uuid = (String) SPUtil.get(mContext, UUID, "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = setUUID();
        }
        return uuid;
    }

    private String setUUID() {
        String uuid = AppUtils.getUUID(mContext);
        SPUtil.put(mContext, UUID, uuid);
        return uuid;
    }
}
