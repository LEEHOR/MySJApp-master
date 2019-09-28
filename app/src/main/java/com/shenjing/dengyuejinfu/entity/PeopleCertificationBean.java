package com.shenjing.dengyuejinfu.entity;
import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1817:55
 * version: 1.0
 * desc   :身份证认证请求参数类
 */
public class PeopleCertificationBean implements Serializable {
    /**
     * address :"浙江省杭州市滨江区越达巷"  身份证住址
     * age : 27  年龄
     * birthday : 1990.04.12  生日
     * id_name : 周伯通     姓名
     * id_number : 320421199011121234  身份证号码
     * gender: 男  性别
     * nation：汉  民族
     * idcard_back_photo :  身份证人面面路径
     * idcard_front_photo :  身份证国徽面路径
     * idcard_portrait_photo :   身份证头像照
     * issuing_authority : 滨江公安局   签发机关
     * validity_period : 2017.02.03-2037.02.03  有效期限
     * validity_period_expired : 0  是否过期 0-证件未过期 1-证件已过期
     * classify : 2  五分类检测 0：复印件 1：PS证件 2：正常证件 3：屏幕翻拍 4：临时身份证 5：其他
     * score : 0.9104828   五分类检测置信度   值在0-1之间
     * living_photo:活体清晰照保存路径
     * coordinate：  经纬度（经度，纬度）
     * locationAddress: 定位地址
     */
    private String address;
    private String age;
    private String birthday;
    private String id_name;
    private String id_number;
    private String gender;
    private String nation;
    private String idcard_back_photo;
    private String idcard_front_photo;
    private String idcard_portrait_photo;
    private String issuing_authority;
    private String validity_period;
    private String validity_period_expired;
    private String classify;
    private String score;
    private String living_photo;
    private String coordinate;
    private String locationAddress;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getIdcard_back_photo() {
        return idcard_back_photo;
    }

    public void setIdcard_back_photo(String idcard_back_photo) {
        this.idcard_back_photo = idcard_back_photo;
    }

    public String getIdcard_front_photo() {
        return idcard_front_photo;
    }

    public void setIdcard_front_photo(String idcard_front_photo) {
        this.idcard_front_photo = idcard_front_photo;
    }

    public String getIdcard_portrait_photo() {
        return idcard_portrait_photo;
    }

    public void setIdcard_portrait_photo(String idcard_portrait_photo) {
        this.idcard_portrait_photo = idcard_portrait_photo;
    }

    public String getIssuing_authority() {
        return issuing_authority;
    }

    public void setIssuing_authority(String issuing_authority) {
        this.issuing_authority = issuing_authority;
    }

    public String getValidity_period() {
        return validity_period;
    }

    public void setValidity_period(String validity_period) {
        this.validity_period = validity_period;
    }

    public String getValidity_period_expired() {
        return validity_period_expired;
    }

    public void setValidity_period_expired(String validity_period_expired) {
        this.validity_period_expired = validity_period_expired;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLiving_photo() {
        return living_photo;
    }

    public void setLiving_photo(String living_photo) {
        this.living_photo = living_photo;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }
}
