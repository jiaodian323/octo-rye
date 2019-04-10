package com.justnd.octoryeclient.entity.user;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/3/8 0008 下午 6:01
 */
public class UserInfo {
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
    private MobileInfo mobileInfo;

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

    public MobileInfo getMobileInfo() {
        return mobileInfo;
    }

    public void setMobileInfo(MobileInfo mobileInfo) {
        this.mobileInfo = mobileInfo;
    }
}
