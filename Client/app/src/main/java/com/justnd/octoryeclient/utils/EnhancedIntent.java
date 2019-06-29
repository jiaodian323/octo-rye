package com.justnd.octoryeclient.utils;

import android.content.Context;
import android.content.Intent;

/**
 * @author Justiniano
 * @Description: 增强型Intent，用以被启动Activity获取父Activity实例
 * @throws
 * @Email jiaodian822@163.com
 * @time 2019/6/23 0023 下午 9:23
 */
public class EnhancedIntent extends Intent {
    private Context mFatherContext;

    public EnhancedIntent (Context packageContext, Class<?> cls) {
        super(packageContext, cls);
        mFatherContext = packageContext;
    }

    public Context getFatherContext() {
        return mFatherContext;
    }
}
