/**   
* @Title: UserInfo.java 
* @Package com.justnd.octoryeserver.beans.user 
* @Description: TODO
* @author JD 
* @EMail jiaodian822@163.com 
* @date 2019年3月22日 下午4:41:55  
*/
package com.justnd.octoryeserver.beans.user;

/** 
* @ClassName: UserInfo 
* @Description: TODO
* @author JD
* @date 2019年3月22日 下午4:41:55 
*  
*/
public class UserInfoBean {
	private int id;
    private String userName;
    private String nickName;
    private int sex;
    private String phoneNumber;
    private String createDate;
    private String profilePicture;
    private String wechat;
    private String weibo;
    private String background;
    private MobileInfoBean mobileInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public MobileInfoBean getMobileInfo() {
        return mobileInfo;
    }

    public void setMobileInfo(MobileInfoBean mobileInfo) {
        this.mobileInfo = mobileInfo;
    }
}
