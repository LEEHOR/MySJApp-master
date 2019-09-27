package com.leehor.simple.wxapi;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.leehor.simple.Config;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * author : Leehor
 * date   : 2019/9/2611:00
 * version: 1.0
 * desc   :微信支付回调
 */
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI wxApi;
    private WxPayListener wxPayListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxApi = WXAPIFactory.createWXAPI(this, Config.WECHAT_APP_ID);
        wxApi.handleIntent(getIntent(), this); //处理微信传回的Intent,当然你也可以在别的地方处理
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case 0:
                wxPayListener.WxPaySuccess(baseResp.errCode,baseResp.transaction,baseResp.errStr);
                break;
            case -1:
                wxPayListener.WxPayFailure(baseResp.errCode,baseResp.transaction,baseResp.errStr);
                break;
            case -2:
                wxPayListener.WxPayCancel(baseResp.errCode,baseResp.transaction,baseResp.errStr);
                break;
        }
        finish();
    }
}
