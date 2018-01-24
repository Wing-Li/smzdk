package com.lyl.smzdk.network;


import com.lyl.smzdk.BuildConfig;
import com.lyl.smzdk.network.api.ImgsApi;
import com.lyl.smzdk.network.api.NeihanApi;
import com.lyl.smzdk.network.api.VideoInflaterApi;
import com.lyl.smzdk.network.api.XgApi;
import com.lyl.smzdk.network.api.YdzxApi;
import com.lyl.smzdk.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
     * 视频列表
     */
    public static String URL_XG = "https://m.ixigua.com/";
    /**
     * 视频评论
     */
    public static String URL_XG_COMMENT = "https://www.ixigua.com/";
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

    private static Retrofit getXgVideoRetrofit(String url) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        HttpUrl httpUrl = chain.request().url();
                        String path = httpUrl.url().toString().substring(20);
                        Request request = chain.request()//
                                .newBuilder()//
                                // cookie 最重要，没有会报 403
                                .addHeader("cookie", "UM_distinctid=15ff6bdfbeba02-0c38ac8ad2598-5c153d17-100200-15ff6bdfbec9d0; tt_webid=6492584011599889933; __tasessionId=zncr5205k1513963900312; _ba=BA0.2-20171217-51225-4K42chFL2KxM68AR8KWP; _ga=GA1.2.2090532234.1511673167; _gid=GA1.2.2028382406.1513962277; csrftoken=141cdc8e06a4468e048d14beaf230f7f")//
                                // .addHeader(":authority", "m.ixigua.com")//
                                // .addHeader(":method", "GET")//
                                // .addHeader(":path", path)//
                                // .addHeader(":scheme", "https")//
                                // .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")//
                                // .addHeader("accept-language", "zh-CN,zh;q=0.8")//
                                // .addHeader("cache-control", "no-cache")//
                                // .addHeader("pragma", "no-cache")//
                                // .addHeader("upgrade-insecure-requests", "1")//
                                .addHeader("user-agent", "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Mobile Safari/537.36")//
                                .build();
                        LogUtils.d("视频地址：" + request.url());
                        return chain.proceed(request);
                    }
                })
                .build();

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
            xgApi = getXgVideoRetrofit(URL_XG).create(XgApi.class);
        }
        return xgApi;
    }

    /**
     * 视频
     */
    public static XgApi getXgCommentApi() {
        if (xgApi == null) {
            xgApi = getXgVideoRetrofit(URL_XG_COMMENT).create(XgApi.class);
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
            Retrofit retrofit = getRetrofit(URL_IMG_SOGOU);
            imgsApi  = retrofit.create(ImgsApi.class);
        }
        return imgsApi;
    }
}
