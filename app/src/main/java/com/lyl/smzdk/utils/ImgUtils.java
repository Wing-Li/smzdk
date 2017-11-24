package com.lyl.smzdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.lyl.smzdk.R;

import java.security.MessageDigest;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class ImgUtils {

    private static final int placeholderRes = R.drawable.gary_bg;
    private static final int errorRes = R.drawable.gary_bg;

    private static RequestOptions baseOptions = new RequestOptions()//
            .placeholder(placeholderRes)//
            .error(errorRes)//
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

//    DiskCacheStrategy.NONE 什么都不缓存
//    DiskCacheStrategy.DATA 仅仅只缓存原来的全分辨率的图像。
//    DiskCacheStrategy.RESOURCE 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
//    DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）

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
        Glide.with(context).load(url).apply(baseOptions).into(imageView);
    }

    public static void loadF(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(baseOptions).apply(new RequestOptions().centerInside()).into(imageView);
    }

    /**
     * 加载圆形图片。
     */
    public static void loadCircle(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(baseOptions).apply(new RequestOptions().transform(new
                GlideCircleTransform(context))).into(imageView);
    }

    /**
     * 加载圆形图片。加载 uri ，一般用来加载本地图片
     */
    public static void loadCircle(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).apply(baseOptions).apply(new RequestOptions().transform(new
                GlideCircleTransform(context))).into(imageView);
    }

    public static void getBitmap(Context context, String url, BitmapTransformation simpleTarget) {
        Glide.with(context).load(url).apply(baseOptions).apply(new RequestOptions().transform(simpleTarget));
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

/**
 * 圆形图片
 */
class GlideCircleTransform extends BitmapTransformation {
    private Context mContext;

    public GlideCircleTransform(Context context) {
        mContext = context;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        try {
            messageDigest.update((mContext.getPackageName() + "CircleTransform").getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}