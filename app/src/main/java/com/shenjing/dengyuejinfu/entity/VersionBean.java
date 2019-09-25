package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/2316:31
 * version: 1.0
 * desc   :
 */
public class VersionBean implements Serializable {

    /**
     * msg : 本次版本号
     * code : 0000
     * data : {"state ":"10 ","versionId":"1.0","forceUpdate":"1","description":"1、修复支付体验问题\n2、加速审核和放款速度！","path":"https://eloan-app.oss-cn-hangzhou.aliyuncs.com/EDai-release_2.0.0.apk"}
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
         * state  : 10
         * versionId : 1.0
         * forceUpdate : 1
         * description : 1、修复支付体验问题
         2、加速审核和放款速度！
         * path : https://eloan-app.oss-cn-hangzhou.aliyuncs.com/EDai-release_2.0.0.apk
         */

        private String state;
        private String versionId;
        private String forceUpdate;
        private String description;
        private String path;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getVersionId() {
            return versionId;
        }

        public void setVersionId(String versionId) {
            this.versionId = versionId;
        }

        public String getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(String forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
