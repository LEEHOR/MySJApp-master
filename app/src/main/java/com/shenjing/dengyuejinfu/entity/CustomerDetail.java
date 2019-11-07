package com.shenjing.dengyuejinfu.entity;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/11/714:17
 * version: 1.0
 * desc   :
 */
public class CustomerDetail {

    /**
     * msg : 查询成功
     * code : 0000
     * data : [{"id":21,"inviteCode":"1c532edf-2a53-71c1-7879-a638686888bf","password":"asdd","phoneNumber":"12345678910","realName":"aa","lastPassword":"as","autonymState":"1","invitedId":12,"facilityCode":"wza","userLevel":"1"}]
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
         * id : 21
         * inviteCode : 1c532edf-2a53-71c1-7879-a638686888bf
         * password : asdd
         * phoneNumber : 12345678910
         * realName : aa
         * lastPassword : as
         * autonymState : 1
         * invitedId : 12
         * facilityCode : wza
         * userLevel : 1
         */

        private int id;
        private String inviteCode;
        private String password;
        private String phoneNumber;
        private String realName;
        private String lastPassword;
        private String autonymState;
        private int invitedId;
        private String facilityCode;
        private String userLevel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getLastPassword() {
            return lastPassword;
        }

        public void setLastPassword(String lastPassword) {
            this.lastPassword = lastPassword;
        }

        public String getAutonymState() {
            return autonymState;
        }

        public void setAutonymState(String autonymState) {
            this.autonymState = autonymState;
        }

        public int getInvitedId() {
            return invitedId;
        }

        public void setInvitedId(int invitedId) {
            this.invitedId = invitedId;
        }

        public String getFacilityCode() {
            return facilityCode;
        }

        public void setFacilityCode(String facilityCode) {
            this.facilityCode = facilityCode;
        }

        public String getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(String userLevel) {
            this.userLevel = userLevel;
        }
    }
}
