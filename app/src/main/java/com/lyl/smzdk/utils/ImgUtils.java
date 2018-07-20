package com.lyl.smzdk.utils;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
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
            final MyImageView img = (MyImageView) imageView;

            // 设置高度自适应
            ControllerListener listener = new BaseControllerListener<ImageInfo>() {
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

            // 设置图片的显示信息
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder()//
                    .setUri(Uri.parse(url))//
                    .setOldController(img.getController()).setControllerListener(listener)//
                    .setAutoPlayAnimations(true);

            // 设置加载中 的动画
            GenericDraweeHierarchyBuilder hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())//
                    .setPlaceholderImage(R.drawable.loading);

            // 设置圆角图片
            if (isRound) {
                RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
                // roundingParams.setOverlayColor(R.color.green);
                hierarchy.setRoundingParams(roundingParams);
            }

            img.setHierarchy(hierarchy.build());
            img.setController(controller.build());
        } else {
            Glide.with(context).load(url.trim()).apply(baseOptions).into(imageView);
        }
    }


    /**
     * 加载图片
     */
    public static void load(Context context, String url, ImageView imageView) {
        if (imageView != null) load(context, url, imageView, false);
    }

    /**
     * 加载圆形图片。
     */
    public static void loadRound(Context context, String url, ImageView imageView) {
        if (imageView != null) load(context, url, imageView, true);
    }

    /**
     * 获取 Bitmap 图片
     *
     * @param context
     * @param url
     * @param simpleTarget
     */
    public static void getBitmap(Context context, String url, SimpleTarget simpleTarget) {
        Glide.with(context).asBitmap().load(url).into(simpleTarget);
    }

    /**
     * 下载图片
     *
     * @param url           下载地址
     * @param filePath      文件在本地的地址
     * @param downloadImage 下载的回调
     */
    public static void downloadImg(final String url, final String filePath, final DownloadImage downloadImage) {
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

                            downloadImage.downloadImage(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            downloadImage.downloadImage(null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                downloadImage.downloadImage(null);
            }
        });
    }

    public interface DownloadImage {
        void downloadImage(File imgFile);
    }

    /**
     * 删除所有缓存的图像（包括存储和内存）
     */
    public static void clearCachesAll() {
        Fresco.getImagePipeline().clearCaches();
    }
}