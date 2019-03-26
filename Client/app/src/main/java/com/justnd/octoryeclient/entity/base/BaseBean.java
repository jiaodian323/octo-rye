package com.justnd.octoryeclient.entity.base;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description: 基础Bean，其他Bean类可继承自该Bean
 * @throws
 * @time 2019/3/20 0020 下午 3:43
 */
public class BaseBean<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
