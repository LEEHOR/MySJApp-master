package com.shenjing.dengyuejinfu.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.entity.QRBean;
import com.shenjing.dengyuejinfu.ui.contract.QRActivityContract;

import com.shenjing.dengyuejinfu.ui.fragmentDialog.ShareDialogFragment;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.shareListener;
import com.shenjing.dengyuejinfu.ui.presenter.QRActivityPresenter;
import com.shenjing.dengyuejinfu.utils.GlideUtils;
import com.shenjing.dengyuejinfu.utils.QRCodeUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/9/2014:47
 * version: 1.0
 * desc   :第三方分享
 */
@Route(path = ARouterUrl.QRShareActivityUrl)
public class QRShareActivity extends BaseActivity<QRActivityPresenter> implements QRActivityContract.View, shareListener {
    @Autowired(name = "shareTitle")
    String shareTitle;
    @Autowired(name = "shareUrl")
    String shareUrl;
    @Autowired(name = "thumbnail")
    String thumbnail;
    @Autowired(name = "describe")
    String describe;
    @BindView(R.id.QR_mStatusBar)
    View QRMStatusBar;
    @BindView(R.id.QR_titleBar)
    TitleBar QRTitleBar;
    @BindView(R.id.or_share_userName)
    TextView orShareUserName;
    @BindView(R.id.or_share_orImage)
    ImageView orShareOrImage;
    @BindView(R.id.QR_share)
    TextView QRShare;
    protected final String TAG = this.getClass().getSimpleName();
    private static final int MSG1=1;
    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG1:
                    GlideUtils.initImageByBitMap(QRShareActivity.this, (Bitmap) msg.obj, orShareOrImage);
                    break;
            }
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_share;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        initActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        setStatusBar(QRMStatusBar, R.color.blue1);
        setTittleBarBackground(QRTitleBar, R.color.blue1);
        setPageTitle(QRTitleBar, "第三方分享", R.color.white);
        setPageLeftText(QRTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {

        //mPresenter.getQRCode(BaseParams.userId);
        if (StringUtils.isSpace(shareTitle) && StringUtils.isSpace(shareUrl)){
            QRShare.setEnabled(false);
        } else {
            startMakeQr(shareUrl);
            QRShare.setEnabled(true);

        }
    }

    @OnClick(R.id.QR_share)
    public void onClick() {
        ShareDialogFragment shareDialogFragment = (ShareDialogFragment)ARouter.getInstance()
                .build(ARouterUrl.ShareDialogFragmentUrl)
                .withString(BaseParams.share_title,shareTitle)
                .withString(BaseParams.share_describe,describe)
                .withString(BaseParams.share_thumbnail,thumbnail)
                .withString(BaseParams.share_url,shareUrl)
                .navigation(this,new LoginNavigationCallback());
        shareDialogFragment.setShareListener(this);
        shareDialogFragment.show(getSupportFragmentManager(),TAG);
    }

    @Override
    public void getSuccess(QRBean qrBean) {
        if (qrBean.getData() != null) {
            if (qrBean.getData().getUrl() != null) {
                startMakeQr(qrBean.getData().getUrl());

            }
        }

    }

    @Override
    public void getFailure() {

    }

    @Override
    public void isCanShare(boolean isCanShare) {
        QRShare.setEnabled(isCanShare);
    }

    @Override
    public void shareSuccess(int sharePlatForm, int shareType) {

    }

    @Override
    public void shareFailure(int sharePlatForm, int shareType, String failedMessage) {

    }

    @Override
    public void shareCancel(int sharePlatForm, int shareType, String message) {

    }

    /**
     * 开始制作二维码
     */
    private void startMakeQr(String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap qrImage = QRCodeUtils.createQRImage(url, 300, 300);
                Message message=new Message();
                message.what=MSG1;
                message.obj=qrImage;
                mhandler.sendMessage(message);
            }
        }).start();
    }
}
