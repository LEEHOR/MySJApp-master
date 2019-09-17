package com.shenjing.mytextapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.MyCustomerActivityContract;
import com.shenjing.mytextapp.ui.presenter.MyCustomerActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Leehor
 * date   : 2019/8/1415:28
 * version: 1.0
 * desc   :我的客户页面
 */
@Route(path = ARouterUrl.MyCustomerActivityUrl)
public class MyCustomerActivity extends BaseActivity<MyCustomerActivityPresenter> implements MyCustomerActivityContract.View {

    @BindView(R.id.customer_mStatusBar)
    View customerMStatusBar;
    @BindView(R.id.customer_titleBar)
    TitleBar customerTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer;
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
        setStatusBar(customerMStatusBar, R.color.blue1);
        setPageTitle(customerTitleBar,"我的客户",R.color.white);
        setTittleBarBackground(customerTitleBar, R.color.blue1);
        setPageLeftText(customerTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
