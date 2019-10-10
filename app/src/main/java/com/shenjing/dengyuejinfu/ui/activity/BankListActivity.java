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
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.decoration.SpacesItemDecoration;
import com.shenjing.dengyuejinfu.entity.BankListBean;
import com.shenjing.dengyuejinfu.ui.activity.adapter.BankListAdapter;
import com.shenjing.dengyuejinfu.ui.contract.BankListActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.BankListActivityPresenter;
import com.shenjing.dengyuejinfu.utils.GlideUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/9/3010:23
 * version: 1.0
 * desc   :  银行列表
 */
@Route(path = ARouterUrl.BankListActivityUrl)
public class BankListActivity extends BaseActivity<BankListActivityPresenter>
        implements BankListActivityContract.View {
    @BindView(R.id.bankList_mStatusBar)
    View bankListMStatusBar;
    @BindView(R.id.bankList_titleBar)
    TitleBar bankListTitleBar;
    @BindView(R.id.bankList_topIv)
    ImageView bankListTopIv;
    @BindView(R.id.bankList_recycler)
    RecyclerView bankListRecycler;
    @BindView(R.id.bankList_swipe)
    SwipeRefreshLayout bankListSwipe;
    private BankListAdapter bankListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_list;
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
        setStatusBar(bankListMStatusBar, R.color.blue1);
        setTittleBarBackground(bankListTitleBar, R.color.blue1);
        setPageTitle(bankListTitleBar, "银行列表", R.color.white);
        setPageLeftText(bankListTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        mPresenter.getBankList();
        bankListAdapter = new BankListAdapter(BankListActivity.this);
       GridLayoutManager gridLayoutManager = new GridLayoutManager(BankListActivity.this, 3, RecyclerView.VERTICAL, false);
        bankListRecycler.setLayoutManager(gridLayoutManager);
        bankListRecycler.setAdapter(bankListAdapter);
        bankListAdapter.setEmptyView(R.layout.view_empty, bankListRecycler);
        bankListRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8f)
                , ConvertUtils.dp2px(8f)));

        bankListSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBankList();
            }
        });
        bankListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BankListBean.DataBean.BankListBeans bankListBean = (BankListBean.DataBean.BankListBeans)adapter.getData().get(position);
                int bankId = bankListBean.getId();
                ARouter.getInstance().build(ARouterUrl.CardListActivityUrl)
                        .withInt("bankId",bankId)
                        .navigation(BankListActivity.this,new LoginNavigationCallback());
            }
        });

    }

    @Override
    public void getBankListSuccess(String imageUrl) {
        if (imageUrl != null) {
            GlideUtils.initImageWithFileCache(BankListActivity.this,imageUrl,bankListTopIv);
        }
    }

    @Override
    public void getBankListFailure() {

    }

    @Override
    public RecyclerView getRecycler() {
        return bankListRecycler;
    }

    @Override
    public BankListAdapter getAdapter() {
        return bankListAdapter;
    }

    @Override
    public void isCanRefresh(boolean refresh) {

    }
}
