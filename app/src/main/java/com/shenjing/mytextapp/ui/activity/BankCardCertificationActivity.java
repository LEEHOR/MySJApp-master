package com.shenjing.mytextapp.ui.activity;


import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.BankCardCertificationActivityContract;
import com.shenjing.mytextapp.ui.presenter.BankCardCertificationActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;
import butterknife.BindView;


/**
 * author : Leehor
 * date   : 2019/8/1418:47
 * version: 1.0
 * desc   :银行卡认证
 */
@Route(path = ARouterUrl.BankCardCertificationActivityUrl)
public class BankCardCertificationActivity extends BaseActivity<BankCardCertificationActivityPresenter>
        implements BankCardCertificationActivityContract.View {
    @BindView(R.id.card_certification_mStatusBar)
    View cardCertificationMStatusBar;
    @BindView(R.id.card_certification_titleBar)
    TitleBar cardCertificationTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_card_certification;
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
        setStatusBar(cardCertificationMStatusBar, R.color.blue1);
        setTittleBarBackground(cardCertificationTitleBar,R.color.blue1);
        setPageTitle(cardCertificationTitleBar,"银行卡认证",R.color.white);
        setPageLeftText(cardCertificationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
