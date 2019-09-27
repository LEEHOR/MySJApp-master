package com.leehor.simple.lightPay.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/2610:44
 * version: 1.0
 * desc   : 微信bean
 */
public class WxBean  implements Serializable {


    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : 服务器返回的josn数据
     */

    private String code;
    private String msg;
    private ReturnDataBean ReturnData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static class ReturnDataBean {
        /**
         * appid : //对应的参数后台返回
         * partnerid :
         * prepayid :
         * package : Sign=WXPay
         * noncestr :
         * timestamp : 1521789303
         * sign :
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
