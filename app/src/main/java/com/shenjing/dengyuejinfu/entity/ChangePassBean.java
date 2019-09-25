package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1516:48
 * version: 1.0
 * desc   :
 */
public class ChangePassBean implements Serializable {

    /**
     * msg : 密码修改成功
     * code : 0000
     * token : eyJraWQiOiJubGsybGtubCVpbzRtbCIsImFsZyI6IkVTMjU2SyJ9.eyJzdWIiOiJ7XCJjcmVkZW50aWFsc1wiOlwiZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2VcIixcInBhc3N3b3JkXCI6XCJlMTBhZGMzOTQ5YmE1OWFiYmU1NmUwNTdmMjBmODgzZVwiLFwicHJpbmNpcGFsXCI6XCJqa2hkYm9cIixcInJlbWVtYmVyTWVcIjpmYWxzZSxcInVzZXJuYW1lXCI6XCJqa2hkYm9cIn0ifQ.iX-I6iFgTlKjnAsNpbysQ3zSZJIZuRDnZjt3HYvnpMQSd0baQtnqg-9BSM0LGb8dyz7Plk9Z1SWsSutCTe1Y7A
     */

    private String msg;
    private String code;
    private String token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public dataBean getData() {
        return data;
    }

    public void setData(dataBean data) {
        this.data = data;
    }

    public class dataBean {

    }
}
