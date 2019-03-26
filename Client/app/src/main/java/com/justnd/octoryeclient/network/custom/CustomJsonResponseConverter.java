package com.justnd.octoryeclient.network.custom;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/3/20 0020 下午 4:11
 */
class CustomJsonResponseConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    CustomJsonResponseConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        try {
            return adapter.fromJson(value.charStream());
        } finally {
            value.close();
        }
    }
}
