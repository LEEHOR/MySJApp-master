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
     * data : {"address":"","id_no":"341226199306151536","back":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5Cimage_back.jpeg?Expires=1884226183&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=FYdZVQJ0Y7jqPuCJu7p3xo0zCH0%3D","real_name":"李浩","state":"9004","front":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5Cimage_front.jpeg?Expires=1884226183&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=M2OYtkmNW%2BTn74Rh4gtz652Q2oc%3D","userId":11}
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
         * address :
         * id_no : 341226199306151536
         * back : http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5Cimage_back.jpeg?Expires=1884226183&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=FYdZVQJ0Y7jqPuCJu7p3xo0zCH0%3D
         * real_name : 李浩
         * state : 9004
         * front : http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5Cimage_front.jpeg?Expires=1884226183&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=M2OYtkmNW%2BTn74Rh4gtz652Q2oc%3D
         * userId : 11
         */

        private String address;
        private String id_no;
        private String back;
        private String real_name;
        private String state;
        private String front;
        private int userId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId_no() {
            return id_no;
        }

        public void setId_no(String id_no) {
            this.id_no = id_no;
        }

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getFront() {
            return front;
        }

        public void setFront(String front) {
            this.front = front;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
