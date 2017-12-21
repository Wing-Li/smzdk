package com.lyl.smzdk.ui.image;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lyl.smzdk.MyApp;
import com.lyl.smzdk.R;
import com.lyl.smzdk.ui.BaseActivity;
import com.lyl.smzdk.utils.FileUtils;
import com.lyl.smzdk.utils.ImgUtils;

import java.io.File;

/**
 * Author: lyl
 * Date Created : 2017/5/16.
 */
public class BaseImageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void download(final View view, String fileUrl) {
        Toast.makeText(mContext, R.string.download_running, Toast.LENGTH_SHORT).show();
        view.setVisibility(View.GONE);

        ImgUtils.downloadImg(getApplicationContext(), fileUrl, new ImgUtils.DownloadImage() {
            @Override
            public void downloadImage(File imgFile) {
                if (imgFile != null) {
                    String imgName = "smzdk_" + System.currentTimeMillis() + ".jpg";
                    // 目标路径
                    File destDir = new File(MyApp.getAppImagePath() + File.separator + imgName);
                    // 移动下载的图片到 目标路径
                    boolean moveFile = FileUtils.moveFile(imgFile.getAbsolutePath(), destDir.getAbsolutePath());

                    if (moveFile) {
                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile
                                (destDir)));
                        Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
