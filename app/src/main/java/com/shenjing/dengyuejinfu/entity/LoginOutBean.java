package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1618:55
 * version: 1.0
 * desc   :
 */
public class LoginOutBean implements Serializable {
    private String code;
    private String msg;
    private dataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public dataBean getData() {
        return data;
    }

    public void setData(dataBean data) {
        this.data = data;
    }

    public class  dataBean{

    }
}
