package com.lyl.smzdk;

import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lyl.smzdk.dao.db.entity.MyObjectBox;
import com.lyl.smzdk.utils.MyUtils;
import com.tencent.bugly.Bugly;

import java.io.File;

import io.objectbox.BoxStore;

/**
 * Author: lyl
 * Date Created : 2017/10/30.
 */
public class MyApp extends MultiDexApplication {

    public static String appDownloadUrl = "https://www.coolapk.com/apk/com.lyl.smzdk";

    private static String appPath;
    private static String appImagePath;

    private BoxStore boxStore;

    public static boolean isWifi;

    @Override
    public void onCreate() {
        super.onCreate();

        initBugly();
        getBoxStore();
        Fresco.initialize(this);
    }

    /**
     * 初始化 bugly
     */
    private void initBugly() {
        String key = BuildConfig.BuglyKey;
        if (MyUtils.isDev()) {
            Bugly.init(getApplicationContext(), key, true);
        } else {
            Bugly.init(getApplicationContext(), key, false);
        }
    }

    /**
     * 获取数据库管理
     */
    public BoxStore getBoxStore() {
        if (boxStore == null) {
            boxStore = MyObjectBox.builder().androidContext(this).build();
            Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
        }

        return boxStore;
    }

    /**
     * 得到存放文件的路径
     */
    public static String getAppPath() {
        if (!TextUtils.isEmpty(appPath)) {
            return appPath;
        }

        File sdFile = Environment.getExternalStorageDirectory();
        File my = new File(sdFile, "Smzdk");
        if (!my.exists()) {
            my.mkdirs();
        }
        return appPath = my.getAbsolutePath();
    }

    /**
     * 得到存放图片的路径
     */
    public static String getAppImagePath() {
        if (!TextUtils.isEmpty(appImagePath)) {
            return appImagePath;
        }

        String sdFile = getAppPath() + File.separator + "Image" + File.separator;
        File my = new File(sdFile);
        if (!my.exists()) {
            my.mkdirs();
        }
        return appImagePath = my.getAbsolutePath();
    }
}
