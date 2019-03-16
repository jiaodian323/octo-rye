package com.justnd.octoryeserver.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/** 
* @ClassName: RSAUtil 
* @Description: TODO 非对称加密RSA工具类
* @author JD
* @date 2019年2月26日 下午8:19:16 
*  
*/
public class RSACoder {
	
	/** 
	* @Fields HEX_CHAR : TODO 字节数据转字符串专用集合
	*/ 
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/** 
	* @Fields RSA_ALGORITHM : TODO RSA非对称加密算法
	*/ 
	private static final String RSA_ALGORITHM = "RSA";
	
	/** 
	* @Fields KEYSTORE_TYPE : TODO KeyStrore类型，即密钥库类型
	*/ 
	private static final String KEYSTORE_TYPE = "JKS";
	
	/** 
	* @Fields KEYSIZE : TODO 密钥长度
	*/ 
	private static final int KEY_SIZE = 1024;
	
	/** 
	* @Title: genKeyPair 
	* @Description: TODO 随机生成密钥对
	* @param @param filePath 密钥对文件保存地址
	* @return void
	* @throws 
	*/
	public static void genKeyPair(String filePath) {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(KEY_SIZE, new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		try {
			// 得到公钥字符串
			String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
			// 得到私钥字符串
			String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
			// 将密钥对写入到文件
			FileWriter publicFW = new FileWriter(filePath + "/publicKey.keystore");
			FileWriter privateFW = new FileWriter(filePath + "/privateKey.keystore");
			BufferedWriter publicBW = new BufferedWriter(publicFW);
			BufferedWriter privateBW = new BufferedWriter(privateFW);
			publicBW.write(publicKeyString);
			privateBW.write(privateKeyString);
			publicBW.flush();
			publicBW.close();
			publicFW.close();
			privateBW.flush();
			privateBW.close();
			privateFW.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//  添加CA认证时，需要抛弃原代码，并使用此部分代码，此部分代码为KeyStore存储的标准写法，
//	需要设置KeyStore内容别名和访问密码等，并需要存储证书链，否则现有仅保存公钥和私钥的方式并不是一种标准的且更安全的方式
//	private void savePublicKeyToFile(String filePath, String fileName, String publicKeyStr) {
//		try {
//			FileWriter publicFW = new FileWriter(filePath + "/publicKey.keystore");
//			BufferedWriter publicBW = new BufferedWriter(publicFW);
//			publicBW.write(publicKeyStr);
//			publicBW.flush();
//			publicBW.close();
//			publicFW.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void savePrivateKeyToFile(String filePath, String password, PrivateKey privateKey) {
//		try {
//			KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
//			KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password.toCharArray());
//			KeyStore.PrivateKeyEntry pkEntry = new KeyStore.PrivateKeyEntry(privateKey, chain);
//		} catch (KeyStoreException e) {
//			e.printStackTrace();
//		}
//	}
 
	/**
	 * 从文件中输入流中加载公钥(读取keystore文件方式有问题，应使用KeyStore类，需要重构)
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static String loadKeyStrByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("输入流为空");
		}
	}
 
	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
			throws Exception {
		try {
			byte[] buffer =Base64.getDecoder().decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}
 
	/** 
	* @Title: loadPrivateKeyByStr 
	* @Description: TODO 根据私钥字符串获取私钥对象
	* @param @param privateKeyStr 私钥字符串
	* @param @return
	* @param @throws Exception
	* @return RSAPrivateKey
	* @throws 
	*/
	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}
 
	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance(RSA_ALGORITHM);
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}
 
	/**
	 * 私钥加密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}
 
	/**
	 * 私钥解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance(RSA_ALGORITHM);
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}
 
	/**
	 * 公钥解密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance(RSA_ALGORITHM);
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}
	
	/** 
	* @Title: isPublicKeyFileValid 
	* @Description: TODO 判断公钥文件内容是否可用
	* @param @param path
	* @param @return
	* @return boolean
	* @throws 
	*/
	public static boolean isPublicKeyFileValid(File file) {
		String filePath = file.getPath();
		try {
			String publicKeyStr = loadKeyStrByFile(filePath);
			loadPublicKeyByStr(publicKeyStr);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	} 
	
	/** 
	* @Title: isPrivateKeyFileValid 
	* @Description: TODO 判断私钥文件内容是否可用
	* @param @param path
	* @param @return
	* @return boolean
	* @throws 
	*/
	public static boolean isPrivateKeyFileValid(File file) {
		String filePath = file.getPath();
		try {
			String publicKeyStr = loadKeyStrByFile(filePath);
			loadPrivateKeyByStr(publicKeyStr);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
 
	/**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}
}
