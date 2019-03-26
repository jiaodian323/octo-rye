package com.justnd.octoryeclient.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/3/22 0022 上午 10:44
 */
public class MobileInfoUtil {

    /**
    * @Description: 获取手机IMEI
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static final String getIMEI(Context context) {
        try {
            // 实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
                    (Context.TELEPHONY_SERVICE);
            // 获取IMEI号
            String imei = telephonyManager.getDeviceId();
            // 验证
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (SecurityException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
    * @Description: 获取手机IMSI
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
                    (Context.TELEPHONY_SERVICE);
            // 获取IMSI号
            String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (SecurityException e) {
            e.printStackTrace();
            return "";
        }
    }
}
