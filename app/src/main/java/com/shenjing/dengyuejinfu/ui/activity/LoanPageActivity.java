package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.contract.LoanPageActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.LoanPageActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/199:15
 * version: 1.0
 * desc   :  我要贷款页面
 */
@Route(path = ARouterUrl.LoanPageActivityUrl)
public class LoanPageActivity extends BaseActivity<LoanPageActivityPresenter> implements LoanPageActivityContract.View {
    @BindView(R.id.loan_mStatusBar)
    View loanMStatusBar;
    @BindView(R.id.loan_titleBar)
    TitleBar loanTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loan_page;
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
        setStatusBar(loanMStatusBar, R.color.blue1);
        setTittleBarBackground(loanTitleBar, R.color.blue1);
        setPageTitle(loanTitleBar, "我要贷款", R.color.white);
        setPageLeftText(loanTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
