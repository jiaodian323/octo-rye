/**   
* @Title: MobileInfo.java 
* @Package com.justnd.octoryeserver.beans.user 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月22日 下午4:42:54  
*/
package com.justnd.octoryeserver.beans.user;

/** 
* @ClassName: MobileInfo 
* @Description: TODO
* @author JD
* @date 2019年3月22日 下午4:42:54 
*  
*/
public class MobileInfoBean {
	private String osSystem;
    private String imei;
    private String imsi;

    public String getOsSystem() {
        return osSystem;
    }

    public void setOsSystem(String osSystem) {
        this.osSystem = osSystem;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
}
