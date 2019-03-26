package com.justnd.octoryeclient.network.custom;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.justnd.octoryeclient.utils.NullValueTypeAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description: 自定义Retrofit的json数据转换器，可在转换器中根据实际需要添加数据加解密等功能
 * @throws
 * @time 2019/3/20 0020 下午 3:46
 */
public class CustomJsonConverterFactory extends Converter.Factory {
    private Gson gson;

    private CustomJsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static CustomJsonConverterFactory create() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .enableComplexMapKeySerialization()         // 支持Map的key为复杂对象的形式
                .serializeNulls()
                .setPrettyPrinting()                        // 调教格式
                .registerTypeAdapter(String.class, new NullValueTypeAdapter())
                .create();

        return create(gson);
    }

    public static CustomJsonConverterFactory create(Gson gson) {
        Log.i("CusRequestConverter", "创建Factory");
        return new CustomJsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        Log.i("CusRequestConverter", "Factory进入response converter");
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomJsonResponseConverter<>(adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Log.i("CusRequestConverter", "Factory进入request converter");
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomJsonRequestConverter<>(gson, adapter);
    }
}
