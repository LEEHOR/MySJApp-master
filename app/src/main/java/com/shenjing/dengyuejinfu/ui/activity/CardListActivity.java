package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.decoration.SpacesItemDecoration;
import com.shenjing.dengyuejinfu.entity.CardListBean;
import com.shenjing.dengyuejinfu.ui.activity.adapter.CardListAdapter;
import com.shenjing.dengyuejinfu.ui.contract.CardListActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.CardListActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/9/3011:56
 * version: 1.0
 * desc   : 银行卡列表
 */
@Route(path = ARouterUrl.CardListActivityUrl)
public class CardListActivity extends BaseActivity<CardListActivityPresenter>
        implements CardListActivityContract.View {
    @Autowired(name = "bankId")
    int bankId;
    @BindView(R.id.currency_mStatusBar)
    View currencyMStatusBar;
    @BindView(R.id.currency_titleBar)
    TitleBar currencyTitleBar;
    @BindView(R.id.currency_recycler)
    RecyclerView currencyRecycler;
    @BindView(R.id.currency_swipe)
    SwipeRefreshLayout currencySwipe;
    private CardListAdapter cardListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.currency_recycler_page;
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
        setStatusBar(currencyMStatusBar, R.color.blue1);
        setTittleBarBackground(currencyTitleBar, R.color.blue1);
        setPageTitle(currencyTitleBar, "银行卡列表", R.color.white);
        setPageLeftText(currencyTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        mPresenter.getCardList(bankId);
        cardListAdapter = new CardListAdapter(CardListActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CardListActivity.this, 2, RecyclerView.VERTICAL, false);
        currencyRecycler.setLayoutManager(gridLayoutManager);
        currencyRecycler.setAdapter(cardListAdapter);
        cardListAdapter.setEmptyView(R.layout.view_empty, currencyRecycler);
        currencyRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8f)
                , ConvertUtils.dp2px(8f)));

        currencySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getCardList(bankId);
            }
        });
        cardListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CardListBean.DataBean.CardList cardList = (CardListBean.DataBean.CardList) adapter.getData().get(position);
                String cardName = cardList.getCardName();
                String jumpUrl = cardList.getJumpUrl();
                ARouter.getInstance().build(ARouterUrl.WebViewActivityUrl)
                        .withString(BaseParams.webViewTitle, cardName)
                        .withString(BaseParams.webViewUrl, jumpUrl)
                        .navigation(CardListActivity.this, new LoginNavigationCallback());
            }
        });
    }


    @Override
    public void getCardListSuccess() {

    }

    @Override
    public void getCardListFailure() {

    }

    @Override
    public RecyclerView getRecycler() {
        return currencyRecycler;
    }

    @Override
    public CardListAdapter getAdapter() {
        return cardListAdapter;
    }

    @Override
    public void isCanRefresh(boolean refresh) {
        currencySwipe.setRefreshing(refresh);
    }
}
