package com.shenjing.dengyuejinfu.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.activity.adapter.ViewPagerCustomerDetailAdapter;
import com.shenjing.dengyuejinfu.ui.contract.MyCustomerDetailActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.MyCustomerDetailActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import java.util.List;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/11/515:19
 * version: 1.0
 * desc   :我的客户列表详情页
 */
@Route(path = ARouterUrl.MyCustomerDetailActivityUrl)
public class MyCustomerDetailActivity extends BaseActivity<MyCustomerDetailActivityPresenter> implements MyCustomerDetailActivityContract.View {

    @BindView(R.id.myCustomer_detail_mStatusBar)
    View myCustomerDetailMStatusBar;
    @BindView(R.id.myCustomer_detail_titleBar)
    TitleBar myCustomerDetailTitleBar;
    @BindView(R.id.myCustomer_detail_tab)
    TabLayout myCustomerDetailTab;
    @BindView(R.id.myCustomer_detail_viewPage)
    ViewPager myCustomerDetailViewPage;

    @Autowired(name = "direct")
    List<Integer> direct;

    @Autowired(name = "inDirect")
    List<Integer> inDirect;

    @Autowired(name="customer_id")
    int customer_id;
    @Autowired(name = "customer_name")
    String customerName;
    @Autowired(name = "bundle")
    Bundle bundle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycustomer_detail;
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
        setStatusBar(myCustomerDetailMStatusBar, R.color.blue1);
        setPageTitle(myCustomerDetailTitleBar, customerName, R.color.white);
        setTittleBarBackground(myCustomerDetailTitleBar, R.color.blue1);
        setPageLeftText(myCustomerDetailTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
        //LogUtils.d("数组",direct.toString(),inDirect.toString());
        ViewPagerCustomerDetailAdapter viewPagerCustomerDetailAdapter = new ViewPagerCustomerDetailAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                customer_id,
                bundle.getIntegerArrayList("direct"),
                bundle.getIntegerArrayList("inDirect"));
        myCustomerDetailViewPage.setAdapter(viewPagerCustomerDetailAdapter);
        myCustomerDetailViewPage.setCurrentItem(0);
        myCustomerDetailTab.setupWithViewPager(myCustomerDetailViewPage, true);
    }

    @Override
    protected void initFunc() {

    }
}
