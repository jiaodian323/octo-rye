/**   
* @Title: RSAUtilTest.java 
* @Package com.justnd.octoryeserver.test.security 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年2月27日 上午12:16:32  
*/
package com.justnd.octoryeserver.test.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.justnd.octoryeserver.security.RSAUtil;

/** 
* @ClassName: RSAUtilTest 
* @Description: TODO
* @author JD
* @date 2019年2月27日 上午12:16:32 
*  
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
"classpath*:/daoContext.xml" })
public class RSAUtilTest {
	private static final String testFilePath = "E:\\MyEclipse_Workplace\\OctoRyeSecurity";
	
	@Test
	public void genKeyPairTest() {
		RSAUtil.genKeyPair(testFilePath);
	}

	@Test
	public void loadPublicKeyByFileTest() {
		String publicKeyStr = "";
		try {
			publicKeyStr = RSAUtil.loadPublicKeyByFile(testFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(publicKeyStr);
	}
	
	@Test
	public void loadPrivateKeyByFileTest() {
		String privateKeyStr = "";
		try {
			privateKeyStr = RSAUtil.loadPrivateKeyByFile(testFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(privateKeyStr);
	}
	
	@Test
	public void publicEncryptTest() {
		String publicKeyStr = "";
		RSAPublicKey publicKey = null;
		byte[] encryptByteArray = null;
		String plainText = "niubility8790";
		
		try {
			publicKeyStr = RSAUtil.loadPublicKeyByFile(testFilePath);
			publicKey = RSAUtil.loadPublicKeyByStr(publicKeyStr);
			encryptByteArray = RSAUtil.encrypt(publicKey, plainText.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(new String(encryptByteArray));
		System.out.println("-------------------------------");
		System.out.println(RSAUtil.byteArrayToString(encryptByteArray));
	}
	
	@Test
	public void privateEncryptTest() {
		String privateKeyStr = "";
		RSAPrivateKey privateKey = null;
		byte[] encryptByteArray = null;
		String plainText = "niubility8790";
		
		try {
			privateKeyStr = RSAUtil.loadPrivateKeyByFile(testFilePath);
			privateKey = RSAUtil.loadPrivateKeyByStr(privateKeyStr);
			encryptByteArray = RSAUtil.encrypt(privateKey, plainText.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(new String(encryptByteArray));
		System.out.println("-------------------------------");
		System.out.println(RSAUtil.byteArrayToString(encryptByteArray));
	}
	
	@Test
	public void publicDecryptTest() {
		
	}
	
	@Test
	public void privateDecryptTest() {
		String publicKeyStr = "";
		String privateKeyStr = "";
		RSAPublicKey publicKey = null;
		RSAPrivateKey privateKey = null;
		byte[] encryptByteArray = null;
		byte[] decryptByteArray = null;
		String plainText = "abcdefghigklmnopqrstuvwxyz12345678900987654321";
		
		try {
			publicKeyStr = RSAUtil.loadPublicKeyByFile(testFilePath);
			publicKey = RSAUtil.loadPublicKeyByStr(publicKeyStr);
			privateKeyStr = RSAUtil.loadPrivateKeyByFile(testFilePath);
			privateKey = RSAUtil.loadPrivateKeyByStr(privateKeyStr);
			encryptByteArray = RSAUtil.encrypt(publicKey, plainText.getBytes());
			decryptByteArray = RSAUtil.decrypt(privateKey, encryptByteArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("明文：" + plainText);
		System.out.print("公钥加密后：");
		System.out.println(RSAUtil.byteArrayToString(encryptByteArray));
		
		System.out.print("私钥解密密文后为：");
		System.out.println(new String(decryptByteArray));
	}
}
