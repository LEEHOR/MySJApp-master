package com.leehor.simple.wxapi;

/**
 * author : Leehor
 * date   : 2019/9/2611:41
 * version: 1.0
 * desc   :
 */
interface WxPayListener {
    void WxPaySuccess(int code,String des,String errStr);
    void WxPayFailure(int code,String des,String errStr);
    void WxPayCancel(int code,String des,String errStr);
}
