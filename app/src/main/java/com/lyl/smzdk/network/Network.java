package com.lyl.smzdk.network;


import com.lyl.smzdk.BuildConfig;
import com.lyl.smzdk.network.api.ImgsApi;
import com.lyl.smzdk.network.api.NeihanApi;
import com.lyl.smzdk.network.api.VideoInflaterApi;
import com.lyl.smzdk.network.api.XgApi;
import com.lyl.smzdk.network.api.YdzxApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lyl on 2017/5/9.
 */
public class Network {

    /**
     * 内涵段子 网址
     */
    private static String URL_NEIHAN = "http://is.snssdk.com/";

    /**
     * 一点资讯
     */
    private static String URL_YDZX = "http://www.yidianzixun.com/";
    /**
     * 视频
     */
    public static String URL_XG = "http://m.ixigua.com/";
    /**
     * 视频解析
     */
    public static String URL_XG_INFLATER = "http://v.ranks.xin/";
    /**
     * 图片列表
     */
    public static String URL_IMG_SOGOU = "http://pic.sogou.com";



    private static final int DEFAULT_TIMEOUT = 15;

    public static OkHttpClient httpClient;
    private static NeihanApi neihanApi;
    private static YdzxApi ydzxApi;
    private static XgApi xgApi;
    private static VideoInflaterApi videoInflater;
    private static ImgsApi imgsApi;

    static {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        // compile 'com.squareup.okhttp3:logging-interceptor:3.8.0'
        // compile 'com.squareup.okhttp3:okhttp:3.8.0'
        if ("dev".equals(BuildConfig.Environment)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }
        httpClient = httpClientBuilder.build();
    }

    private static Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()//
                .client(httpClient)//
                .baseUrl(url)//
                .addConverterFactory(GsonConverterFactory.create())//
                .build();
    }

    /**
     * 内涵段子
     */
    public static NeihanApi getNeihanApi() {
        if (neihanApi == null) {
            neihanApi = getRetrofit(URL_NEIHAN).create(NeihanApi.class);
        }
        return neihanApi;
    }

    /**
     * 一点咨询
     */
    public static YdzxApi getYdzxApi() {
        if (ydzxApi == null) {
            ydzxApi = getRetrofit(URL_YDZX).create(YdzxApi.class);
        }
        return ydzxApi;
    }

    /**
     * 视频
     */
    public static XgApi getXgApi() {
        if (xgApi == null) {
            xgApi = getRetrofit(URL_XG).create(XgApi.class);
        }
        return xgApi;
    }

    /**
     * 视频解析
     */
    public static VideoInflaterApi getVideoInflaterApi() {
        if (videoInflater == null) {
            videoInflater = getRetrofit(URL_XG_INFLATER).create(VideoInflaterApi.class);
        }
        return videoInflater;
    }

    /**
     * 图片集合
     */
    public static ImgsApi getImgsApi(){
        if (imgsApi == null){
            imgsApi  = getRetrofit(URL_IMG_SOGOU).create(ImgsApi.class);
        }
        return imgsApi;
    }
}
