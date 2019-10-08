package com.shenjing.dengyuejinfu.entity;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/3010:22
 * version: 1.0
 * desc   :  信用卡申请--银行列表
 */
public class BankListBean {

    /**
     * msg : 获取成功
     * code : 0000
     * data : {"img":"http://jmd-img.oss-cn-hangzhou.aliyuncs.com/repayment//7e861c73-e7e4-44df-9549-113a17c50e14%5C%E5%BE%AE%E6%9C%8D%E5%8A%A1.jpg?Expires=1884074142&OSSAccessKeyId=LTAI2jejMitchJsw&Signature=oAkPb1KQzwDbSVDwqhuObpo8yNQ%3D","bankList":[{"id":2,"bank":"建设银行","table1":"table1","table2":"table2","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null},{"id":3,"bank":"平安银行","table1":"","table2":"","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null},{"id":4,"bank":"工商银行","table1":"table1","table2":"table2","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null},{"id":5,"bank":"邮政银行","table1":"table1","table2":"table2","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null}]}
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
         * bankList : [{"id":2,"bank":"建设银行","table1":"table1","table2":"table2","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null},{"id":3,"bank":"平安银行","table1":"","table2":"","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null},{"id":4,"bank":"工商银行","table1":"table1","table2":"table2","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null},{"id":5,"bank":"邮政银行","table1":"table1","table2":"table2","introduction":"简介","logo":"","status":"1","createTime":"2019-09-29T17:33:44.000+0800","bankCredit":null,"cardIssuanceTime":null,"applications":null}]
         */

        private String img;
        private List<BankListBeans> bankList;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<BankListBeans> getBankList() {
            return bankList;
        }

        public void setBankList(List<BankListBeans> bankList) {
            this.bankList = bankList;
        }

        public static class BankListBeans {
            /**
             * id : 2
             * bank : 建设银行
             * table1 : table1
             * table2 : table2
             * introduction : 简介
             * logo :
             * status : 1
             * createTime : 2019-09-29T17:33:44.000+0800
             * bankCredit : null
             * cardIssuanceTime : null
             * applications : null
             */

            private int id;
            private String bank;
            private String table1;
            private String table2;
            private String introduction;
            private String logo;
            private String status;
            private String createTime;
            private Object bankCredit;
            private Object cardIssuanceTime;
            private Object applications;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getTable1() {
                return table1;
            }

            public void setTable1(String table1) {
                this.table1 = table1;
            }

            public String getTable2() {
                return table2;
            }

            public void setTable2(String table2) {
                this.table2 = table2;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getBankCredit() {
                return bankCredit;
            }

            public void setBankCredit(Object bankCredit) {
                this.bankCredit = bankCredit;
            }

            public Object getCardIssuanceTime() {
                return cardIssuanceTime;
            }

            public void setCardIssuanceTime(Object cardIssuanceTime) {
                this.cardIssuanceTime = cardIssuanceTime;
            }

            public Object getApplications() {
                return applications;
            }

            public void setApplications(Object applications) {
                this.applications = applications;
            }
        }
    }
}
