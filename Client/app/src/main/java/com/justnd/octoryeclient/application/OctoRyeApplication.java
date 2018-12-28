package com.justnd.octoryeclient.application;

import android.app.Application;

public class OctoRyeApplication extends Application {
    public static OctoRyeApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {
        //初始化Leak内存泄露检测工具
//        LeakCanary.install(this);
//        //初始化Stetho调试工具
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    }

    public static OctoRyeApplication getInstance() {
        return mInstance;
    }
}
