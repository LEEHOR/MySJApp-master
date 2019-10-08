package com.shenjing.dengyuejinfu.entity;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/3013:50
 * version: 1.0
 * desc   :
 */
public class LoanListBean {

    /**
     * msg : 获取成功
     * code : 0000
     * data : {"img":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//7e861c73-e7e4-44df-9549-113a17c50e14%5C%E5%BE%AE%E6%9C%8D%E5%8A%A1.jpg?Expires=1884074142&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=oAkPb1KQzwDbSVDwqhuObpo8yNQ%3D","loadList":[{"id":1,"name":"宜人贷","limitations":null,"introduction":"简介1","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T19:30:44.000+0800"},{"id":2,"name":"宜人贷","limitations":null,"introduction":"简介1","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T19:30:44.000+0800"},{"id":4,"name":"宜人贷","limitations":null,"introduction":"简介1","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T19:30:44.000+0800"}]}
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
         * img : http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//7e861c73-e7e4-44df-9549-113a17c50e14%5C%E5%BE%AE%E6%9C%8D%E5%8A%A1.jpg?Expires=1884074142&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=oAkPb1KQzwDbSVDwqhuObpo8yNQ%3D
         * loadList : [{"id":1,"name":"宜人贷","limitations":null,"introduction":"简介1","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T19:30:44.000+0800"},{"id":2,"name":"宜人贷","limitations":null,"introduction":"简介1","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T19:30:44.000+0800"},{"id":4,"name":"宜人贷","limitations":null,"introduction":"简介1","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T19:30:44.000+0800"}]
         */

        private String img;
        private List<LoadListBean> loadList;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<LoadListBean> getLoadList() {
            return loadList;
        }

        public void setLoadList(List<LoadListBean> loadList) {
            this.loadList = loadList;
        }

        public static class LoadListBean {
            /**
             * id : 1
             * name : 宜人贷
             * limitations : null
             * introduction : 简介1
             * status : 1
             * jumpUrl : www.baidu.com
             * createTime : 2019-09-29T19:30:44.000+0800
             */

            private int id;
            private String logo;
            private String name;
            private String limitations;
            private String introduction;
            private String status;
            private String jumpUrl;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLimitations() {
                return limitations;
            }

            public void setLimitations(String limitations) {
                this.limitations = limitations;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getJumpUrl() {
                return jumpUrl;
            }

            public void setJumpUrl(String jumpUrl) {
                this.jumpUrl = jumpUrl;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }
}
