package com.justnd.octoryeclient.network.api;

import com.justnd.octoryeclient.entity.base.BaseBean;
import com.justnd.octoryeclient.entity.user.UserInfo;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description: 用户操作接口
 * @throws
 * @time 2019/2/15 0015 下午 3:19
 */

public interface UserService {
    
    /** 
    * @Description: 使用手机号注册
    * @param userJson 用户信息bean
    * @return 
    * @throws 
    * @author Justiniano  Email:jiaodian822@163.com
    */
    @POST("s/user/register/signup")
    Observable<BaseBean<UserInfo>> signUp(@Body UserInfo userJson);

    @POST("s/user/register/signupcheck")
    Observable<BaseBean> signUpCheck(@Body UserInfo userJson);
}
