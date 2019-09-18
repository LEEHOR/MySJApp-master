package com.shenjing.dengyuejinfu.respondModule;

/**
 * author : Leehor
 * date   : 2019/9/1817:54
 * version: 1.0
 * desc   : 返回参数基类
 */
public class BaseModel {

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
    }
}
