package com.shenjing.dengyuejinfu.entity;

import java.io.Serializable;

/**
 * author : Leehor
 * date   : 2019/9/1819:24
 * version: 1.0
 * desc   :
 */
public class BankOcrInfoBean implements Serializable {

    /**
     * partner_order_id : andr_1545967493371
     * bank_card_photo : http://10.1.30.51:8080/idsafe-front/front/1.0/......
     * bank_name : 杭州商业银行
     * bank_card_no : 1234567890122222
     * card_type : 借记卡
     * org_code : 1234568
     * sdk_bank_card_photo : Bitmap
     * success : true
     * message : 操作成功
     */

    private String partner_order_id;
    private String bank_card_photo;
    private String bank_name;
    private String bank_card_no;
    private String card_type;
    private String org_code;
    private String sdk_bank_card_photo;
    private String success;
    private String message;

    public String getPartner_order_id() {
        return partner_order_id;
    }

    public void setPartner_order_id(String partner_order_id) {
        this.partner_order_id = partner_order_id;
    }

    public String getBank_card_photo() {
        return bank_card_photo;
    }

    public void setBank_card_photo(String bank_card_photo) {
        this.bank_card_photo = bank_card_photo;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card_no() {
        return bank_card_no;
    }

    public void setBank_card_no(String bank_card_no) {
        this.bank_card_no = bank_card_no;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getSdk_bank_card_photo() {
        return sdk_bank_card_photo;
    }

    public void setSdk_bank_card_photo(String sdk_bank_card_photo) {
        this.sdk_bank_card_photo = sdk_bank_card_photo;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
