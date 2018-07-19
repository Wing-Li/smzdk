package com.lyl.smzdk;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lyl.smzdk.dao.entity.MyObjectBox;
import com.lyl.smzdk.utils.MyUtils;
import com.tencent.bugly.Bugly;

import java.io.File;

import io.objectbox.BoxStore;

/**
 * Author: lyl
 * Date Created : 2017/10/30.
 */
public class MyApp extends Application {

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

    private void initBugly() {
        String key = BuildConfig.BuglyKey;
        if (MyUtils.isDev()) {
            Bugly.init(getApplicationContext(), key, true);
        } else {
            Bugly.init(getApplicationContext(), key, false);
        }
    }

    public BoxStore getBoxStore() {
        if (boxStore == null) {
            boxStore = MyObjectBox.builder().androidContext(this).build();
            Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
        }

        return boxStore;
    }

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

    public static String getAppImagePath() {
        if (!TextUtils.isEmpty(appImagePath)) {
            return appImagePath;
        }

        String sdFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM";
        File my = new File(sdFile, "Smzdk");
        if (!my.exists()) {
            my.mkdirs();
        }
        return appImagePath = my.getAbsolutePath();
    }
}
