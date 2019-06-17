package com.justnd.octoryeclient.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/4/18 0018 下午 9:10
 */
public class CustomStringUtil {

    /**
    * @Description: 将日期转为yyyyMMdd格式的字符串
    * @param date 将被转换的日期
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

}
