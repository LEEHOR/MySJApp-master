package com.shenjing.dengyuejinfu.entity;

/**
 * author : Leehor
 * date   : 2019/9/1517:22
 * version: 1.0
 * desc   :忘记密码
 */
public class LostPassBean {
    private String phone;
    private String vcode;
    private  String newPwd;
    private String signMsg;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    public LostPassBean() {
    }

    @Override
    public String toString() {
        return "LostPassBean{" +
                "phone='" + phone + '\'' +
                ", vcode='" + vcode + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }
}
