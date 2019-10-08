package com.shenjing.dengyuejinfu.entity;

/**
 * author : Leehor
 * date   : 2019/9/3014:42
 * version: 1.0
 * desc   :
 */
public class AliyunNotificationBean {

    /**
     * code : string
     * data : {}
     * msg : string
     */

    private String code;
    private DataBean data;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
