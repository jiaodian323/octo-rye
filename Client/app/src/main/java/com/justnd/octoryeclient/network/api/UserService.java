package com.justnd.octoryeclient.network.api;

import com.justnd.octoryeclient.entity.base.BaseBean;
import com.justnd.octoryeclient.entity.user.LoginBean;
import com.justnd.octoryeclient.entity.user.SignUpInfo;
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
    * @param signUp 注册信息bean
    * @return 
    * @throws 
    * @author Justiniano  Email:jiaodian822@163.com
    */
    @POST("s/user/register/signup")
    Observable<BaseBean> signUp(@Body SignUpInfo signUp);

    /**
    * @Description: 注册查询
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    @POST("s/user/register/signupcheck")
    Observable<BaseBean> signUpCheck(@Body UserInfo user);

    /** 
    * @Description: 登录请求
    * @param
    * @return
    * @throws 
    * @author Justiniano  Email:jiaodian822@163.com
    */
    @POST("s/user/login")
    Observable<BaseBean> login(@Body LoginBean login);
}
