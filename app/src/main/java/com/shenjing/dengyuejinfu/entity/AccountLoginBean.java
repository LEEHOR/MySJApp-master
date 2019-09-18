package com.shenjing.dengyuejinfu.entity;

/**
 * author : Leehor
 * date   : 2019/9/1213:57
 * version: 1.0
 * desc   :
 */
public class AccountLoginBean {

    private String facilityCode;

    private String password;

    private String userName;

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AccountLoginBean() {
    }

    @Override
    public String toString() {
        return "AccountLoginBean{" +
                "facilityCode='" + facilityCode + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
