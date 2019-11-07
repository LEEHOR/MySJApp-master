package com.shenjing.dengyuejinfu.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/11/710:06
 * version: 1.0
 * desc   :
 */
public class MyCustomerBean {


    /**
     * msg : 查询成功
     * code : 0000
     * data : {"2b":{"2d":[11],"2i":[65]},"1b":{"1d":[21,22],"1i":[23,25]},"5b":{"5i":[],"5d":[]},"4b":{"4d":[],"4i":[55]},"3b":{"3d":[],"3i":[]},"customer":[{"id":1,"levelType":"1","levelName":"普通用户","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"注册成为有效用户/n/r可申请信用卡,查看部分课程","userId":null,"createTime":null,"updateTime":null},{"id":2,"levelType":"2","levelName":"课程会员","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值356元(App充值)/n/r可用私人订制,查看高级教程","userId":null,"createTime":null,"updateTime":null},{"id":3,"levelType":"3","levelName":"银管家","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值940元(App充值)/n/r课程会员基础上10推广名额,收益率50%","userId":null,"createTime":null,"updateTime":null},{"id":4,"levelType":"4","levelName":"金管家","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值7520元(App充值)/n/r课程会员基础上100推广名额,收益率60%","userId":null,"createTime":null,"updateTime":null},{"id":5,"levelType":"5","levelName":"服务商","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值56400元(线下充值-联系客服)/n/r课程会员基础上1000推广名额,收益率70%","userId":null,"createTime":null,"updateTime":null}]}
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
         * 2b : {"2d":[11],"2i":[65]}
         * 1b : {"1d":[21,22],"1i":[23,25]}
         * 5b : {"5i":[],"5d":[]}
         * 4b : {"4d":[],"4i":[55]}
         * 3b : {"3d":[],"3i":[]}
         * customer : [{"id":1,"levelType":"1","levelName":"普通用户","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"注册成为有效用户/n/r可申请信用卡,查看部分课程","userId":null,"createTime":null,"updateTime":null},{"id":2,"levelType":"2","levelName":"课程会员","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值356元(App充值)/n/r可用私人订制,查看高级教程","userId":null,"createTime":null,"updateTime":null},{"id":3,"levelType":"3","levelName":"银管家","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值940元(App充值)/n/r课程会员基础上10推广名额,收益率50%","userId":null,"createTime":null,"updateTime":null},{"id":4,"levelType":"4","levelName":"金管家","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值7520元(App充值)/n/r课程会员基础上100推广名额,收益率60%","userId":null,"createTime":null,"updateTime":null},{"id":5,"levelType":"5","levelName":"服务商","logo":null,"pirce":null,"rate":null,"number":null,"buyType":null,"description":"充值56400元(线下充值-联系客服)/n/r课程会员基础上1000推广名额,收益率70%","userId":null,"createTime":null,"updateTime":null}]
         */

        @SerializedName("2b")
        private _$2bBean _$2b;
        @SerializedName("1b")
        private _$1bBean _$1b;
        @SerializedName("5b")
        private _$5bBean _$5b;
        @SerializedName("4b")
        private _$4bBean _$4b;
        @SerializedName("3b")
        private _$3bBean _$3b;
        private List<CustomerBean> customer;

        public _$2bBean get_$2b() {
            return _$2b;
        }

        public void set_$2b(_$2bBean _$2b) {
            this._$2b = _$2b;
        }

        public _$1bBean get_$1b() {
            return _$1b;
        }

        public void set_$1b(_$1bBean _$1b) {
            this._$1b = _$1b;
        }

        public _$5bBean get_$5b() {
            return _$5b;
        }

        public void set_$5b(_$5bBean _$5b) {
            this._$5b = _$5b;
        }

        public _$4bBean get_$4b() {
            return _$4b;
        }

        public void set_$4b(_$4bBean _$4b) {
            this._$4b = _$4b;
        }

        public _$3bBean get_$3b() {
            return _$3b;
        }

        public void set_$3b(_$3bBean _$3b) {
            this._$3b = _$3b;
        }

        public List<CustomerBean> getCustomer() {
            return customer;
        }

        public void setCustomer(List<CustomerBean> customer) {
            this.customer = customer;
        }

        public static class _$2bBean {
            @SerializedName("2d")
            private List<Integer> _$2d;
            @SerializedName("2i")
            private List<Integer> _$2i;

            public List<Integer> get_$2d() {
                return _$2d;
            }

            public void set_$2d(List<Integer> _$2d) {
                this._$2d = _$2d;
            }

            public List<Integer> get_$2i() {
                return _$2i;
            }

            public void set_$2i(List<Integer> _$2i) {
                this._$2i = _$2i;
            }
        }

        public static class _$1bBean {
            @SerializedName("1d")
            private List<Integer> _$1d;
            @SerializedName("1i")
            private List<Integer> _$1i;

            public List<Integer> get_$1d() {
                return _$1d;
            }

            public void set_$1d(List<Integer> _$1d) {
                this._$1d = _$1d;
            }

            public List<Integer> get_$1i() {
                return _$1i;
            }

            public void set_$1i(List<Integer> _$1i) {
                this._$1i = _$1i;
            }
        }

        public static class _$5bBean {
            @SerializedName("5i")
            private List<Integer> _$5i;
            @SerializedName("5d")
            private List<Integer> _$5d;

            public List<Integer> get_$5i() {
                return _$5i;
            }

            public void set_$5i(List<Integer> _$5i) {
                this._$5i = _$5i;
            }

            public List<Integer> get_$5d() {
                return _$5d;
            }

            public void set_$5d(List<Integer> _$5d) {
                this._$5d = _$5d;
            }
        }

        public static class _$4bBean {
            @SerializedName("4d")
            private List<Integer> _$4d;
            @SerializedName("4i")
            private List<Integer> _$4i;

            public List<Integer> get_$4d() {
                return _$4d;
            }

            public void set_$4d(List<Integer> _$4d) {
                this._$4d = _$4d;
            }

            public List<Integer> get_$4i() {
                return _$4i;
            }

            public void set_$4i(List<Integer> _$4i) {
                this._$4i = _$4i;
            }
        }

        public static class _$3bBean {
            @SerializedName("3d")
            private List<Integer> _$3d;
            @SerializedName("3i")
            private List<Integer> _$3i;

            public List<Integer> get_$3d() {
                return _$3d;
            }

            public void set_$3d(List<Integer> _$3d) {
                this._$3d = _$3d;
            }

            public List<Integer> get_$3i() {
                return _$3i;
            }

            public void set_$3i(List<Integer> _$3i) {
                this._$3i = _$3i;
            }
        }

        public static class CustomerBean {
            /**
             * id : 1
             * levelType : 1
             * levelName : 普通用户
             * logo : null
             * pirce : null
             * rate : null
             * number : null
             * buyType : null
             * description : 注册成为有效用户/n/r可申请信用卡,查看部分课程
             * userId : null
             * createTime : null
             * updateTime : null
             */

            private int id;
            private String levelType;
            private String levelName;
            private Object logo;
            private Object pirce;
            private Object rate;
            private Object number;
            private Object buyType;
            private String description;
            private Object userId;
            private Object createTime;
            private Object updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLevelType() {
                return levelType;
            }

            public void setLevelType(String levelType) {
                this.levelType = levelType;
            }

            public String getLevelName() {
                return levelName;
            }

            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }

            public Object getLogo() {
                return logo;
            }

            public void setLogo(Object logo) {
                this.logo = logo;
            }

            public Object getPirce() {
                return pirce;
            }

            public void setPirce(Object pirce) {
                this.pirce = pirce;
            }

            public Object getRate() {
                return rate;
            }

            public void setRate(Object rate) {
                this.rate = rate;
            }

            public Object getNumber() {
                return number;
            }

            public void setNumber(Object number) {
                this.number = number;
            }

            public Object getBuyType() {
                return buyType;
            }

            public void setBuyType(Object buyType) {
                this.buyType = buyType;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
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
