package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.shenjing.dengyuejinfu.decoration.SpacesItemDecoration;
import com.shenjing.dengyuejinfu.ui.activity.adapter.IncreaseQuotaInformationAdapter;
import com.shenjing.dengyuejinfu.ui.contract.IncreaseTheQuotaInformationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.IncreaseTheQuotaInformationActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/9/2416:08
 * version: 1.0
 * desc   :提额资讯公共页
 */
@Route(path= ARouterUrl.IncreaseQuotaInformationActivityUrl)
public class IncreaseQuotaInformationActivity extends BaseActivity<IncreaseTheQuotaInformationActivityPresenter>
        implements IncreaseTheQuotaInformationActivityContract.View {
    @Autowired(name = BaseParams.IncreaseQuotaType)
    int QuotaType;
    @BindView(R.id.increase_information_mStatusBar)
    View increaseInformationMStatusBar;
    @BindView(R.id.increase_information_titleBar)
    TitleBar increaseInformationTitleBar;
    @BindView(R.id.increase_information_recycler)
    RecyclerView increaseInformationRecycler;
    @BindView(R.id.increase_quota_swipe)
    SwipeRefreshLayout increaseQuotaSwipe;
    private LinearLayoutManager linearLayoutManager;
    private IncreaseQuotaInformationAdapter informationAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_increase_quota_information;
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
        setStatusBar(increaseInformationMStatusBar, R.color.blue1);
        setPageTitle(increaseInformationTitleBar, "提额秘籍资讯", R.color.white);
        setTittleBarBackground(increaseInformationTitleBar, R.color.blue1);
        setPageLeftText(increaseInformationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
        increaseQuotaSwipe.setColorSchemeResources(R.color.blue4);
    }

    @Override
    protected void initFunc() {
        linearLayoutManager = new LinearLayoutManager(this);
        informationAdapter = new IncreaseQuotaInformationAdapter();
        increaseInformationRecycler.setLayoutManager(linearLayoutManager);
        increaseInformationRecycler.setAdapter(informationAdapter);
        increaseInformationRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(5f)
                , ConvertUtils.dp2px(8f)));

        mPresenter.getInformation(BaseParams.userId,String.valueOf(QuotaType),0);
        increaseQuotaSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });

        mPresenter.loadMore();

        informationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    @Override
    public IncreaseQuotaInformationAdapter getAdapter() {
        return informationAdapter;
    }

    @Override
    public SwipeRefreshLayout getSwipe() {
        return increaseQuotaSwipe;
    }

    @Override
    public RecyclerView getRecycler() {
        return increaseInformationRecycler;
    }

    @Override
    public void isCanRefresh(boolean refresh) {
        increaseQuotaSwipe.setRefreshing(refresh);
    }
}
