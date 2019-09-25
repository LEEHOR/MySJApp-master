package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/1715:34
 * version: 1.0
 * desc   :
 */
public class BannerBean implements Serializable {


    /**
     * msg : 首页信息获取成功
     * code : 0000
     * data : {"switchs":"1","banner":[{"path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//7e861c73-e7e4-44df-9549-113a17c50e14%5C%E5%BE%AE%E6%9C%8D%E5%8A%A1.jpg?Expires=1884074142&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=oAkPb1KQzwDbSVDwqhuObpo8yNQ%3D","id":6,"title":"阿萨德阿萨德3","url":"https://www.cnblogs.com/zuidongfeng/p/9926471.html"},{"path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//1e737eee-b950-4a33-96b6-ae79e8fcde8b%5C%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96.jpg?Expires=1884074136&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=6tdrCDt9Qqafodd1pEA9%2Fo1WAZ0%3D","id":5,"title":"阿萨德阿萨德2","url":"https://www.cnblogs.com/zuidongfeng/p/9926471.html"},{"path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//2db7180b-c12d-4ba2-b304-e627b0eec0b6%5C%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.jpg?Expires=1884074124&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=DH0IiBZGWMMSUrkClN01ncPQ8JA%3D","id":4,"title":"阿萨德阿萨德1","url":"https://www.cnblogs.com/zuidongfeng/p/9926471.html"}],"marquee":[{"id":3,"title":"marquee3","content":"marquee3"},{"id":2,"title":"marquee2","content":"marquee2"},{"id":1,"title":"marquee1","content":"marquee1"}]}
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
         * switchs : 1
         * banner : [{"path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//7e861c73-e7e4-44df-9549-113a17c50e14%5C%E5%BE%AE%E6%9C%8D%E5%8A%A1.jpg?Expires=1884074142&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=oAkPb1KQzwDbSVDwqhuObpo8yNQ%3D","id":6,"title":"阿萨德阿萨德3","url":"https://www.cnblogs.com/zuidongfeng/p/9926471.html"},{"path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//1e737eee-b950-4a33-96b6-ae79e8fcde8b%5C%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96.jpg?Expires=1884074136&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=6tdrCDt9Qqafodd1pEA9%2Fo1WAZ0%3D","id":5,"title":"阿萨德阿萨德2","url":"https://www.cnblogs.com/zuidongfeng/p/9926471.html"},{"path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//2db7180b-c12d-4ba2-b304-e627b0eec0b6%5C%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.jpg?Expires=1884074124&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=DH0IiBZGWMMSUrkClN01ncPQ8JA%3D","id":4,"title":"阿萨德阿萨德1","url":"https://www.cnblogs.com/zuidongfeng/p/9926471.html"}]
         * marquee : [{"id":3,"title":"marquee3","content":"marquee3"},{"id":2,"title":"marquee2","content":"marquee2"},{"id":1,"title":"marquee1","content":"marquee1"}]
         */

        private String switchs;
        private List<Banner> banner;
        private List<MarqueeBean> marquee;

        public String getSwitchs() {
            return switchs;
        }

        public void setSwitchs(String switchs) {
            this.switchs = switchs;
        }

        public List<Banner> getBanner() {
            return banner;
        }

        public void setBanner(List<Banner> banner) {
            this.banner = banner;
        }

        public List<MarqueeBean> getMarquee() {
            return marquee;
        }

        public void setMarquee(List<MarqueeBean> marquee) {
            this.marquee = marquee;
        }

        public class  Banner {
            /**
             * path : http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//7e861c73-e7e4-44df-9549-113a17c50e14%5C%E5%BE%AE%E6%9C%8D%E5%8A%A1.jpg?Expires=1884074142&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=oAkPb1KQzwDbSVDwqhuObpo8yNQ%3D
             * id : 6
             * title : 阿萨德阿萨德3
             * url : https://www.cnblogs.com/zuidongfeng/p/9926471.html
             */

            private String path;
            private int id;
            private String title;
            private String url;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class MarqueeBean {
            /**
             * id : 3
             * title : marquee3
             * content : marquee3
             */

            private int id;
            private String title;
            private String content;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
