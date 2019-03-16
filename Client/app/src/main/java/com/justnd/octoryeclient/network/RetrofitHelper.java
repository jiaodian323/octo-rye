package com.justnd.octoryeclient.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.justnd.octoryeclient.application.OctoRyeApplication;
import com.justnd.octoryeclient.network.api.RecommendService;
import com.justnd.octoryeclient.network.api.SecurityService;
import com.justnd.octoryeclient.network.api.UserService;
import com.justnd.octoryeclient.network.auxiliary.ApiConstants;
import com.justnd.octoryeclient.utils.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static OkHttpClient mOkHttpClient;
    private static String DEFAULT_BASE_URL = ApiConstants.CLOUD_SERVER_URL;
    private static String DEFAULT_DEBUG_BASE_URL = ApiConstants.DEBUG_URL;

    private static RecommendService mRecommendService;
    private static UserService mUserService;
    private static SecurityService mSecurityService;

    static {
        initOkHttpClient();
    }

    public static RecommendService getBiliTestService () {
//        if (mRecommendService == null) {             暂时注释掉是否为空的判断，为测试代码提供便利
            Retrofit retrofit = buildRetrofit(ApiConstants.RECOMMEND_URL_TEST);
            mRecommendService = retrofit.create(RecommendService.class);
//        }

        return mRecommendService;
    }

    /**
    * @Description: 获取推荐服务的网络接口
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static RecommendService getRecommendService () {
//        if (mRecommendService == null) {             暂时注释掉是否为空的判断，为测试代码提供便利
            Retrofit retrofit = buildRetrofit();
            mRecommendService = retrofit.create(RecommendService.class);
//        }

        return mRecommendService;
    }

    /**
    * @Description: 获取用户操作相关的网络接口
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static UserService getUserService() {
        if (mUserService == null) {
            Retrofit retrofit = buildRetrofit();
            mUserService = retrofit.create(UserService.class);
        }

        return mUserService;
    }

    /**
    * @Description: 获取安全模块相关的网络接口
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static SecurityService getSecurityService() {
        if (mSecurityService == null) {
            Retrofit retrofit = buildRetrofit();
            mSecurityService = retrofit.create(SecurityService.class);
        }

        return mSecurityService;
    }

    /**
    * @Description: 重载创建Retrofit对象接口，使用默认基础URL
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    private static Retrofit buildRetrofit() {
        return buildRetrofit(DEFAULT_DEBUG_BASE_URL);
    }

    /**
    * @Description: 创建Retrofit对象
    * @param baseUrl 基础URL
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    private static Retrofit buildRetrofit(String baseUrl) {
         return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(OctoRyeApplication.getInstance().getCacheDir
                            (), "HttpCache"), 1024 * 1024 * 10);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }


    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("UserInfo-Agent")
                    .addHeader("UserInfo-Agent", ApiConstants.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间60秒(应调试需要，暂时设置为5秒)
            int maxAge = 5;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(OctoRyeApplication.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(OctoRyeApplication.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
