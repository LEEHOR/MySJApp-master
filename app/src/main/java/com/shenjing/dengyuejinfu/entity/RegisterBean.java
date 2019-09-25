package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1212:05
 * version: 1.0
 * desc   :
 */
public class RegisterBean implements Serializable {

    /**
     * msg : 注册成功
     * code : 0000
     * userId : 2
     * token : eyJraWQiOiJubGsybGtubCVpbzRtbCIsImFsZyI6IkVTMjU2SyJ9.eyJzdWIiOiJ7XCJjcmVkZW50aWFsc1wiOlwiNDQwZTM3NjAwMGFmY2VjYWY0MDY3NWVlY2RmMWJjYmNcIixcInBhc3N3b3JkXCI6XCI0NDBlMzc2MDAwYWZjZWNhZjQwNjc1ZWVjZGYxYmNiY1wiLFwicHJpbmNpcGFsXCI6XCJqa2hkYm9cIixcInJlbWVtYmVyTWVcIjpmYWxzZSxcInVzZXJuYW1lXCI6XCJqa2hkYm9cIn0ifQ.FYO9o_GaKRh1hEYaKYJHqxTuZlGLuyfba7NzXO-9bbnLdDF0S7qBQkCCW1ZytKUqyem6JrRzIPqrHi0R4xwYJw
     */

    private String msg;
    private String code;
    private dataBean data;
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

    public dataBean getData() {
        return data;
    }

    public void setData(dataBean data) {
        this.data = data;
    }

    public class dataBean{
        private String userId;
        private String token;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
