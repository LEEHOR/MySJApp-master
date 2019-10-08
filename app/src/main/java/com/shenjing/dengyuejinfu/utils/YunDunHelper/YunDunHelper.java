package com.shenjing.dengyuejinfu.utils.YunDunHelper;

import android.content.Context;
import android.util.Log;

import com.alibaba.security.rp.RPSDK;
import com.blankj.utilcode.util.LogUtils;

//import com.alibaba.security.rp.RPSDK;

/**
 * author : Leehor
 * date   : 2019/9/2917:34
 * version: 1.0
 * desc   :阿里云实名认证
 */
public class YunDunHelper {
    private static YunDunHelper yunDunHelper = null;
    private YunDunListener yunDunListener;

    public static YunDunHelper getInstance() {
        if (yunDunHelper == null) {
            yunDunHelper = new YunDunHelper();
        }
        return yunDunHelper;
    }

    public YunDunHelper() {

    }

    /**
     * 开始认证
     *
     * @param token
     * @param context
     */
    public void startAliOcr(String token, Context context) {
        RPSDK.start(token, context, new RPSDK.RPCompletedListener() {
            @Override
            public void onAuditResult(RPSDK.AUDIT audit, String s) {
                LogUtils.d("识别", audit, "code", s);
                if (audit == RPSDK.AUDIT.AUDIT_PASS) { //认证通过
                    if (yunDunListener != null) {
                        LogUtils.d("识别2", audit, "code", s);
                        yunDunListener.YunDunSuccess();
                    }


                } else if (audit == RPSDK.AUDIT.AUDIT_FAIL) { //认证不通过
                    if (yunDunListener != null) {
                        LogUtils.d("识别3", audit, "code", s);
                        yunDunListener.YunDunFailure();
                    }


                } else if (audit == RPSDK.AUDIT.AUDIT_NOT) { //未认证，具体原因可通过code来区分（code取值参见下方表格），通常是用户主动退出或者姓名身份证号实名校验不匹配等原因，导致未完成认证流程
                    if (yunDunListener != null) {
                        LogUtils.d("识别4", audit, "code", s);
                        yunDunListener.YunDunNot();
                    }


                } else {
                    if (yunDunListener != null) {
                        LogUtils.d("识别5", audit, "code", s);
                        yunDunListener.YunDunFailure();
                    }
                }
            }
        });
    }

    public void setYunDunListener(YunDunListener yunDunListener) {
        this.yunDunListener = yunDunListener;
    }
}
