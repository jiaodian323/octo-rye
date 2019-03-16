package com.justnd.octoryeclient.network.api;

import com.justnd.octoryeclient.entity.recommond.ArticleDetail;
import com.justnd.octoryeclient.entity.recommond.RecommendBannerInfo;
import com.justnd.octoryeclient.entity.recommond.RecommendInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RecommendService {

    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Observable<RecommendBannerInfo> getRecommendedBannerInfo();

    /**
    * @Description: 获取每日推荐接口
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    @GET("s/show/recommend")
    Observable<RecommendInfo> getRecommendedInfo();

    /**
    * @Description: 获取文章详细内容接口
    * @param aid 文章内容ID
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    @GET("s/show/articledetail")
    Observable<ArticleDetail> getArticleDetail(@Query("aid") int aid);
}
