package com.shenjing.mytextapp.ui.fragment;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseFragment;
import com.shenjing.mytextapp.ui.contract.MemberFragmentContract;
import com.shenjing.mytextapp.ui.presenter.MemberFragmentPresenter;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1216:29
 * version: 1.0
 * desc   :加入会员
 */
public class MemberFragment extends BaseFragment<MemberFragmentPresenter> implements MemberFragmentContract.View {


    @BindView(R.id.member_mStatusBar)
    View mStatusBar;
    @BindView(R.id.member_titleBar)
    TitleBar fragmentCouponTitleBar;

    public static MemberFragment newInstance() {
        MemberFragment fragment = new MemberFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    protected void initInjector() {
        initFragmentComponent().inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBar(mStatusBar,R.color.white);
        setPageTitle(fragmentCouponTitleBar, "会员升级",R.color.textColor);
        setTittleBarBackgraound(fragmentCouponTitleBar,R.color.white);
    }

    @Override
    protected void initFunc() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            LogUtils.d("showFragment--member");
        }
    }
}
