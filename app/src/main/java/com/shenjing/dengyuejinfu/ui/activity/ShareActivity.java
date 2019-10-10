package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import androidx.cardview.widget.CardView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.entity.QRBean;
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
    private boolean isCanShare = false;

    private String url;
    private String title;
    private String thumbnail;
    private String describe;

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
        mPresenter.getShareInfo(BaseParams.userId);

    }

    @OnClick({R.id.page_share, R.id.other_share})
    public void onClick(View view) {
        if (!isCanShare){
            ToastUtils.showLong("暂时无法分享");
            return;
        }
        switch (view.getId()) {
            case R.id.page_share:
                ARouter.getInstance().build(ARouterUrl.WebViewActivityUrl)
                        .withString(BaseParams.webViewTitle, title)
                        .withString(BaseParams.webViewUrl, url)
                        .navigation(this, new LoginNavigationCallback());
                break;
            case R.id.other_share:
                ARouter.getInstance().build(ARouterUrl.QRShareActivityUrl)
                        .withString("shareTitle",title)
                        .withString("shareUrl",url)
                        .withString("thumbnail",thumbnail)
                        .withString("describe",describe)
                        .navigation(this, new LoginNavigationCallback());
                break;
        }
    }

    @Override
    public void getSuccess(QRBean qrBean) {
        if (qrBean.getData() != null) {
            this.url = qrBean.getData().getUrl();
            this.title = qrBean.getData().getTitle();
            this.thumbnail = qrBean.getData().getThumbnail();
            this.describe = qrBean.getData().getDescribe();
        }
    }

    @Override
    public void getFailure() {

    }

    @Override
    public void isCanShare(boolean isCanShares) {
        this.isCanShare = isCanShares;
    }
}
