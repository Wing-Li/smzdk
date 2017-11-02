package com.lyl.smzdk;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Author: lyl
 * Date Created : 2017/10/30.
 */
public class MyApp extends Application {

    private static String appPath;

    @Override
    public void onCreate() {
        super.onCreate();

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
