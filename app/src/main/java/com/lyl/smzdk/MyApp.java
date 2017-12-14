package com.lyl.smzdk;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import com.lyl.smzdk.greendao.MyHelper;
import com.lyl.smzdk.greendao.gen.DaoMaster;
import com.lyl.smzdk.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.io.File;

/**
 * Author: lyl
 * Date Created : 2017/10/30.
 */
public class MyApp extends Application {

    private static String appPath;

    public static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        getDB();
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
        File sdFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        File my = new File(sdFile, "smzdk");
        if (!my.exists()) {
            my.mkdirs();
        }
        return appPath = my.getAbsolutePath();
    }
}
