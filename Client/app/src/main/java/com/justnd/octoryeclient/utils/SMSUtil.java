package com.justnd.octoryeclient.utils;

import java.util.regex.Pattern;

public class SMSUtil {
    /**
     * @param
     * @return
     * @throws
     * @Description: 发送验证码短信
     * @author Justiniano  Email:jiaodian822@163.com
     */
    public static void sendAuthCodeSMS() {
    }

    /**
     * @param phoneStr 手机号码字符串
     * @return
     * @throws
     * @Description: 校验手机号码格式是否正确
     * @author Justiniano  Email:jiaodian822@163.com
     */
    public static boolean isPhoneNumber(String phoneStr) {
        return Pattern.compile(ConstantUtil.PHONE_REGEX)
                .matcher(phoneStr)
                .matches();
    }

    /**
     * @param
     * @return
     * @throws
     * @Description: 验证码是否正确
     * @author Justiniano  Email:jiaodian822@163.com
     */
    public static boolean isAuthCodeCorrect(String phoneNumber, String authCode) {
        return true;
    }
}
