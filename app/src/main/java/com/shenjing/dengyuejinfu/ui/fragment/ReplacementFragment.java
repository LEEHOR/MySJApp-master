package com.shenjing.dengyuejinfu.ui.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseFragment;
import com.shenjing.dengyuejinfu.ui.contract.ReplacementFragmentContract;
import com.shenjing.dengyuejinfu.ui.presenter.ReplacementFragmentPresenter;
import com.shenjing.dengyuejinfu.widgte.TitleBar;


import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1216:37
 * version: 1.0
 * desc   :智能代还
 */
public class ReplacementFragment extends BaseFragment<ReplacementFragmentPresenter> implements ReplacementFragmentContract.View {

    @BindView(R.id.replace_mStatusBar)
    View mStatusBar;
    @BindView(R.id.replace_titleBar)
    TitleBar fragmentCouponTitleBar;

    public static ReplacementFragment newInstance() {
        ReplacementFragment fragment = new ReplacementFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_replacement;
    }

    @Override
    protected void initInjector() {
        initFragmentComponent().inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBar(mStatusBar,R.color.white);
        setPageTitle(fragmentCouponTitleBar, "智能代还",R.color.textColor);
        setTittleBarBackgraound(fragmentCouponTitleBar,R.color.white);
    }

    @Override
    protected void initFunc() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            LogUtils.d("showFragment--replacement");
        }
    }
}
