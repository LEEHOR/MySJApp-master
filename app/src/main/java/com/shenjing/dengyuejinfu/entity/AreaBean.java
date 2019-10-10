package com.shenjing.dengyuejinfu.entity;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/10/913:58
 * version: 1.0
 * desc   :
 */
public class AreaBean {
    private List<Area> areaList;

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public static class  Area{

        /**
         * code : 120101
         * name : 和平区
         */

        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
