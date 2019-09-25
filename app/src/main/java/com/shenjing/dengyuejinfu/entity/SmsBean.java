package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1214:54
 * version: 1.0
 * desc   :
 */
public class SmsBean  implements Serializable {

    /**
     * msg : 获取成功
     * code : 0000
     * userId : 2
     * token : eyJraWQiOiJubGsybGtubCVpbzRtbCIsImFsZyI6IkVTMjU2SyJ9.eyJzdWIiOiJ7XCJjcmVkZW50aWFsc1wiOlwiNDQwZTM3NjAwMGFmY2VjYWY0MDY3NWVlY2RmMWJjYmNcIixcInBhc3N3b3JkXCI6XCI0NDBlMzc2MDAwYWZjZWNhZjQwNjc1ZWVjZGYxYmNiY1wiLFwicHJpbmNpcGFsXCI6XCJqa2hkYm9cIixcInJlbWVtYmVyTWVcIjpmYWxzZSxcInVzZXJuYW1lXCI6XCJqa2hkYm9cIn0ifQ.ugFsOYmOasa64sI0etWHYgdfaiaLM7TUoqghtw7f6AkLAPEQpyRGv5gZVpNheoVCwyfi-SJjhp9Vxxt3N59-1A
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
