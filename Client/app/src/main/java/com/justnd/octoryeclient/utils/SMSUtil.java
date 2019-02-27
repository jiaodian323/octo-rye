package com.justnd.octoryeclient.utils;

import java.util.regex.Pattern;

public class SMSUtil {
    /** 
    * @Description: 发送验证码短信
    * @param 
    * @return 
    * @throws 
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static void sendAuthCodeSMS() {
    }

    /**
     * @Description: 校验手机号码格式是否正确
     * @param phoneStr 手机号码字符串
     * @return
     * @throws
     * @author Justiniano  Email:jiaodian822@163.com
     */
    public static boolean isPhoneNumber(String phoneStr) {
        return Pattern.compile(ConstantUtil.PHONE_REGEX)
                .matcher(phoneStr)
                .matches();
    }

    /**
    * @Description: 验证码是否正确
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static boolean isAuthCodeCorrect(String phoneNumber, String authCode) {
        // 测试代码
        if (phoneNumber == "18601246975" && authCode == "8888")
            return true;
        else
            return false;
    }
}
