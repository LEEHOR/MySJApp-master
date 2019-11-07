package com.shenjing.dengyuejinfu.ui.presenter;



import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.CustomerDetail;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CustomerApi;
import com.shenjing.dengyuejinfu.ui.contract.ViewPagerCustomerDetailContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class ViewPageCustomerDetailPresenter extends BasePresenter<ViewPagerCustomerDetailContract.View>
        implements ViewPagerCustomerDetailContract.Presenter {


    @Inject
    public ViewPageCustomerDetailPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCustomDetail(String isString, String status) {
        mView.showLoading();
        LogUtils.d("参数",isString);
        RetrofitManager.create(CustomerApi.class).getCustomerDetail(isString,status)
                .compose(mView.<CustomerDetail>bindToLife())
                .compose(RxSchedulers.<CustomerDetail>applySchedulers())
                .subscribe(new Consumer<CustomerDetail>() {
                    @Override
                    public void accept(CustomerDetail customerDetail) {
                        mView.hideLoading();
                        if (customerDetail.getCode() != null && customerDetail.getCode().equals("0000")) {
                            if (customerDetail.getData() != null) {
                                mView.getAdapter().setNewData(customerDetail.getData());
                            }
                            mView.showSuccess(customerDetail.getMsg());
                            mView.getCustomerDetailSuccess();
                        } else {
                            mView.showFail(customerDetail.getMsg());
                            mView.isCanRefresh(false);
                            mView.getCustomerDetailFailure();
                            mView.getAdapter().setEmptyView(R.layout.view_error, mView.getRecycler());
                        }
                    }
                },this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误...");
        mView.isCanRefresh(false);
        mView.getAdapter().setEmptyView(R.layout.view_error, mView.getRecycler());
    }
}
