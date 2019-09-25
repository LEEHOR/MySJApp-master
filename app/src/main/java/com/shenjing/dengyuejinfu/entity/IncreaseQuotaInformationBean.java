package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/2417:36
 * version: 1.0
 * desc   :
 */
public class IncreaseQuotaInformationBean implements Serializable {

    /**
     * msg : 可以访问
     * code : 0000
     * data : [{"id":1,"title":"查看信息","createTime":"2019-09-17 15:34:30","pageView":0,"url":"www.baidu.com","userId":null,"status":null,"updateTime":null,"accessLevel":"1","type":"1"}]
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
         * title : 查看信息
         * createTime : 2019-09-17 15:34:30
         * pageView : 0
         * url : www.baidu.com
         * userId : null
         * status : null
         * updateTime : null
         * accessLevel : 1
         * type : 1
         */

        private int id;
        private String title;
        private String createTime;
        private int pageView;
        private String url;
        private Object userId;
        private Object status;
        private Object updateTime;
        private String accessLevel;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPageView() {
            return pageView;
        }

        public void setPageView(int pageView) {
            this.pageView = pageView;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getAccessLevel() {
            return accessLevel;
        }

        public void setAccessLevel(String accessLevel) {
            this.accessLevel = accessLevel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
