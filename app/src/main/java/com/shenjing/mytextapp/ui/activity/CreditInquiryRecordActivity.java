package com.shenjing.mytextapp.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.CreditInquiryRecordActivityContract;
import com.shenjing.mytextapp.ui.presenter.CreditInquiryRecordActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1910:16
 * version: 1.0
 * desc   :  征信记录页面
 */
@Route(path = ARouterUrl.CreditInquiryRecordActivityUrl)
public class CreditInquiryRecordActivity extends BaseActivity<CreditInquiryRecordActivityPresenter>
        implements CreditInquiryRecordActivityContract.View {
    @BindView(R.id.credit_inquiry_record_mStatusBar)
    View creditInquiryRecordMStatusBar;
    @BindView(R.id.credit_inquiry_record_titleBar)
    TitleBar creditInquiryRecordTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_inquiry_record;
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
        setStatusBar(creditInquiryRecordMStatusBar, R.color.blue1);
        setTittleBarBackground(creditInquiryRecordTitleBar, R.color.blue1);
        setPageTitle(creditInquiryRecordTitleBar, "征信记录", R.color.white);
        setPageLeftText(creditInquiryRecordTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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