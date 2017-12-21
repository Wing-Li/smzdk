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
 * Created by lyl on 2017/5/16.
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
                // 目标路径
                File destDir = new File(MyApp.getAppPath() + File.separator + "smzdk_" + imgFile.getName());
                // 移动下载的图片到 目标路径
                boolean moveFile = FileUtils.moveFile(imgFile.getAbsolutePath(), destDir.getAbsolutePath());

                if (moveFile) {
                    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imgFile)));
                    Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Call<ResponseBody> responseBodyCall = Network.getNeihanApi().downloadFileWithDynamicUrlSync(fileUrl);
//        Call<ResponseBody> clone = responseBodyCall.clone();
//        clone.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    String imgName = "";
//                    if (isGif) {
//                        imgName = "smzdk_" + System.currentTimeMillis() + ".gif";
//                    } else {
//                        imgName = "smzdk_" + System.currentTimeMillis() + ".jpeg";
//                    }
//
//                    File imgFile = new File(MyApp.getAppPath() + File.separator + imgName);
//                    boolean toDisk = writeResponseBodyToDisk(response.body(), imgName, imgFile);
//                    if (toDisk) {
//                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile
//                                (imgFile)));
//                        Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
//                view.setVisibility(View.VISIBLE);
//            }
//        });
    }

//    private boolean writeResponseBodyToDisk(ResponseBody body, String imgName, File imgFile) {
//        try {
//            InputStream inputStream = null;
//            OutputStream outputStream = null;
//
//            try {
//                byte[] fileReader = new byte[1024];
//
//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;
//
//                inputStream = body.byteStream();
//                outputStream = new FileOutputStream(imgFile);
//
//                while (true) {
//                    int read = inputStream.read(fileReader);
//
//                    if (read == -1) {
//                        break;
//                    }
//
//                    outputStream.write(fileReader, 0, read);
//
//                    fileSizeDownloaded += read;
//                }
//
//                outputStream.flush();
//
//                return true;
//            } catch (IOException e) {
//                return false;
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            }
//        } catch (IOException e) {
//            return false;
//        }
//    }
}
