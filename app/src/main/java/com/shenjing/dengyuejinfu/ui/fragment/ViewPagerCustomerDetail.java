package com.shenjing.dengyuejinfu.ui.fragment;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseFragment;
import com.shenjing.dengyuejinfu.decoration.SpacesItemDecoration;
import com.shenjing.dengyuejinfu.ui.contract.ViewPagerCustomerDetailContract;
import com.shenjing.dengyuejinfu.ui.fragment.adapter.CustomerDetailAdapter;
import com.shenjing.dengyuejinfu.ui.presenter.ViewPageCustomerDetailPresenter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/11/515:39
 * version: 1.0
 * desc   :我的客户页面fragment
 */

public class ViewPagerCustomerDetail extends BaseFragment<ViewPageCustomerDetailPresenter>
        implements ViewPagerCustomerDetailContract.View {

    @BindView(R.id.customerDetailRecycler)
    RecyclerView customerDetailRecycler;
    private CustomerDetailAdapter customerDetailAdapter;
    private List<Integer> idStrings=new ArrayList<>();

    /**
     * @param level 会员类型
     * @param type  审核状态
     * @return
     */
    public static ViewPagerCustomerDetail newInstance(int level, int type, List<Integer> directs, List<Integer> inDirects) {
        ViewPagerCustomerDetail viewPagerCustomerDetail = new ViewPagerCustomerDetail();
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        bundle.putInt("type", type);
        bundle.putIntegerArrayList("direct", (ArrayList<Integer>) directs);
        bundle.putIntegerArrayList("indirect", (ArrayList<Integer>) inDirects);
        viewPagerCustomerDetail.setArguments(bundle);
        return viewPagerCustomerDetail;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_customer_detail;
    }

    @Override
    protected void initInjector() {
        initFragmentComponent().inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        idStrings.clear();
        customerDetailAdapter = new CustomerDetailAdapter();
        if (getArguments() != null) {
            int type = getArguments().getInt("type");
            ArrayList<Integer> direct = getArguments().getIntegerArrayList("direct");
            ArrayList<Integer> indirect = getArguments().getIntegerArrayList("indirect");
            if (direct!=null){
                idStrings.addAll(direct);
            }
            if (indirect !=null){
                idStrings.addAll(indirect);
            }

            LogUtils.d("用户",idStrings.toString());
            mPresenter.getCustomDetail(idStrings.toString(),String.valueOf(type+1));

        }

    }

    @Override
    protected void initFunc() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        customerDetailRecycler.setLayoutManager(linearLayoutManager);
        customerDetailRecycler.setAdapter(customerDetailAdapter);
        customerDetailAdapter.setEmptyView(R.layout.view_empty, customerDetailRecycler);
        customerDetailRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8f)
                , ConvertUtils.dp2px(10f)));
    }

    @Override
    public void getCustomerDetailSuccess() {

    }

    @Override
    public void getCustomerDetailFailure() {

    }

    @Override
    public void isCanRefresh(boolean refresh) {

    }

    @Override
    public CustomerDetailAdapter getAdapter() {
        return customerDetailAdapter;
    }

    @Override
    public RecyclerView getRecycler() {
        return customerDetailRecycler;
    }
}
