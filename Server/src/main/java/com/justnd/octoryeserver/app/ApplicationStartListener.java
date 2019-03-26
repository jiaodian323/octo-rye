/**   
* @Title: ApplicationStartListener.java 
* @Package com.justnd.octoryeserver.app 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月15日 下午5:15:26  
*/
package com.justnd.octoryeserver.app;

import java.io.File;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.justnd.octoryeserver.security.RSACoder;
import com.justnd.octoryeserver.util.ConstantUtil;
import com.justnd.octoryeserver.util.FileUtil;

/**
 * @ClassName: ApplicationStartListener
 * @Description: TODO 服务器启动预加载操作类
 * @author JD
 * @date 2019年3月15日 下午5:15:26
 * 
 */
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		genKeys();
	}

	/**
	 * @Title: genKeys 根据实际情况生成公钥和私钥文件 @Description: TODO @param @return
	 * void @throws
	 */
	public void genKeys() {
		System.out.println("--------------------------------------------------------------");
		String webInfSecPath = FileUtil.getWebInfSecPath();
		File publicKeyFile = new File(webInfSecPath + ConstantUtil.PUBLIC_KEY_FILE_NAME);
		File privateKeyFile = new File(webInfSecPath + ConstantUtil.PRIVATE_KEY_FILE_NAME);
		// 如果公钥和私钥文件均存在，并且都是可用的，则不做任何操作，否则重新生成公钥和私钥文件
		if (publicKeyFile.exists() && privateKeyFile.exists()) {
			if (!RSACoder.isPublicKeyFileValid(publicKeyFile)
					|| !RSACoder.isPrivateKeyFileValid(privateKeyFile)) {
				System.out.println("公钥或者私钥文件不可用，重新生成");
				RSACoder.genKeyPair(webInfSecPath);
			}
		} else {
			System.out.println("公钥或者私钥文件不存在，重新生成");
			RSACoder.genKeyPair(webInfSecPath);
		}

		try {
			RSACoder.rsaPublicKeyStr = RSACoder.loadKeyStrByFile(publicKeyFile);
			RSACoder.rsaPrivateKeyStr = RSACoder.loadKeyStrByFile(privateKeyFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("--------------------------------------------------------------");
	}
}
