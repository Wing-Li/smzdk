package com.lyl.smzdk.network.converter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.lyl.smzdk.utils.DESHelper;
import com.lyl.smzdk.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Author: lyl
 * Date Created : 2018/7/25.
 */
public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        try {
            String result = value.string();
            // 这里返回来的数据 前后都多了个双引号
            String decrypt = DESHelper.decrypt(result.substring(1, result.length() - 1));
            LogUtils.d("返回的Json：" + decrypt);
            return adapter.fromJson(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return null;
    }
}