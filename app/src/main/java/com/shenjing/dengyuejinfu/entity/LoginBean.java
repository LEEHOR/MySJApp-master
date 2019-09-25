package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1214:54
 * version: 1.0
 * desc   :
 */
public class LoginBean implements Serializable {

    /**
     * msg : 登录成功
     * date : {"name":"18571512117","id":"7"}
     * code : 0000
     */

    private String msg;
    private DataBean data;
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


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 18571512117
         * id : 7
         */

        private String name;
        private String id;
        private String token;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
