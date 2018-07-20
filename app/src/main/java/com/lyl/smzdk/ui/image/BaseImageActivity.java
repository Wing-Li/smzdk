package com.lyl.smzdk.ui.image;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.ImgUtils;

import java.io.File;

/**
 * Author: lyl
 * Date Created : 2017/5/16.
 */
@SuppressLint("Registered")
public class BaseImageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void download(final View view, String fileUrl, final boolean isGif) {
        Toast.makeText(mContext, R.string.download_running, Toast.LENGTH_SHORT).show();
        if (view != null) {
            view.setVisibility(View.GONE);
        }

        String imgName = "";
        if (isGif) {
            imgName = "smzdk_" + System.currentTimeMillis() + ".gif";
        } else {
            imgName = "smzdk_" + System.currentTimeMillis() + ".jpg";
        }
        // 目标路径
        String destDir = MyApp.getAppImagePath() + File.separator + imgName;

        ImgUtils.downloadImg(fileUrl, destDir, new ImgUtils.DownloadImageCallback() {
            @Override
            public void onDownloadImage(File imgFile) {
                if (imgFile != null) {
                    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imgFile)));
                    Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
