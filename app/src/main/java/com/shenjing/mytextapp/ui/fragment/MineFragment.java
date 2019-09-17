package com.shenjing.mytextapp.ui.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseFragment;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.common.LoginNavigationCallback;
import com.shenjing.mytextapp.ui.contract.MineFragmentContract;
import com.shenjing.mytextapp.ui.presenter.MineFragmentPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/1216:29
 * version: 1.0
 * desc   :我的页面
 */
public class MineFragment extends BaseFragment<MineFragmentPresenter> implements MineFragmentContract.View {

    @BindView(R.id.mine_toolbar)
    FrameLayout mineToolbar;
    @BindView(R.id.mine_setting_iv)
    ImageView mineSettingIv;
    @BindView(R.id.mine_transaction_detail)
    LinearLayout mineTransactionDetail;
    @BindView(R.id.mine_customer)
    LinearLayout mineCustomer;
    @BindView(R.id.mine_share)
    LinearLayout mineShare;
    @BindView(R.id.mine_certification)
    LinearLayout mineCertification;
    @BindView(R.id.mine_card_certification)
    LinearLayout mineCardCertification;
    @BindView(R.id.mine_operation_manual)
    LinearLayout mineOperationManual;
    @BindView(R.id.mine_cash_withdrawal)
    TextView mineCashWithdrawal;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInjector() {
        initFragmentComponent().inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        //沉浸式标题栏
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        int i = ConvertUtils.px2sp(BarUtils.getActionBarHeight());
        mineToolbar.setPadding(0, i, 0, 0);
    }


    @Override
    protected void initFunc() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            LogUtils.d("showFragment--mine");
        }
    }
    @OnClick({R.id.mine_setting_iv, R.id.mine_transaction_detail, R.id.mine_customer, R.id.mine_share,
            R.id.mine_certification, R.id.mine_card_certification, R.id.mine_operation_manual, R.id.mine_cash_withdrawal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_setting_iv:  //设置
                 ARouter.getInstance().build(ARouterUrl.SettingActivityUrl).navigation();
                break;
            case R.id.mine_transaction_detail: //交易详情
                ARouter.getInstance().build(ARouterUrl.TransactionDetailsActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
            case R.id.mine_customer: //我的客户
                ARouter.getInstance().build(ARouterUrl.MyCustomerActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
            case R.id.mine_share: //我的分享
                ARouter.getInstance().build(ARouterUrl.ShareActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
            case R.id.mine_certification: //实名认证
                ARouter.getInstance().build(ARouterUrl.CertificationActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
            case R.id.mine_card_certification: //信用卡认证
                ARouter.getInstance().build(ARouterUrl.CreditCardCertificationActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
            case R.id.mine_operation_manual:  //操作手册
                break;
            case R.id.mine_cash_withdrawal:  //立即提现
                ARouter.getInstance().build(ARouterUrl.CashWithdrawalActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
        }
    }
}
