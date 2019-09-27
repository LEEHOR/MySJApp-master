package com.leehor.simple.lightPay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.leehor.simple.lightPay.entity.PayResult;
import com.leehor.simple.lightPay.entity.WxBean;
import com.leehor.simple.Config;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/9/2610:11
 * version: 1.0
 * desc   :支付类
 */
public class PayHelper {
    private static PayHelper mPayHelper = null;
    private IPayListener mIPayListener;
    public static PayHelper getInstance() {
        if (mPayHelper == null) {
            mPayHelper = new PayHelper();
        }
        return mPayHelper;
    }

    public PayHelper() {

    }


    IWXAPI msgApi = null;

    /**
     * 微信支付
     * @param data
     * @param context
     */
    public void WexPay(WxBean.ReturnDataBean data, Context context) {
        if (msgApi == null) {
            msgApi = WXAPIFactory.createWXAPI(context, null);
            msgApi.registerApp(Config.WECHAT_APP_ID);// 将该app注册到微信
        }
        PayReq req = new PayReq();
        if (!msgApi.isWXAppInstalled()) {
            return;
        }
        if (data != null) {

            req.appId = data.getAppid();
            req.partnerId = data.getPartnerid();
            req.prepayId = data.getPrepayid();
            req.nonceStr = data.getNoncestr();
            req.timeStamp = data.getTimestamp() + "";
            req.packageValue = data.getPackageX();
            req.sign = data.getSign();
            // req.extData = MaiLiApplication.getInstance().getUserInfo().getPhone();  微信拓展参数
            msgApi.sendReq(req);
        }
    }

    /**
     * 支付宝支付
     * @param activity
     * @param orderInfo
     */
    public void AliPay(Activity activity, final String orderInfo) {
        final PayTask alipay = new PayTask(activity);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        if (mIPayListener != null) {
                            mIPayListener.onSuccess();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        if (mIPayListener != null) {
                            mIPayListener.onFail();
                        }
                    }
                    break;
            }
        }
    };


    public void setIPayListener(IPayListener iPayListener) {
        this.mIPayListener = iPayListener;
    }

}
