package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.base.BaseFragment;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.decoration.SpacesItemDecoration;
import com.shenjing.dengyuejinfu.respondModule.CreditCardListModel;
import com.shenjing.dengyuejinfu.ui.activity.adapter.CreditficationCardListAdapter;
import com.shenjing.dengyuejinfu.ui.contract.CreditCardCertificationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.CreditCardCertificationActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/1419:10
 * version: 1.0
 * desc   :信用卡认证
 */
@Route(path = ARouterUrl.CreditCardCertificationActivityUrl)
public class CreditCardCertificationActivity extends BaseActivity<CreditCardCertificationActivityPresenter>
        implements CreditCardCertificationActivityContract.View {
    @BindView(R.id.credit_card_mStatusBar)
    View creditCardMStatusBar;
    @BindView(R.id.credit_card_titleBar)
    TitleBar creditCardTitleBar;
    @BindView(R.id.credit_card_floatingButton)
    FloatingActionButton creditCardFloatingButton;
    @BindView(R.id.credit_card_recycler)
    RecyclerView creditCardRecycler;
    @BindView(R.id.credit_card_swipe)
    SwipeRefreshLayout creditCardSwipe;
    private LinearLayoutManager linearLayoutManager;
    private CreditficationCardListAdapter cardListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_card_certification;
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
        setStatusBar(creditCardMStatusBar, R.color.blue1);
        setTittleBarBackground(creditCardTitleBar, R.color.blue1);
        setPageTitle(creditCardTitleBar, "信用卡包", R.color.white);
        setPageLeftText(creditCardTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        cardListAdapter = new CreditficationCardListAdapter(this);
        creditCardRecycler.setLayoutManager(linearLayoutManager);
        creditCardRecycler.setAdapter(cardListAdapter);
        creditCardRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(5f)
                , ConvertUtils.dp2px(5f)));

    }

    @Override
    protected void initFunc() {
        mPresenter.getCardList(BaseParams.userId);
        creditCardSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getCardList(BaseParams.userId);
            }
        });
        cardListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CreditCardListModel.DataBean.CreditCardBean creditCardBean = (CreditCardListModel.DataBean.CreditCardBean) adapter.getData().get(position);
                mPresenter.setCardStatus(BaseParams.userId, creditCardBean.getCreditCardNo());
            }
        });

    }

    @OnClick(R.id.credit_card_floatingButton)
    public void onClick() {
        ARouter.getInstance().build(ARouterUrl.AddCardActivityUrl).navigation();
    }

    @Override
    public void getCardListSuccess(CreditCardListModel creditCardListModel) {

    }

    @Override
    public void getCardListFailure() {

    }

    @Override
    public void setBankStatusSuccess() {
        mPresenter.getCardList(BaseParams.userId);
    }

    @Override
    public void setBankStatusFailure() {

    }

    @Override
    public void Refresh(boolean Refresh) {
        creditCardSwipe.setRefreshing(Refresh);
    }

    @Override
    public CreditficationCardListAdapter getViewListAdapter() {
        return cardListAdapter;
    }
}
