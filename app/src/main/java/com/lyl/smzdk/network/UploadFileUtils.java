package com.lyl.smzdk.network;

import com.lyl.smzdk.utils.LogUtils;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;

/**
 * Author: lyl
 * Date Created : 2018/8/21.
 */
public class UploadFileUtils {

    // 重用uploadManager。一般地，只需要创建一个uploadManager对象
    private static UploadManager uploadManager;
    // 取消上传
    private volatile boolean isCancelled = false;
    private static String QiniuToken;


    private static UploadFileUtils mUploadFileUtils = new UploadFileUtils();

    private UploadFileUtils() {
        Configuration config = new Configuration.Builder()//
                .connectTimeout(10)           // 链接超时。默认10秒
                .responseTimeout(40)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        uploadManager = new UploadManager(config);
    }

    public static UploadFileUtils getInstants() {
        return mUploadFileUtils;
    }


    /**
     * 设置 Token ，有有效期
     */
    public void setToken(String token) {
        QiniuToken = token;
    }

    /**
     * 上传文件
     *
     * @param data 数据，可以是 byte 数组、文件路径、文件
     */
    public void uploadFile(File data, String fileName, final UploadFileCallBack uploadFileCallBack) {
        if (!data.exists()) return;

        // 重置取消上传标志位
        isCancelled = false;

        uploadManager.put(data, fileName, QiniuToken, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            uploadFileCallBack.onSuccess(key);
                        } else {
                            uploadFileCallBack.onFail();
                        }

                        LogUtils.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);

                    }
                },//
                new UploadOptions(null, null, false, new UpProgressHandler() {

                    public void progress(String key, double percent) {
                        uploadFileCallBack.onProgress(percent);
                    }
                }, new UpCancellationSignal() {

                    /**
                     * 取消上传
                     */
                    public boolean isCancelled() {
                        return isCancelled;
                    }
                }));

    }

    /**
     * 取消上传，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
     */
    private void cancellUpload() {
        isCancelled = true;
    }

    public interface UploadFileCallBack{
        void onSuccess(String path);
        void onFail();
        void onProgress(double progress);
    }
}
