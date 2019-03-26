package com.justnd.octoryeclient.network.api;

import com.justnd.octoryeclient.entity.base.BaseBean;
import com.justnd.octoryeclient.entity.security.SecurityInfo;

import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/3/15 0015 下午 2:50
 */
public interface SecurityService {
    @POST("s/sec/info")
    Observable<BaseBean<SecurityInfo>> getSecurityInfo();
}
