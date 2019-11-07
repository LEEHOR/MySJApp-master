package com.shenjing.dengyuejinfu.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.Postcard;
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
import com.shenjing.dengyuejinfu.entity.CustomerBean;
import com.shenjing.dengyuejinfu.ui.activity.adapter.MyCustomerAdapter;
import com.shenjing.dengyuejinfu.ui.contract.MyCustomerActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.MyCustomerActivityPresenter;
import com.shenjing.dengyuejinfu.utils.PhoneUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1415:28
 * version: 1.0
 * desc   :我的客户页面
 */
@Route(path = ARouterUrl.MyCustomerActivityUrl)
public class MyCustomerActivity extends BaseActivity<MyCustomerActivityPresenter> implements MyCustomerActivityContract.View {

    @BindView(R.id.customer_mStatusBar)
    View customerMStatusBar;
    @BindView(R.id.customer_titleBar)
    TitleBar customerTitleBar;
    @BindView(R.id.MyCustomer_recycler)
    RecyclerView MyCustomerRecycler;
    @BindView(R.id.myCustomer_swipe)
    SwipeRefreshLayout myCustomerSwipe;
    private MyCustomerAdapter myCustomerAdapter;
    private List<Integer> direct = new ArrayList<>();
    private List<Integer> inDirect = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer;
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
        setStatusBar(customerMStatusBar, R.color.blue1);
        setPageTitle(customerTitleBar, "我的客户", R.color.white);
        setTittleBarBackground(customerTitleBar, R.color.blue1);
        setPageLeftText(customerTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        myCustomerAdapter = new MyCustomerAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        MyCustomerRecycler.setLayoutManager(linearLayoutManager);
        MyCustomerRecycler.setAdapter(myCustomerAdapter);
        myCustomerAdapter.setEmptyView(R.layout.view_empty, MyCustomerRecycler);
        MyCustomerRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8f)
                , ConvertUtils.dp2px(10f)));

        myCustomerSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCustomerList();
            }
        });
        myCustomerAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                // direct.clear();
                // inDirect.clear();
                CustomerBean customerBean = (CustomerBean) adapter.getData().get(position);
                //  direct.addAll(customerBean.getDirect());
                // inDirect.addAll(customerBean.getInDirect());
                Bundle bundle=new Bundle();
                bundle.putIntegerArrayList("direct",(ArrayList<Integer>) customerBean.getDirect());
                bundle.putIntegerArrayList("inDirect",(ArrayList<Integer>) customerBean.getInDirect());
                Postcard build = ARouter.getInstance().build(ARouterUrl.MyCustomerDetailActivityUrl);
                build.withInt("customer_id", customerBean.getId())
                        .withString("customer_name", customerBean.getLevelName());
//                if (customerBean.getDirect() != null) {
//                    build.withIntegerArrayList("direct", (ArrayList<Integer>) customerBean.getDirect());
//                }
//                if (customerBean.getInDirect() != null) {
//                    build.withIntegerArrayList("inDirect", (ArrayList<Integer>) customerBean.getInDirect());
//                }
                build.withBundle("bundle",bundle);
                build.navigation(MyCustomerActivity.this, new LoginNavigationCallback());
            }
        });

        getCustomerList();

    }

    private void getCustomerList() {
        mPresenter.getCustomer(Long.parseLong(BaseParams.userId));
    }

    @Override
    public void getCustomerSuccess() {

    }

    @Override
    public void getCustomerFailure() {

    }

    @Override
    public void isCanRefresh(boolean refresh) {
        myCustomerSwipe.setRefreshing(refresh);
    }

    @Override
    public MyCustomerAdapter getAdapter() {
        return myCustomerAdapter;
    }

    @Override
    public RecyclerView getRecycler() {
        return MyCustomerRecycler;
    }

//    @OnClick(R.id.myCustomer_1)
//    public void onClick() {
//        ARouter.getInstance().build(ARouterUrl.MyCustomerDetailActivityUrl)
//                .withInt("VipType",0)
//                .navigation(this,new LoginNavigationCallback());
//    }
}
