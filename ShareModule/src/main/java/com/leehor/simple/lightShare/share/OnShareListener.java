package com.leehor.simple.lightShare.share;


/**
 * @author gexinyu
 */
public interface OnShareListener {

    //分享成功
    void onShareSuccess(@ShareKeeper.Platform int sharePlatForm, @ShareKeeper.ShareType int shareType);

    //分享失败 之后加一些信息message
    void onShareFailed(@ShareKeeper.Platform int sharePlatForm, @ShareKeeper.ShareType int shareType, String failedMessage);

    //取消分享
    void onCancelShare(@ShareKeeper.Platform int sharePlatForm, @ShareKeeper.ShareType int shareType, String message);

}
