package com.justnd.octoryeclient.network.api;

import com.justnd.octoryeclient.entity.recommond.RecommendBannerInfo;
import com.justnd.octoryeclient.entity.recommond.RecommendInfo;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RecommendService {
    @GET("x/show/old?platform=android&device=&build=412001")
    Observable<RecommendInfo> getRecommendedInfo();

    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Observable<RecommendBannerInfo> getRecommendedBannerInfo();

    @GET("s/show/recommend")
    Observable<RecommendInfo> getRecommendedInfoDebug();
}
