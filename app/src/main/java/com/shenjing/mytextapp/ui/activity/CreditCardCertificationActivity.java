package com.shenjing.mytextapp.ui.activity;

import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.CreditCardCertificationActivityContract;
import com.shenjing.mytextapp.ui.presenter.CreditCardCertificationActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/1419:10
 * version: 1.0
 * desc   :信用卡认证
 */
@Route(path = ARouterUrl.CreditCardCertificationActivityUrl)
public class CreditCardCertificationActivity extends BaseActivity<CreditCardCertificationActivityPresenter>
        implements CreditCardCertificationActivityContract.View {
    @BindView(R.id.credit_card_mStatusBar)
    View creditCardMStatusBar;
    @BindView(R.id.credit_card_titleBar)
    TitleBar creditCardTitleBar;
    @BindView(R.id.credit_card_floatingButton)
    FloatingActionButton creditCardFloatingButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_card_certification;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        setStatusBar(creditCardMStatusBar, R.color.blue1);
        setTittleBarBackground(creditCardTitleBar, R.color.blue1);
        setPageTitle(creditCardTitleBar, "信用卡包", R.color.white);
        setPageLeftText(creditCardTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {

    }

    @OnClick(R.id.credit_card_floatingButton)
    public void onClick() {
        ARouter.getInstance().build(ARouterUrl.AddCardActivityUrl).navigation();
    }
}
