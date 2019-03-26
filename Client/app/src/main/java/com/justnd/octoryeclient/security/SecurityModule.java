package com.justnd.octoryeclient.security;

import android.util.Log;

import com.justnd.octoryeclient.R;
import com.justnd.octoryeclient.utils.ToastUtil;

import java.security.interfaces.RSAPublicKey;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/3/14 0014 下午 9:27
 */
public class SecurityModule {
    /**
    * @Fields: 调试TAG
    */
    public static final String TAG = "SecurityModule";
    /**
     * @Fields: 保存base64编码的公钥字符串静态字段
     */
    public static String mRSAPublicKeyStrBase64;
    /**
    * @Fields: RSA公钥
    */
    private RSAPublicKey mRSAPublicKey;

    /**
    * @Fields: RSA工具类
    */
    private RSAUtil mRSAUtil;

    public SecurityModule() {
        mRSAUtil = new RSAUtil();

        // 读取公钥
        if (mRSAPublicKeyStrBase64 != null && mRSAPublicKeyStrBase64 != "") {
            try {
                mRSAPublicKey = mRSAUtil.loadPublicKeyByStr(mRSAPublicKeyStrBase64);
            } catch (Exception e) {
                ToastUtil.ShortToast(R.string.exception_security_module_initial);
            }
        }
    }

    public String receivePublicKey() {
        return null;
    }

    /**
    * @Description: 将明文加密后以十六进制编码的密文字符串返回
    * @param plaintText 明文
    * @return 十六进制密文
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public String encrypt(String plaintText) {
        byte[] plaintBytes = plaintText.getBytes();
        String cipherStr = "";
        try {
            byte[] cipherBytes = mRSAUtil.encrypt(mRSAPublicKey, plaintBytes);
//            cipherStr = new String(cipherBytes, ENCODE);
            System.out.println("密文字节数组打印");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cipherBytes.length; i++) {
                sb.append(cipherBytes[i] + " ");
            }
            Log.i("byteArrayPrint:", sb.toString());
            cipherStr = byteToHexString(cipherBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherStr;
    }

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteToHexString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (data == null || data.length <= 0) {
            return null;
        }
        for (int i = 0; i < data.length; i++) {
            int v = data[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
    * @Description: 16进制字符串转字符数组
    * @param
    * @return
    * @throws
    * @author Justiniano  Email:jiaodian822@163.com
    */
    public static byte[] hexStringToByte(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
