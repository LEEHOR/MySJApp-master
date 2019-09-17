package com.shenjing.mytextapp.ui.activity;


import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.CertificationActivityContract;
import com.shenjing.mytextapp.ui.presenter.CertificationActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;


/**
 * author : Leehor
 * date   : 2019/8/1418:42
 * version: 1.0
 * desc   :身份认证
 */
@Route(path = ARouterUrl.CertificationActivityUrl)
public class CertificationActivity extends BaseActivity<CertificationActivityPresenter>
        implements CertificationActivityContract.View {
    @BindView(R.id.certification_mStatusBar)
    View certificationMStatusBar;
    @BindView(R.id.certification_titleBar)
    TitleBar certificationTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification;
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
        setStatusBar(certificationMStatusBar, R.color.blue1);
        setTittleBarBackground(certificationTitleBar,R.color.blue1);
        setPageTitle(certificationTitleBar,"身份认证",R.color.white);
        setPageLeftText(certificationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
