package com.shenjing.mytextapp.ui.fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseFragment;
import com.shenjing.mytextapp.ui.contract.ViewPagerTransactionContract;
import com.shenjing.mytextapp.ui.presenter.ViewPageTransactionPresenter;

/**
 * author : Leehor
 * date   : 2019/8/1410:48
 * version: 1.0
 * desc   :交易明细ViewPager
 */
public class ViewPagerTransaction extends BaseFragment<ViewPageTransactionPresenter>
        implements ViewPagerTransactionContract.View {

    public static ViewPagerTransaction newInstance(int position) {
        ViewPagerTransaction viewPagerTransaction = new ViewPagerTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("type", position);
        viewPagerTransaction.setArguments(bundle);
        return viewPagerTransaction;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.viewpager_transaction_details;
    }

    @Override
    protected void initInjector() {
        initFragmentComponent().inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initFunc() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            LogUtils.d("showFragment--viewPager");
        }
    }
}
