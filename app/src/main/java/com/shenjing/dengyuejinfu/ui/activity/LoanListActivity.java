package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.shenjing.dengyuejinfu.entity.LoanListBean;
import com.shenjing.dengyuejinfu.ui.activity.adapter.LoanListAdapter;
import com.shenjing.dengyuejinfu.ui.contract.LoanListActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.LoanListActivityPresenter;
import com.shenjing.dengyuejinfu.utils.GlideUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/9/3014:05
 * version: 1.0
 * desc   :贷款列表
 */
@Route(path = ARouterUrl.LoanListActivityUrl)
public class LoanListActivity extends BaseActivity<LoanListActivityPresenter>
        implements LoanListActivityContract.View {
    @BindView(R.id.loanList_mStatusBar)
    View loanListMStatusBar;
    @BindView(R.id.loanList_titleBar)
    TitleBar loanListTitleBar;
    @BindView(R.id.loanList_topIv)
    ImageView loanListTopIv;
    @BindView(R.id.loanList_recycler)
    RecyclerView loanListRecycler;
    @BindView(R.id.loanList_swipe)
    SwipeRefreshLayout loanListSwipe;
    private LoanListAdapter loanListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loan_list;
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
        setStatusBar(loanListMStatusBar, R.color.blue1);
        setTittleBarBackground(loanListTitleBar, R.color.blue1);
        setPageTitle(loanListTitleBar, "贷款列表", R.color.white);
        setPageLeftText(loanListTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        mPresenter.getLoanList();
        loanListAdapter = new LoanListAdapter(LoanListActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(LoanListActivity.this, 3, RecyclerView.VERTICAL, false);
        loanListRecycler.setLayoutManager(gridLayoutManager);
        loanListRecycler.setAdapter(loanListAdapter);
        loanListAdapter.setEmptyView(R.layout.view_empty, loanListRecycler);
        loanListRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8f)
                , ConvertUtils.dp2px(8f)));

        loanListSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getLoanList();
            }
        });
        loanListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LoanListBean.DataBean.LoadListBean loadListBean = (LoanListBean.DataBean.LoadListBean) adapter.getData().get(position);
                String name = loadListBean.getName();
                String jumpUrl = loadListBean.getJumpUrl();
                ARouter.getInstance().build(ARouterUrl.WebViewActivityUrl)
                        .withString(BaseParams.webViewTitle, name)
                        .withString(BaseParams.webViewUrl, jumpUrl)
                        .navigation(LoanListActivity.this, new LoginNavigationCallback());
            }
        });

    }

    @Override
    public void getLoanListSuccess(String imageUrl) {
        if (imageUrl != null) {
            GlideUtils.initImageWithFileCache(LoanListActivity.this, imageUrl, loanListTopIv);
        }

    }

    @Override
    public void getLoanListFailure() {

    }

    @Override
    public RecyclerView getRecycler() {
        return loanListRecycler;
    }

    @Override
    public LoanListAdapter getAdapter() {
        return loanListAdapter;
    }

    @Override
    public void isCanRefresh(boolean refresh) {
        loanListSwipe.setRefreshing(refresh);
    }
}
