package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.contract.PaymentVerificationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.PaymentVerificationActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1418:53
 * version: 1.0
 * desc   :安全支付认证
 */
@Route(path = ARouterUrl.PaymentVerificationActivityUrl)
public class PaymentVerificationActivity extends BaseActivity<PaymentVerificationActivityPresenter>
        implements PaymentVerificationActivityContract.View {
    @BindView(R.id.payment_verification_mStatusBar)
    View paymentVerificationMStatusBar;
    @BindView(R.id.payment_verification_titleBar)
    TitleBar paymentVerificationTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security_payment_verificatio;
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
        setStatusBar(paymentVerificationMStatusBar, R.color.blue1);
        setTittleBarBackground(paymentVerificationTitleBar,R.color.blue1);
        setPageTitle(paymentVerificationTitleBar,"安全支付认证",R.color.white);
        setPageLeftText(paymentVerificationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {

    }
}
