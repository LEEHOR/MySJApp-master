package com.shenjing.dengyuejinfu.respondModule;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/1715:34
 * version: 1.0
 * desc   :
 */
public class BannerModel {


    /**
     * msg : 首页信息获取成功
     * code : 0000
     * data : {"banner":[{"id":1,"picUuid":"1","title":"标题","url":"www.baidu.com","path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5C%E5%B7%A5%E7%A8%8B%E5%8D%8F%E4%BD%9C.jpg?Expires=1884065615&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=Y1pA249bftyzHp6badED8bt9u6g%3D","uploadUserId":null,"creatTime":"2019-09-17 16:26:00","soldOutTime":"2019-09-17 16:26:02"},{"id":2,"picUuid":"2","title":"标题","url":"www.baidu.com","path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5C%E5%B7%A5%E7%A8%8B%E5%8D%8F%E4%BD%9C.jpg?Expires=1884065615&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=Y1pA249bftyzHp6badED8bt9u6g%3D","uploadUserId":null,"creatTime":"2019-09-17 16:26:00","soldOutTime":"2019-09-17 16:26:02"},{"id":3,"picUuid":"3","title":"标题","url":"www.baidu.com","path":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5C%E5%B7%A5%E7%A8%8B%E5%8D%8F%E4%BD%9C.jpg?Expires=1884065615&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=Y1pA249bftyzHp6badED8bt9u6g%3D","uploadUserId":null,"creatTime":"2019-09-17 16:26:00","soldOutTime":"2019-09-17 16:26:02"}],"marquee":["marquee1","marquee2","marquee3"]}
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
        private List<BannerBean> banner;
        private List<String> marquee;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<String> getMarquee() {
            return marquee;
        }

        public void setMarquee(List<String> marquee) {
            this.marquee = marquee;
        }

        public static class BannerBean {
            /**
             * title : 标题
             * url : www.baidu.com
             * path : http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//18571512117%5C%E5%B7%A5%E7%A8%8B%E5%8D%8F%E4%BD%9C.jpg?Expires=1884065615&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=Y1pA249bftyzHp6badED8bt9u6g%3D
             */

            private String title;
            private String url;
            private String path;

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

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }
}
