package com.shenjing.mytextapp.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.AddCreditCardActivityContract;
import com.shenjing.mytextapp.ui.presenter.AddCreditCardActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1419:35
 * version: 1.0
 * desc   :添加信用卡
 */
@Route(path = ARouterUrl.AddCardActivityUrl)
public class AddCreditCardActivity extends BaseActivity<AddCreditCardActivityPresenter>
        implements AddCreditCardActivityContract.View {
    @BindView(R.id.add_card_mStatusBar)
    View addCardMStatusBar;
    @BindView(R.id.add_card_titleBar)
    TitleBar addCardTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_credit_card;
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
        setStatusBar(addCardMStatusBar, R.color.blue1);
        setTittleBarBackground(addCardTitleBar,R.color.blue1);
        setPageTitle(addCardTitleBar,"添加信用卡",R.color.white);
        setPageLeftText(addCardTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
