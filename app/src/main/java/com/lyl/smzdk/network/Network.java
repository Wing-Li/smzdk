package com.lyl.smzdk.network;


import com.lyl.smzdk.BuildConfig;
import com.lyl.smzdk.network.api.ImgsApi;
import com.lyl.smzdk.network.api.NeihanApi;
import com.lyl.smzdk.network.api.VideoInflaterApi;
import com.lyl.smzdk.network.api.XgApi;
import com.lyl.smzdk.network.api.YdzxApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
     * 视频
     */
    public static String URL_XG = "https://m.ixigua.com/";
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
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()//
                                .newBuilder()//
                                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")//
                                .addHeader("Accept-Encoding", "gzip, deflate, br")//
                                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")//
                                .addHeader("Connection", "keep-alive")//
                                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Mobile Safari/537.36")//
                                .addHeader("Cookie", "UM_distinctid=15fe7d49584716-0774d592b71f07-5e183017-1fa400-15fe7d49585b32; tt_webid=6491512292630398478; csrftoken=9d0993216394e68e20639fe705e719dd; _ba=BA0.2-20171212-51225-U3xIto01M3RTM3mNZEo3; _ga=GA1.2.1462588719.1511422990")//
                                .build();
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
