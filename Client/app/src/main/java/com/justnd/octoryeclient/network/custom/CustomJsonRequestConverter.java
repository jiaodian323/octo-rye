package com.justnd.octoryeclient.network.custom;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.justnd.octoryeclient.security.SecurityModule;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/3/20 0020 下午 4:19
 */
class CustomJsonRequestConverter<T> implements Converter<T, RequestBody> {
    private static final String TAG =  "CusRequestConverter";

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final SecurityModule security;

    CustomJsonRequestConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
        this.security = new SecurityModule();
    }

    @Override
    public RequestBody convert(T value) {
        Log.i(TAG, "请求的value类型：" + value.getClass().getSimpleName());
        String plaintJsonText = adapter.toJson(value);
        Log.i(TAG, "plaintJsonText:" + plaintJsonText);
        String cipherText = security.encrypt(plaintJsonText);

        return RequestBody.create(MEDIA_TYPE, cipherText);
    }
}
