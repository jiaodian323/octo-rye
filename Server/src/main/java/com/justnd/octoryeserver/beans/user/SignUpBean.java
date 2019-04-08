package com.justnd.octoryeserver.beans.user;

/**
 * @author Justiniano  Email:jiaodian822@163.com
 * @Description:
 * @throws
 * @time 2019/4/3 0003 上午 9:39
 */
public class SignUpBean {
    private int signUpMode;
    private String phoneNumber;
    private String pass;
    private String signUpTime;

    public int getSignUpMode() {
        return signUpMode;
    }

    public void setSignUpMode(int signUpMode) {
        this.signUpMode = signUpMode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(String sighUpTime) {
        this.signUpTime = sighUpTime;
    }
}
