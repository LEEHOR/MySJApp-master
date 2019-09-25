package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/1918:45
 * version: 1.0
 * desc   :
 */
public class CreditCardListBean implements Serializable {

    /**
     * msg : 获取成功
     * code : 0000
     * data : {"creditCard":[{"id":4,"userId":12,"name":"李浩","idNo":"341226199306151536","creditCardNo":"6217082020831489313","bankPhone":"18571512117","bank":"","cvvn2":"789","validityPeriod":"1221","createTime":"2019-09-19T18:33:56.000+0800","status":"0","updateTime":null},{"id":5,"userId":12,"name":"李浩","idNo":"341226199306151536","creditCardNo":"6217002020031489313","bankPhone":"18571512117","bank":"","cvvn2":"789","validityPeriod":"1221","createTime":"2019-09-20T10:06:05.000+0800","status":"1","updateTime":null}]}
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
        private List<CreditCardBean> creditCard;

        public List<CreditCardBean> getCreditCard() {
            return creditCard;
        }

        public void setCreditCard(List<CreditCardBean> creditCard) {
            this.creditCard = creditCard;
        }

        public static class CreditCardBean {
            /**
             * id : 4
             * userId : 12
             * name : 李浩
             * idNo : 341226199306151536
             * creditCardNo : 6217082020831489313
             * bankPhone : 18571512117
             * bank :
             * cvvn2 : 789
             * validityPeriod : 1221
             * createTime : 2019-09-19T18:33:56.000+0800
             * status : 0
             * updateTime : null
             */

            private int id;
            private int userId;
            private String name;
            private String idNo;
            private String creditCardNo;
            private String bankPhone;
            private String bank;
            private String cvvn2;
            private String validityPeriod;
            private String createTime;
            private String status;
            private Object updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getCreditCardNo() {
                return creditCardNo;
            }

            public void setCreditCardNo(String creditCardNo) {
                this.creditCardNo = creditCardNo;
            }

            public String getBankPhone() {
                return bankPhone;
            }

            public void setBankPhone(String bankPhone) {
                this.bankPhone = bankPhone;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getCvvn2() {
                return cvvn2;
            }

            public void setCvvn2(String cvvn2) {
                this.cvvn2 = cvvn2;
            }

            public String getValidityPeriod() {
                return validityPeriod;
            }

            public void setValidityPeriod(String validityPeriod) {
                this.validityPeriod = validityPeriod;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
