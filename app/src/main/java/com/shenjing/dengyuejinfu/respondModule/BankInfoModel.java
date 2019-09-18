package com.shenjing.dengyuejinfu.respondModule;

/**
 * author : Leehor
 * date   : 2019/9/1819:24
 * version: 1.0
 * desc   :
 */
public class BankInfoModel {

    /**
     * msg : 该用户未认证
     * code : 0000
     * data : null
     */

    private String msg;
    private String code;
    private DataBean data;
    private String feasibility;
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

    public String getFeasibility() {
        return feasibility;
    }

    public void setFeasibility(String feasibility) {
        this.feasibility = feasibility;
    }

    public static class DataBean {
        private String bank_card_no;
        private String bank_card_img;
        private String phone_number;
        private String bank;


        public String getBank_card_no() {
            return bank_card_no;
        }

        public void setBank_card_no(String bank_card_no) {
            this.bank_card_no = bank_card_no;
        }

        public String getBank_card_img() {
            return bank_card_img;
        }

        public void setBank_card_img(String bank_card_img) {
            this.bank_card_img = bank_card_img;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

    }
}
