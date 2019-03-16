/**   
* @Title: FileUtil.java 
* @Package com.justnd.octoryeserver.util 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月16日 下午2:54:31  
*/
package com.justnd.octoryeserver.util;

/** 
* @ClassName: FileUtil 
* @Description: TODO 文件工具类
* @author JD
* @date 2019年3月16日 下午2:54:31 
*  
*/
public class FileUtil {
    /** 
    * @Title: getWebInfPath 
    * @Description: TODO 获取项目中WEB_INF目录（该写法是有Bug可能的，
    * 例如项目路径中包含被替换字符串，可能导致该方法出现错误）
    * @param @return 目录字符串
    * @return String
    * @throws 
    */
    public static String getWebInfSecPath() {
    	String path = Thread.currentThread().getContextClassLoader().getResource("").toString();  
		path = path.replace('/', '\\');              // 将/换成\  
        path = path.replace("file:", "");            // 去掉file:  
        path = path.replace("%20", " ");             // 替换
        path = path.replace("classes\\", "sec");     // 替换class目录为sec目录  
        path = path.substring(1);                    // 去掉第一个\,如 \D:\JavaWeb...  
		
		return path;
    }
}
