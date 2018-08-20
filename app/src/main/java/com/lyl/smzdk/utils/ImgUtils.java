package com.lyl.smzdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lyl.smzdk.R;
import com.lyl.smzdk.network.Network;
import com.lyl.smzdk.view.imageview.MyImageView;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Wing_Li
 * 2016/4/14.
 */
public class ImgUtils {

    private static final int placeholderRes = R.drawable.bg_gary;
    private static final int errorRes = R.drawable.bg_gary;

    private static RequestOptions baseOptions = new RequestOptions()//
            .placeholder(placeholderRes)//
            .error(errorRes)//
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    private static void load(Context context, String url, ImageView imageView, boolean isRound) {
        if (imageView instanceof MyImageView) {
            LogUtils.d("Fresco: " + url);
            final MyImageView img = (MyImageView) imageView;

            // 设置高度自适应
            ControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                    updateViewSize(img, imageInfo);
                }

                @Override
                public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                    updateViewSize(img, imageInfo);
                }

                void updateViewSize(SimpleDraweeView draweeView, @Nullable ImageInfo imageInfo) {
                    if (imageInfo != null) {
                        draweeView.setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
                    }
                }
            };

            // 圆形图片如果设置占位图，图片就成方的了
            if (!isRound) {
                // 设置加载中 的动画
                GenericDraweeHierarchyBuilder hierarchy = new GenericDraweeHierarchyBuilder(context.getResources());
                hierarchy.setPlaceholderImage(R.drawable.loading);
                img.setHierarchy(hierarchy.build());
            }

            // 设置图片的显示信息
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(Uri.parse(url));
            controller.setOldController(img.getController()).setControllerListener(listener);
            controller.setAutoPlayAnimations(true);
            img.setController(controller.build());

        } else {
            LogUtils.d("Glide: " + url);
            // 单个可放大的大图加载时用到
            Glide.with(context).load(url.trim()).apply(baseOptions).into(imageView);
        }
    }


    /**
     * 加载图片
     */
    public static void load(Context context, String url, ImageView imageView) {
        if (imageView != null && !TextUtils.isEmpty(url)) {
            load(context, url, imageView, false);
        }
    }

    /**
     * 加载图片
     */
    public static void load(Context context, int res, ImageView imageView) {
        if (imageView != null && 0 != res) {
            Glide.with(context).load(res).apply(baseOptions).into(imageView);
        }
    }

    /**
     * 加载圆形图片。
     */
    public static void loadRound(Context context, String url, ImageView imageView) {
        if (imageView != null) load(context, url, imageView, true);
    }

    // ======================================== ↓ 获取Bitmap ↓ ================================================================

    public static void getBitmap(Context context, String url, final BitmapCallback bitmapCallback) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(final Bitmap bitmap) {
                if (bitmap == null) {
                    bitmapCallback.onBitmap(null);
                } else {
                    bitmapCallback.onBitmap(bitmap);
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                bitmapCallback.onBitmap(null);
            }
        }, CallerThreadExecutor.getInstance());
    }

    public interface BitmapCallback {
        /**
         * 返回的结果在子线程中，千万注意
         */
        void onBitmap(Bitmap bitmap);
    }
    // ======================================== ↑ 获取Bitmap ↑ ================================================================

    // ======================================== ↓ 下载图片 ↓ ================================================================

    /**
     * 下载图片
     *
     * @param url           下载地址
     * @param filePath      文件在本地的地址
     * @param downloadImage 下载的回调
     */
    public static void downloadImg(final String url, final String filePath, final DownloadImageCallback downloadImage) {
        Call<ResponseBody> clone = Network.getDownloadFile().downloadFile(url).clone();

        clone.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        try {
                            Source source = Okio.source(body.byteStream());

                            File file = new File(filePath);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            Sink sink = Okio.sink(file);
                            BufferedSink bufferedSink = Okio.buffer(sink);
                            bufferedSink.writeAll(source);
                            bufferedSink.close();

                            downloadImage.onDownloadImage(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            downloadImage.onDownloadImage(null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                downloadImage.onDownloadImage(null);
            }
        });
    }

    public interface DownloadImageCallback {
        void onDownloadImage(File imgFile);
    }

    // ======================================== ↑ 下载图片 ↑ ================================================================

    /**
     * 删除所有缓存的图像（包括存储和内存）
     */
    public static void clearCachesAll() {
        Fresco.getImagePipeline().clearCaches();
    }
}