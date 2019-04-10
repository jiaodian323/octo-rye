/**   
* @Title: ConstantUtil.java 
* @Package com.justnd.octoryeserver.utils 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月26日 下午4:57:44  
*/
package com.justnd.octoryeserver.util;

/** 
* @ClassName: ConstantUtil 
* @Description: TODO
* @author JD
* @date 2019年1月26日 下午4:57:44 
*  
*/
public class ConstantUtil {
	
	/** 
	* @Fields STATUS_CODE_FAIL : TODO 失败信息状态码
	*/ 
	public static final int STATUS_CODE_FAIL = 0;
	
	/** 
	* @Fields STATUS_CODE_SUCCESS : TODO 成功信息状态码
	*/ 
	public static final int STATUS_CODE_SUCCESS = 1;
	
	/** 
	* @Fields STATUS_CODE_OTHER : TODO 其他类型状态码
	*/ 
	public static final int STATUS_CODE_OTHER = 2;
	
	/** 
	* @Fields TYPE_ARTICLE : TODO 类型：文章
	*/ 
	public static final String TYPE_ARTICLE = "article";
	
	/** 
	* @Fields TYPE_MUSIC : TODO 类型：音乐
	*/ 
	public static final String TYPE_MUSIC = "music";
	
	/** 
	* @Fields TYPE_VIDEO : TODO 类型：视频
	*/ 
	public static final String TYPE_VIDEO = "video";
	
	/** 
	* @Fields TYPE_AUDIO : TODO 类型：音频
	*/ 
	public static final String TYPE_AUDIO = "audio";
	
	/** 
	* @Fields PUBLIC_KEY_FILE_NAME : TODO 公钥文件名
	*/ 
	public static final String PUBLIC_KEY_FILE_NAME = "\\publickey.keystore";
	
	/** 
	* @Fields PRIVATE_KEY_FILE_NAME : TODO 私钥文件名
	*/ 
	public static final String PRIVATE_KEY_FILE_NAME = "\\privateKey.keystore";
	
	public static final String PHONE_NUMBER_ALREADY_SIGNED_UP = "该手机已被注册，不能重复注册";
	
	public static final String SUCCESS_SEND_AUTH_SMS = "成功发送验证码短信";
	
	public static final String SUCCESS_SEND_RECOMMENDED = "成功推送热门";
	
	public static final String SUCCESS_SEND_ARTICLE_DETAIL = "成功查询文章内容";
	
	public static final String SUCCESS_SIGN_UP = "注册成功";
	
	public static final String FAILED_SIGN_UP = "注册失败";
	
	public static final String SUCCESS_LOGIN = "登录成功";
	
	public static final String FAILED_LOGIN = "登录失败";
	
	public static final String SUCCESS_USER_INFO_QUERY = "成功查询用户信息";
}
