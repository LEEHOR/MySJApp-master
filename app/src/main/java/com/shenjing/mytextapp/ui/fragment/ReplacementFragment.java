package com.shenjing.mytextapp.ui.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseFragment;
import com.shenjing.mytextapp.ui.contract.ReplacementFragmentContract;
import com.shenjing.mytextapp.ui.presenter.ReplacementFragmentPresenter;
import com.shenjing.mytextapp.widgte.TitleBar;


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
        mFragmentComponent.inject(this);
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
}
