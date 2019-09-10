package com.leehor.lightshare.wxapi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.leehor.lightshare.Config;
import com.leehor.lightshare.share.ShareKeeper;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI wxApi = WXAPIFactory.createWXAPI(this, Config.WECHAT_APP_ID);
        wxApi.handleIntent(getIntent(), this); //处理微信传回的Intent,当然你也可以在别的地方处理
    }

    @Override
    public void onResp(BaseResp resp) { //在这个方法中处理微信传回的数据
        ShareKeeper.getInstance().performWechatShareResult(resp);

        Log.e("TAG", "微信分享："+resp.errCode);
    }

    @Override
    public void onReq(BaseReq req) {
        //......这里是用来处理接收的请求,暂不做讨论
    }
}