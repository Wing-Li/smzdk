package com.lyl.smzdk;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import com.lyl.smzdk.greendao.MyHelper;
import com.lyl.smzdk.greendao.gen.DaoMaster;
import com.lyl.smzdk.greendao.gen.DaoSession;
import com.lyl.smzdk.utils.MyUtils;
import com.tencent.bugly.Bugly;

import org.greenrobot.greendao.database.Database;

import java.io.File;

/**
 * Author: lyl
 * Date Created : 2017/10/30.
 */
public class MyApp extends Application {

    public static String appDownloadUrl = "https://www.coolapk.com/apk/com.lyl.smzdk";

    private static String appPath;
    private static String appImagePath;

    public static DaoSession mDaoSession;

    public static boolean isWifi;

    @Override
    public void onCreate() {
        super.onCreate();

        initBugly();
        getDB();
    }

    private void initBugly() {
        String key = BuildConfig.BuglyKey;
        if (MyUtils.isDev()) {
            Bugly.init(getApplicationContext(), key, true);
        } else {
            Bugly.init(getApplicationContext(), key, false);
        }
    }

    public DaoSession getDB() {
        if (mDaoSession == null) {
            MyHelper myHelper = new MyHelper(this);
            Database db = myHelper.getWritableDb();
            mDaoSession = new DaoMaster(db).newSession();
        }

        return mDaoSession;
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
