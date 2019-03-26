/**   
* @Title: RSAUtilTest.java 
* @Package com.justnd.octoryeserver.test.security 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年2月27日 上午12:16:32  
*/
package com.justnd.octoryeserver.test.security;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.justnd.octoryeserver.security.RSACoder;

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
public class RSACoderTest {
	private static final String testFilePath = "E:\\MyEclipse_Workplace\\OctoRyeSecurity";
	private static final String publicPath = "\\publicKey.keystore";
	private static final String privatePath = "\\privateKey.keystore";
	
	@Test
	public void genKeyPairTest() {
		RSACoder.genKeyPair(testFilePath);
	}

	@Test
	public void loadPublicKeyByFileTest() {
		String publicKeyStr = "";
		try {
			publicKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + publicPath));
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
			privateKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + publicPath));
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
			publicKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + publicPath));
			publicKey = RSACoder.loadPublicKeyByStr(publicKeyStr);
			encryptByteArray = RSACoder.encrypt(publicKey, plainText.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(new String(encryptByteArray));
		System.out.println("-------------------------------");
		System.out.println(RSACoder.byteToHexString(encryptByteArray));
	}
	
	@Test
	public void privateEncryptTest() {
		String privateKeyStr = "";
		RSAPrivateKey privateKey = null;
		byte[] encryptByteArray = null;
		String plainText = "niubility8790";
		
		try {
			privateKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + privatePath));
			privateKey = RSACoder.loadPrivateKeyByStr(privateKeyStr);
			encryptByteArray = RSACoder.encrypt(privateKey, plainText.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(new String(encryptByteArray));
		System.out.println("-------------------------------");
		System.out.println(RSACoder.byteToHexString(encryptByteArray));
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
			publicKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + publicPath));
			publicKey = RSACoder.loadPublicKeyByStr(publicKeyStr);
			privateKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + privatePath));
			privateKey = RSACoder.loadPrivateKeyByStr(privateKeyStr);
			encryptByteArray = RSACoder.encrypt(publicKey, plainText.getBytes());
			decryptByteArray = RSACoder.decrypt(privateKey, encryptByteArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("明文：" + plainText);
		System.out.print("公钥加密后：");
		System.out.println(RSACoder.byteToHexString(encryptByteArray));
		
		System.out.print("私钥解密密文后为：");
		System.out.println(new String(decryptByteArray));
	}
	
	@Test
	public void publicDecryptTest() {
		String publicKeyStr = "";
		String privateKeyStr = "";
		RSAPublicKey publicKey = null;
		RSAPrivateKey privateKey = null;
		byte[] encryptByteArray = null;
		byte[] decryptByteArray = null;
		String plainText = "十年生死两茫茫";
		
		try {
			publicKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + publicPath));
			publicKey = RSACoder.loadPublicKeyByStr(publicKeyStr);
			privateKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + privatePath));
			privateKey = RSACoder.loadPrivateKeyByStr(privateKeyStr);
			encryptByteArray = RSACoder.encrypt(privateKey, plainText.getBytes());
			decryptByteArray = RSACoder.decrypt(publicKey, encryptByteArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("明文：" + plainText);
		System.out.print("公钥加密后：");
		System.out.println(RSACoder.byteToHexString(encryptByteArray));
		
		System.out.print("私钥解密密文后为：");
		System.out.println(new String(decryptByteArray));
	}
	
	@Test
	public void otherTest() {
		for (Provider p: Security.getProviders()) {
			System.out.println(p);
			
			for (Map.Entry<Object, Object> entry: p.entrySet()) {
				System.out.println("---" + entry.getKey());
			}
		}
	}
	
	@Test
	public void otherTestTwo() {
		byte[] input = "alskdjflaskdjflaskjdflaskjdflaksjdfaksjdflkajsdflkjasldkfj".getBytes();
		try {
			MessageDigest sha = MessageDigest.getInstance("sha");
			sha.update(input);
			byte[] output = sha.digest();
			
			System.out.println(RSACoder.byteToHexString(output));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void bytesArrayTest() {
		String publicKeyStr = "";
		String privateKeyStr = "";
		RSAPublicKey publicKey = null;
		RSAPrivateKey privateKey = null;
		byte[] encryptByteArray = null;
		byte[] decryptByteArray = null;
		String plainText = "十年生死两茫茫";
		
		try {
			publicKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + publicPath));
			publicKey = RSACoder.loadPublicKeyByStr(publicKeyStr);
			privateKeyStr = RSACoder.loadKeyStrByFile(new File(testFilePath + privatePath));
			privateKey = RSACoder.loadPrivateKeyByStr(privateKeyStr);
			
			encryptByteArray = RSACoder.encrypt(publicKey, plainText.getBytes());
			System.out.println("加密后的字节数组：");
			printByteArray(encryptByteArray);
			
			System.out.println("转换为十六进制字符串：");
			String hexString = RSACoder.byteToHexString(encryptByteArray);
			System.out.println(hexString);
			
			System.out.println("十六进制字符串获取的字节数组：");
			byte[] hexBytes = hexString.getBytes();
			printByteArray(hexBytes);
			
			System.out.println("（新的）十六进制字符串获取的字节数组：");
			byte[] newHexBytes = RSACoder.hexStringToByte(hexString);
			printByteArray(newHexBytes);
			
			decryptByteArray = RSACoder.decrypt(privateKey, newHexBytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("明文：" + plainText);
		
		System.out.print("私钥解密密文后为：");
		System.out.println(new String(decryptByteArray));
	}
	
	private void printByteArray(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			sb.append(byteArray[i] + " ");
		}
		
		System.out.println(sb.toString());
	}
}
