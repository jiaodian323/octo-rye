/**   
* @Title: MD5Coder.java 
* @Package com.justnd.octoryeserver.security 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年4月3日 下午4:20:07  
*/
package com.justnd.octoryeserver.security;

import java.security.MessageDigest;
import java.util.Base64;

/** 
* @ClassName: MD5Coder 
* @Description: TODO
* @author JD
* @date 2019年4月3日 下午4:36:58 
*  
*/
public class MD5Coder {

	/**
	 * @Fields RSA_ALGORITHM : TODO MD5摘要算法字符串
	 */
	private static final String MD5_ALGORITHM = "MD5";
	
	public static byte[] encodeMD5(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
		
		return md.digest(data.getBytes());
	}
	
	
	/** 
	* @Title: encodeMD5WithSalt 
	* @Description: TODO MD5加盐处理，使用时间戳和密码明文混合（目前混合方式较为简单，日后可做深化）
	* @param @param data
	* @param @param salt
	* @param @return
	* @param @throws Exception
	* @return byte[]
	* @throws 
	*/
	public static byte[] encodeMD5WithSalt(String data, String salt) throws Exception {
		MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
		
		String mix = salt + data;
		return md.digest(mix.getBytes());
	}
	
	/** 
	* @Title: encode 
	* @Description: TODO 对密码明文MD5加盐（时间戳）摘要后重新编码
	* @param @param plaintext
	* @param @param timestamp
	* @param @return Base64编码
	* @param @throws Exception
	* @return String
	* @throws 
	*/
	public static String encode(String plaintext, String timestamp) throws Exception{
		byte[] mdBytes = encodeMD5WithSalt(plaintext, timestamp);
		String base64Str = Base64.getEncoder().encodeToString(mdBytes);
		return base64Str;
	}
}
