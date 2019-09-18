package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.contract.ShareActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.ShareActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1416:57
 * version: 1.0
 * desc   :分享页面
 */
@Route(path = ARouterUrl.ShareActivityUrl)
public class ShareActivity extends BaseActivity<ShareActivityPresenter> implements ShareActivityContract.View {

    @BindView(R.id.share_mStatusBar)
    View shareMStatusBar;
    @BindView(R.id.share_titleBar)
    TitleBar shareTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
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
        setStatusBar(shareMStatusBar, R.color.blue1);
        setPageTitle(shareTitleBar, "分享", R.color.white);
        setTittleBarBackground(shareTitleBar, R.color.blue1);
        setPageLeftText(shareTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
