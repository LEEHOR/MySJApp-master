package com.shenjing.dengyuejinfu.respondModule;

/**
 * author : Leehor
 * date   : 2019/9/1817:55
 * version: 1.0
 * desc   :
 */
public class PeopleCertificationStatus {


    /**
     * msg : 查询成功
     * code : 0000
     * data : {"state":"9002"}
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

    public static class DataBean {
        /**
         * state : 9002
         */

        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
