package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.contract.CashWithdrawalActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.CashWithdrawalActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1511:15
 * version: 1.0
 * desc   :立即提现(分润页面)
 */
@Route(path = ARouterUrl.CashWithdrawalActivityUrl)
public class CashWithdrawalActivity extends BaseActivity<CashWithdrawalActivityPresenter>
        implements CashWithdrawalActivityContract.View {
    @BindView(R.id.cash_withdrawal_mStatusBar)
    View cashWithdrawalMStatusBar;
    @BindView(R.id.ash_withdrawal_titleBar)
    TitleBar ashWithdrawalTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_immediate_cash_withdrawal;
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
        setStatusBar(cashWithdrawalMStatusBar, R.color.blue1);
        setTittleBarBackground(ashWithdrawalTitleBar, R.color.blue1);
        setPageTitle(ashWithdrawalTitleBar, "分润", R.color.white);
        setPageLeftText(ashWithdrawalTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
        setTitleRight(ashWithdrawalTitleBar, "提现记录", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                ARouter.getInstance().build(ARouterUrl.DiscountRecordActivityUrl).navigation();
            }
        });
    }

    @Override
    protected void initFunc() {

    }
}
