package com.shenjing.dengyuejinfu.entity;

/**
 * author : Leehor
 * date   : 2019/9/1210:55
 * version: 1.0
 * desc   :
 */
public class UserRegisterBean {
    /**
     * '邀请码'
     */
    private String inviteCode;

    /**
     * '用户的昵称'
     */
    private String userName;

    /**
     * '用户登陆的密码，MD5加密'
     */
    private String password;

    /**
     * '用户的手机号'
     */
    private String phoneNumber;

    /**
     * '用户的真实姓名'
     */
    private String realName;

    /**
     * 最后一次修改的密码
     */
    private String lastPassword;


    /**
     * 最后一次登录的地点
     */
    private String lastLoginAddr;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 邀请人邀请码
     */
    private String invitedCode;

    /**
     * 设备唯一识别码
     */
    private String facilityCode;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLastPassword() {
        return lastPassword;
    }

    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public String getLastLoginAddr() {
        return lastLoginAddr;
    }

    public void setLastLoginAddr(String lastLoginAddr) {
        this.lastLoginAddr = lastLoginAddr;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public UserRegisterBean() {
    }

    @Override
    public String toString() {
        return "UserRegisterBean{" +
                "inviteCode='" + inviteCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", realName='" + realName + '\'' +
                ", lastPassword='" + lastPassword + '\'' +
                ", lastLoginAddr='" + lastLoginAddr + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", invitedCode='" + invitedCode + '\'' +
                ", facilityCode='" + facilityCode + '\'' +
                '}';
    }
}
