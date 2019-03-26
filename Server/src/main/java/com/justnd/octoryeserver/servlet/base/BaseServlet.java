/**   
* @Title: BaseServlet.java 
* @Package com.justnd.octoryeserver.servlet.base 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年1月7日 下午1:02:58  
*/
package com.justnd.octoryeserver.servlet.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.justnd.octoryeserver.security.RSACoder;

/** 
* @ClassName: BaseServlet 
* @Description: TODO
* @author JD
* @date 2019年1月7日 下午1:02:58 
*  
*/
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 2212761522044508738L;

	/** 
	* @Title: getRequestBodyStr 读取请求体字符串
	* @Description: TODO
	* @param @param req HTTP请求
	* @param @return
	* @return String
	* @throws 
	*/
	protected String getRequestBodyStr(HttpServletRequest req) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			String tempStr;
			br = req.getReader();
			while ((tempStr = br.readLine()) != null) {
				sb.append(tempStr);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String cipher = sb.toString();
		System.out.println("密文:" + cipher);
		String plaintText = "";
		try {
			System.out.println("私钥：" + RSACoder.rsaPrivateKeyStr);
			byte[] cipherBytes = RSACoder.hexStringToByte(cipher);
			// 调试代码
//			System.out.println("密文字节数组打印");
//			StringBuilder sbTwo = new StringBuilder();
//            for (int i = 0; i < cipherBytes.length; i++) {
//            	sbTwo.append(cipherBytes[i] + " ");
//            }
//            System.out.println(sbTwo.toString());
			RSAPrivateKey privateKey = RSACoder.loadPrivateKeyByStr(RSACoder.rsaPrivateKeyStr);
		    plaintText = new String(RSACoder.decrypt(privateKey, cipherBytes), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("解密明文：" + plaintText);
		return plaintText;
	}
}
