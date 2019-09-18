package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.contract.DiscountRecordActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.DiscountRecordActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1511:34
 * version: 1.0
 * desc   : 提现记录页面
 */
@Route(path = ARouterUrl.DiscountRecordActivityUrl)
public class DiscountRecordActivity extends BaseActivity<DiscountRecordActivityPresenter>
        implements DiscountRecordActivityContract.View {

    @BindView(R.id.discount_record_mStatusBar)
    View discountRecordMStatusBar;
    @BindView(R.id.discount_record_titleBar)
    TitleBar discountRecordTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discount_ecord;
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
        setStatusBar(discountRecordMStatusBar, R.color.blue1);
        setTittleBarBackground(discountRecordTitleBar, R.color.blue1);
        setPageTitle(discountRecordTitleBar, "提现记录", R.color.white);
        setPageLeftText(discountRecordTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
