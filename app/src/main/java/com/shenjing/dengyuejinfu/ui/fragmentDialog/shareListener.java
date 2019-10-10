package com.shenjing.dengyuejinfu.ui.fragmentDialog;

/**
 * author : Leehor
 * date   : 2019/10/916:20
 * version: 1.0
 * desc   :
 */
public interface shareListener {
    void shareSuccess(int sharePlatForm, int shareType);

    void shareFailure(int sharePlatForm, int shareType, String failedMessage);

    void shareCancel(int sharePlatForm, int shareType, String message);
}
