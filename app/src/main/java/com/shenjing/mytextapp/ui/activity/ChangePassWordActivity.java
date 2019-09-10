package com.shenjing.mytextapp.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.ChangePassWordActivityContract;
import com.shenjing.mytextapp.ui.presenter.ChangePassWordActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/159:39
 * version: 1.0
 * desc   :修改密码
 */
@Route(path = ARouterUrl.ChangePassWordActivityUrl)
public class ChangePassWordActivity extends BaseActivity<ChangePassWordActivityPresenter>
        implements ChangePassWordActivityContract.View {
    @BindView(R.id.change_pass_mStatusBar)
    View changePassMStatusBar;
    @BindView(R.id.change_pass_titleBar)
    TitleBar changePassTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
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
        setStatusBar(changePassMStatusBar, R.color.blue1);
        setTittleBarBackground(changePassTitleBar,R.color.blue1);
        setPageTitle(changePassTitleBar,"修改密码",R.color.white);
        setPageLeftText(changePassTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
