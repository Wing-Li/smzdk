package com.lyl.smzdk.network.api;

import com.lyl.smzdk.network.entity.images.Album;
import com.lyl.smzdk.network.entity.images.ImageInfo;
import com.lyl.smzdk.network.entity.images.ImageMenu;
import com.lyl.smzdk.network.entity.myapi.BaseCallBack;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: lyl
 * <p>
 * Date Created : 2018/8/3.
 */
public interface MyMnApi {

//    // method Url, headers, body参数都可以动态外部传入
//    @FormUrlEncoded
//    @POST("{url}")
//    Observable<ResponseBody> executePost(@Path("url") String url, @FieldMap Map<String, String> maps);

    // ========================================================↓用户相关↓========================================================================

    /**
     * 获取排行榜的类型
     */
    @GET("ranktype/getAll")
    Observable<BaseCallBack<List<ImageMenu>>> getRankType();

    /**
     * 随机获取图集列表
     */
    @POST("album/getRandomList")
    Observable<BaseCallBack<List<Album>>> getAlbumRandomList(@Query("rankId") long rankId);

    /**
     * 获取指定图集的图片
     */
    @POST("image/getList")
    Observable<BaseCallBack<List<ImageInfo>>> getAlbumImageList(@Query("albumId") long albumId);
}
