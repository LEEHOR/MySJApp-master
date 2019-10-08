package com.shenjing.dengyuejinfu.entity;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/3011:58
 * version: 1.0
 * desc   :
 */
public class CardListBean {

    /**
     * msg : 获取成功
     * code : 0000
     * data : {"cardList":[{"id":1,"bankId":2,"cardImg":"","cardName":"卡名字","introduction":"简介1","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T18:10:06.000+0800"},{"id":2,"bankId":2,"cardImg":"","cardName":"卡名字","introduction":"简介2","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T18:10:06.000+0800"},{"id":3,"bankId":2,"cardImg":"","cardName":"卡名字","introduction":"简介3","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T18:10:06.000+0800"},{"id":4,"bankId":2,"cardImg":"","cardName":"卡名字","introduction":"简介4","status":"1","jumpUrl":"www.baidu.com","createTime":"2019-09-29T18:10:06.000+0800"}]}
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
        private List<CardList> cardList;

        public List<CardList> getCardList() {
            return cardList;
        }

        public void setCardList(List<CardList> cardList) {
            this.cardList = cardList;
        }

        public static class CardList {
            /**
             * id : 1
             * bankId : 2
             * cardImg :
             * cardName : 卡名字
             * introduction : 简介1
             * status : 1
             * jumpUrl : www.baidu.com
             * createTime : 2019-09-29T18:10:06.000+0800
             */

            private int id;
            private int bankId;
            private String cardImg;
            private String cardName;
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

            public int getBankId() {
                return bankId;
            }

            public void setBankId(int bankId) {
                this.bankId = bankId;
            }

            public String getCardImg() {
                return cardImg;
            }

            public void setCardImg(String cardImg) {
                this.cardImg = cardImg;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
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
        }
    }
}
