/**   
* @Title: GsonUtil.java 
* @Package com.justnd.octoryeserver.utils 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月9日 下午10:36:44  
*/
package com.justnd.octoryeserver.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** 
* @ClassName: GsonUtil 
* @Description: TODO Gson工具类 (暂时不考虑性能问题，只以实现基本功能为主)
* @author JD
* @date 2019年1月9日 下午10:36:44 
*  
*/
public class GsonUtil {
	
	private static Gson gsonInstance;
	
	private GsonUtil() {}
	
	private static Gson getInstance() {
		if (gsonInstance == null) {
			gsonInstance = new GsonBuilder()
	                .setLenient()
	                .enableComplexMapKeySerialization()         // 支持Map的key为复杂对象的形式  
	                .serializeNulls()
	                .setPrettyPrinting()                        // 调教格式  
	                .registerTypeAdapter(String.class, new NullValueTypeAdapter())
	                .create();  
		}
		
		return gsonInstance;
	}
	
	public static String toJsonString(Object obj) {
		return getInstance().toJson(obj);
	}
}
