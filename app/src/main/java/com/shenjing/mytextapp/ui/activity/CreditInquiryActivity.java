package com.shenjing.mytextapp.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.CreditInquiryActivityContract;
import com.shenjing.mytextapp.ui.presenter.CreditInquiryActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/199:49
 * version: 1.0
 * desc   :  信用卡测评
 */
@Route(path = ARouterUrl.CreditInquiryActivityUrl)
public class CreditInquiryActivity extends BaseActivity<CreditInquiryActivityPresenter>
        implements CreditInquiryActivityContract.View {

    @BindView(R.id.credit_inquiry_mStatusBar)
    View creditInquiryMStatusBar;
    @BindView(R.id.credit_inquiry_titleBar)
    TitleBar creditInquiryTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_inquiry;
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
        setStatusBar(creditInquiryMStatusBar, R.color.blue1);
        setTittleBarBackground(creditInquiryTitleBar, R.color.blue1);
        setPageTitle(creditInquiryTitleBar, "征信查询", R.color.white);
        setPageLeftText(creditInquiryTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
        setTitleRight(creditInquiryTitleBar, "历史记录", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                ARouter.getInstance().build(ARouterUrl.CreditInquiryRecordActivityUrl).navigation();
            }
        });
    }

    @Override
    protected void initFunc() {

    }
}
