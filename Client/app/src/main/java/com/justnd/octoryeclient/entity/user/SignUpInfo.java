package com.justnd.octoryeclient.entity.user;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/4/3 0003 上午 9:39
 */
public class SignUpInfo {
    /**
    * @Fields: 注册模式：0、保留；1、手机；2、微信；3、微博；
    */
    private int signUpMode;
    /**
    * @Fields: 注册手机号
    */
    private String phoneNumber;
    /**
    * @Fields: 密码
    */
    private String pass;

    private String wechat;

    private String weibo;

    public int getSignUpMode() {
        return signUpMode;
    }

    public void setSignUpMode(int signUpMode) {
        this.signUpMode = signUpMode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }
}
