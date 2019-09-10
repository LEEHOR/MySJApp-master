package com.shenjing.mytextapp.entity;

/**
 * author : Leehor
 * date   : 2019/8/1315:48
 * version: 1.0
 * desc   :
 */
public class ReceiptBean {
    private int type;
    private String title;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ReceiptBean() {
    }

    public ReceiptBean(int type, String title) {
        this.type = type;
        this.title = title;
    }
}
