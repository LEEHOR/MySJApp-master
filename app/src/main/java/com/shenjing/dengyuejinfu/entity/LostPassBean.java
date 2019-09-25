package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1517:24
 * version: 1.0
 * desc   :
 */
public class LostPassBean implements Serializable {
    private String  code;
    private String msg;

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
}
