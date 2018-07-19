package com.lyl.smzdk.utils;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lyl.smzdk.R;
import com.lyl.smzdk.view.MyImageView;

import java.io.File;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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

//    DiskCacheStrategy.NONE 什么都不缓存
//    DiskCacheStrategy.DATA 仅仅只缓存原来的全分辨率的图像。
//    DiskCacheStrategy.RESOURCE 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
//    DiskCacheStrategy.ALL 缓存所有版本的图像
//    DiskCacheStrategy.AUTOMATIC: 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。

    /**
     * @param context
     * @param url       图片的地址
     * @param imageView ImageView
     * @param thumbnail 简单的缩略图:0.1f 作为参数，Glide 将会显示原始图像的10%的大小
     */
    public static void load(Context context, String url, ImageView imageView, float thumbnail) {
        // 加载GIF慢
        // https://github.com/bumptech/glide/issues/513#issuecomment-117690923、
//        Glide glide = Glide.get(context);
//        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(Network.httpClient);
//
//        glide.register(GlideUrl.class, InputStream.class, factory);
        Glide.with(context).load(url).apply(baseOptions).thumbnail(thumbnail).into(imageView);
    }

    public static void load(Context context, int url, ImageView imageView) {
        Glide.with(context).load(url).apply(baseOptions).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        if (imageView instanceof MyImageView){
            final MyImageView img = (MyImageView) imageView;

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

            DraweeController controller = Fresco.newDraweeControllerBuilder()//
                    .setUri(Uri.parse(url))//
                    .setControllerListener(listener)//
                    .setAutoPlayAnimations(true)
                    .build();
            img.setController(controller);

        } else {
            Glide.with(context).load(url.trim()).apply(baseOptions).into(imageView);
        }
    }



    public static void load(Context context, URL url, ImageView imageView) {
        Glide.with(context).load(url).apply(baseOptions).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, int w, int h) {
        Glide.with(context).load(url).apply(baseOptions).apply(new RequestOptions().override(w, h)).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, int w, int h, float thumbnail) {
        Glide.with(context).load(url).apply(baseOptions).thumbnail(thumbnail).apply(new RequestOptions().override(w, h)).into(imageView);
    }

    public static void loadGif(Context context, String url, ImageView imageView) {
        Glide.with(context).asGif().load(url).apply(baseOptions).apply(new RequestOptions()).into(imageView);
    }

    public static void loadGif(Context context, String url, ImageView imageView, int w, int h) {
        Glide.with(context).asGif().load(url).apply(baseOptions).apply(new RequestOptions().override(w, h)).into(imageView);
    }

    /**
     * 加载圆形图片。
     */
    public static void loadCircle(Context context, String url, ImageView imageView) {
        if (imageView != null)
        Glide.with(context).load(url).apply(baseOptions).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    /**
     * 加载圆形图片。加载 uri ，一般用来加载本地图片
     */
    public static void loadCircle(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).apply(baseOptions).apply(RequestOptions.circleCropTransform()).into(imageView);
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
     * @param context
     * @param url
     * @param downloadImage
     */
    public static void downloadImg(final Context context, final String url, final DownloadImage downloadImage) {
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> observableEmitter) throws Exception {
                FutureTarget<File> target = Glide.with(context)//
                        .asFile()//
                        .load(url)//
                        .submit();
                File file = target.get();

                observableEmitter.onNext(file);
            }
        })//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(File file) {
                        downloadImage.downloadImage(file);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        downloadImage.downloadImage(null);
                        LogUtils.d("下载图片出错："+throwable.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public interface DownloadImage {
        void downloadImage(File imgFile);
    }

    /**
     * 释放内存
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /**
     * 清除所有缓存
     */
    public static void clearAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}