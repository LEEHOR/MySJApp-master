package com.shenjing.dengyuejinfu.entity;

/**
 * author : Leehor
 * date   : 2019/9/1213:54
 * version: 1.0
 * desc   : 手机号登录
 */
public class PhoneLoginBean {
    /**
     * 设备识别码
     */
    private String  facilityCode;

    private String phoneNumber;

    private String  yzm;

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public PhoneLoginBean() {
    }
}
