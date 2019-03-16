package com.justnd.octoryeclient.network.api;

import com.justnd.octoryeclient.entity.user.SignUpCheck;
import com.justnd.octoryeclient.entity.user.UserInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    * @param phoneNumber 手机号码
    * @return 
    * @throws 
    * @author Justiniano  Email:jiaodian822@163.com
    */
    @POST("s/user/register/signup")
    @FormUrlEncoded
    Observable<UserInfo> signUpByPhoneNumber(@Field("phoneNumber")String phoneNumber);

    @POST("s/user/register/signupcheck")
    @FormUrlEncoded
    Observable<SignUpCheck> signUpCheckByPhoneNumber(@Field("phoneNumber")String phoneNumber);
}
