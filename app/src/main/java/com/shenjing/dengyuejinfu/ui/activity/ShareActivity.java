package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import androidx.cardview.widget.CardView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.ui.contract.ShareActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.ShareActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.page_share)
    CardView pageShare;
    @BindView(R.id.other_share)
    CardView otherShare;

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

    @OnClick({R.id.page_share, R.id.other_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.page_share:
                ARouter.getInstance().build(ARouterUrl.WebViewActivityUrl)
                        .withString(BaseParams.webViewTitle,"分享")
                        .withString(BaseParams.webViewUrl,null)
                        .navigation(this,new LoginNavigationCallback());
                break;
            case R.id.other_share:
                    ARouter.getInstance().build(ARouterUrl.QRShareActivityUrl)
                            .navigation(this,new LoginNavigationCallback());
                break;
        }
    }
}
