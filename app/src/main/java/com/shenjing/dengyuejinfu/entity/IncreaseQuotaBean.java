package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/2416:46
 * version: 1.0
 * desc   :
 */
public class IncreaseQuotaBean implements Serializable {

    /**
     * msg : 查找成功
     * code : 0000
     * data : [{"id":1,"picturePath":null,"className":"title0","classRemark":"remark0","createTime":null,"updateTime":null,"status":null},{"id":2,"picturePath":null,"className":"title1","classRemark":"remark1","createTime":null,"updateTime":null,"status":null},{"id":3,"picturePath":null,"className":"title2","classRemark":"remark2","createTime":null,"updateTime":null,"status":null},{"id":4,"picturePath":null,"className":"title3","classRemark":"remark3","createTime":null,"updateTime":null,"status":null},{"id":5,"picturePath":null,"className":"title4","classRemark":"remark4","createTime":null,"updateTime":null,"status":null},{"id":6,"picturePath":null,"className":"title5","classRemark":"remark5","createTime":null,"updateTime":null,"status":null},{"id":7,"picturePath":null,"className":"title6","classRemark":"remark6","createTime":null,"updateTime":null,"status":null},{"id":8,"picturePath":null,"className":"title7","classRemark":"remark7","createTime":null,"updateTime":null,"status":null},{"id":9,"picturePath":null,"className":"title8","classRemark":"remark8","createTime":null,"updateTime":null,"status":null},{"id":10,"picturePath":null,"className":"title9","classRemark":"remark9","createTime":null,"updateTime":null,"status":null}]
     */

    private String msg;
    private String code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * picturePath : null
         * className : title0
         * classRemark : remark0
         * createTime : null
         * updateTime : null
         * status : null
         */

        private int id;
        private Object picturePath;
        private String className;
        private String classRemark;
        private Object createTime;
        private Object updateTime;
        private Object status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getPicturePath() {
            return picturePath;
        }

        public void setPicturePath(Object picturePath) {
            this.picturePath = picturePath;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getClassRemark() {
            return classRemark;
        }

        public void setClassRemark(String classRemark) {
            this.classRemark = classRemark;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
    }
}
