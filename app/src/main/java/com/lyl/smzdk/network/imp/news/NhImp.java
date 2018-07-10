package com.lyl.smzdk.network.imp.news;

import android.app.Activity;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.lyl.smzdk.constans.Constans;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.network.entity.video.NhCommentReply;
import com.lyl.smzdk.utils.AppUtils;
import com.lyl.smzdk.utils.MyUtils;
import com.lyl.smzdk.utils.SPUtil;

import retrofit2.Call;

/**
 * Created by lyl on 2017/5/23.
 */

public class NhImp {

    private String version_code = "625";
    private String version_name = "6.2.5";
    private String manifest_version_code = "625";
    private String update_version_code = "6250";

    public static final int CONTENT_NUM = 15;

    private Activity mContext;

    private String mCity;
    private String mLatitude;
    private String mLongitude;
    private long mMinTime;
    private int mScreenWidth;
    private String mIid;
    private String mDeviceId;
    private String mUuidEassay;
    private String mDeviceType;
    private String mDeviceBrand;
    private int mOsApi;
    private String mOsVersion;
    private String mOpenudId;
    private String mResolution;
    private int mDpi;

    public NhImp(Activity context) {
        mContext = context;
        initData();
    }

    private void initData() {
        mCity = (String) SPUtil.get(mContext, Constans.SP_CITY, "北京");
        mLatitude = (String) SPUtil.get(mContext, Constans.SP_LATITUDE, "0");
        mLongitude = (String) SPUtil.get(mContext, Constans.SP_LONGITUDE, "0");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;

        if (TextUtils.isEmpty(mIid = (String) SPUtil.get(mContext, Constans.SP_IID, ""))) {
            mIid = MyUtils.getRandomNumber(10);
            SPUtil.put(mContext, Constans.SP_IID, mIid);
        }

        if (TextUtils.isEmpty(mDeviceId = (String) SPUtil.get(mContext, Constans.SP_DEVICE_ID, ""))) {
            mDeviceId = MyUtils.getRandomNumber(11);
            SPUtil.put(mContext, Constans.SP_DEVICE_ID, mDeviceId);
        }

        mDeviceType = AppUtils.getModel();
        mDeviceBrand = AppUtils.getManufacturer();
        mOsApi = AppUtils.getSDKVersion();
        mOsVersion = AppUtils.getSDKVersionName();

        if (TextUtils.isEmpty(mUuidEassay = (String) SPUtil.get(mContext, Constans.SP_UUID_EASSAY, ""))) {
            mUuidEassay = MyUtils.getRandomNumber(15);
            SPUtil.put(mContext, Constans.SP_UUID_EASSAY, mUuidEassay);
        }

        mOpenudId = AppUtils.getUUID().substring(0, 16);

        mResolution = displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
        mDpi = displayMetrics.densityDpi;
    }

    /**
     * 获取热门评论 的二级评论
     *
     * @param id
     * @param page
     * @return
     */
    public Call<NhCommentReply> getNhCommentReply(String id, int page) {
        return Network.getNeihanApi().getNhCommentReply(id,// 段子group_id
                CONTENT_NUM,// 数量
                page * CONTENT_NUM,// 跳过 n 条
                mIid,// 一个长度为10的纯数字字符串，用于标识用户唯一性
                mDeviceId,// 设备 id，一个长度为11的纯数字字符串
                version_code,// 版本号去除小数点，例如612
                version_name,// 版本号，例如6.1.2
                mDeviceType,// 设备型号，例如 hongmi
                mDeviceBrand,// 设备品牌，例如 xiaomi
                mOsApi, // 操作系统版本，例如20
                mOsVersion,// 操作系统版本号，例如7.1.0
                mUuidEassay,// 用户 id，一个长度为15的纯数字字符串
                mOpenudId,// 一个长度为16的数字和小写字母混合字符串
                manifest_version_code,// 版本号去除小数点，例如612
                mResolution,// 屏幕宽高，例如 1920*1080
                mDpi,// 手机 dpi
                update_version_code);
    }

}
