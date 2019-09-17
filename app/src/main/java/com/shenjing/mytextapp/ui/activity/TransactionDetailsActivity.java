package com.shenjing.mytextapp.ui.activity;

import android.view.View;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.activity.adapter.ViewPagerTransactionAdapter;
import com.shenjing.mytextapp.ui.contract.TransactionDetailsActivityContract;
import com.shenjing.mytextapp.ui.presenter.TransactionDetailsActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1410:27
 * version: 1.0
 * desc   :交易明细页面
 */
@Route(path = ARouterUrl.TransactionDetailsActivityUrl)
public class TransactionDetailsActivity extends BaseActivity<TransactionDetailsActivityPresenter>
        implements TransactionDetailsActivityContract.View {
    @BindView(R.id.transaction_mStatusBar)
    View transactionMStatusBar;
    @BindView(R.id.transaction_titleBar)
    TitleBar transactionTitleBar;
    @BindView(R.id.transaction_tab)
    TabLayout transactionTab;
    @BindView(R.id.transaction_viewPage)
    ViewPager transactionViewPage;
    private ViewPagerTransactionAdapter viewPagerTransactionAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_details;
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
        setStatusBar(transactionMStatusBar, R.color.blue1);
        setPageTitle(transactionTitleBar, "交易明细", R.color.white);
        setTittleBarBackground(transactionTitleBar, R.color.blue1);
        setPageLeftText(transactionTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });

        viewPagerTransactionAdapter = new ViewPagerTransactionAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        transactionViewPage.setAdapter(viewPagerTransactionAdapter);
        transactionViewPage.setCurrentItem(0);
        transactionTab.setupWithViewPager(transactionViewPage, true);
    }

    @Override
    protected void initFunc() {

    }
}
