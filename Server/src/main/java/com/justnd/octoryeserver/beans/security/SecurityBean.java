/**   
* @Title: SecurityBean.java 
* @Package com.justnd.octoryeserver.beans 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月18日 下午3:51:24  
*/
package com.justnd.octoryeserver.beans.security;

/** 
* @ClassName: SecurityBean 
* @Description: TODO 安全信息Bean
* @author JD
* @date 2019年3月18日 下午3:51:24 
*  
*/
public class SecurityBean {
	private String updateTime;
    private String expirationTime;
    private String publicKeyString;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getPublicKeyString() {
        return publicKeyString;
    }

    public void setPublicKeyString(String publicKeyString) {
        this.publicKeyString = publicKeyString;
    }
}
