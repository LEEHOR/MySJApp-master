package com.shenjing.dengyuejinfu.respondModule;

/**
 * author : Leehor
 * date   : 2019/9/1819:24
 * version: 1.0
 * desc   :
 */
public class BankInfoModel {


    /**
     * msg : 请求成功
     * code : 0000
     * data : {"feasibility":"3","bank":"天地银行","phone_number":"18571512117","bank_card_no":"6217002020031489313","state":"9003","bank_card_img":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/bankCardImg%5CbankCardImg?Expires=1884235964&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=U1f%2FbIor3wPJwP%2BN5NOoKfhov%2FI%3D"}
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
         * feasibility : 3
         * bank : 天地银行
         * phone_number : 18571512117
         * bank_card_no : 6217002020031489313
         * state : 9003
         * bank_card_img : http://jmd-img.oss-cn-hangzhou.aliyuncs.com/bankCardImg%5CbankCardImg?Expires=1884235964&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=U1f%2FbIor3wPJwP%2BN5NOoKfhov%2FI%3D
         */

        private String feasibility;
        private String bank;
        private String phone_number;
        private String bank_card_no;
        private String state;
        private String bank_card_img;

        public String getFeasibility() {
            return feasibility;
        }

        public void setFeasibility(String feasibility) {
            this.feasibility = feasibility;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getBank_card_no() {
            return bank_card_no;
        }

        public void setBank_card_no(String bank_card_no) {
            this.bank_card_no = bank_card_no;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getBank_card_img() {
            return bank_card_img;
        }

        public void setBank_card_img(String bank_card_img) {
            this.bank_card_img = bank_card_img;
        }
    }
}
