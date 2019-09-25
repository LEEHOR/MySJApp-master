package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1814:33
 * version: 1.0
 * desc   :
 */
public class AddCreditCardBean implements Serializable {

    /**
     * msg : 上传成功
     * code : 0000
     */

    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
