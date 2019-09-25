package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1915:27
 * version: 1.0
 * desc   :
 */
public class PaymentBean implements Serializable {

    /**
     * msg : 该用户未认证
     * code : 0000
     * data : null
     */

    private String msg;
    private String code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String state;
        private String takeImg;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTakeImg() {
            return takeImg;
        }

        public void setTakeImg(String takeImg) {
            this.takeImg = takeImg;
        }
    }
}
