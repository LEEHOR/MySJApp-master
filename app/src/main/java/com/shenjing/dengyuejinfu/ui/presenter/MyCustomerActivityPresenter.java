package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.CustomerBean;
import com.shenjing.dengyuejinfu.entity.MyCustomerBean;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CustomerApi;
import com.shenjing.dengyuejinfu.ui.contract.MyCustomerActivityContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class MyCustomerActivityPresenter extends BasePresenter<MyCustomerActivityContract.View>
        implements MyCustomerActivityContract.Presenter{


    private List<CustomerBean> customerBeanList =new ArrayList<>();
    @Inject
    public MyCustomerActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCustomer(long userId) {
        customerBeanList.clear();
        mView.showLoading();
        RetrofitManager.create(CustomerApi.class).getCustomer(userId)
                .compose(mView.<MyCustomerBean>bindToLife())
                .compose(RxSchedulers.<MyCustomerBean>applySchedulers())
                .subscribe(new Consumer<MyCustomerBean>() {
                    @Override
                    public void accept(MyCustomerBean myCustomerBean) {
                        mView.hideLoading();
                        if (myCustomerBean.getCode() != null && myCustomerBean.getCode().equals("0000")) {
                            mView.showSuccess(myCustomerBean.getMsg());
                            mView.isCanRefresh(false);
                            if (myCustomerBean.getData() != null) {
                                List<MyCustomerBean.DataBean.CustomerBean> customer = myCustomerBean.getData().getCustomer();
                                if (customer != null) {
                                    for (int i = 0; i <customer.size() ; i++) {
                                        CustomerBean customerBean=new CustomerBean();
                                        customerBean.setLevelName(customer.get(i).getLevelName());
                                        customerBean.setLevelType(customer.get(i).getLevelType());
                                        customerBean.setId(customer.get(i).getId());
                                        if (i==0){
                                            if (myCustomerBean.getData().get_$1b() != null) {
                                                customerBean.setDirect(myCustomerBean.getData().get_$1b().get_$1d());
                                                customerBean.setInDirect(myCustomerBean.getData().get_$1b().get_$1i());
                                            }

                                        } else if (i==1){
                                            if (myCustomerBean.getData().get_$2b() != null) {
                                                customerBean.setDirect(myCustomerBean.getData().get_$2b().get_$2d());
                                                customerBean.setInDirect(myCustomerBean.getData().get_$2b().get_$2i());
                                            }

                                        } else if(i==2){
                                            if (myCustomerBean.getData().get_$3b() != null) {
                                                customerBean.setDirect(myCustomerBean.getData().get_$3b().get_$3d());
                                                customerBean.setInDirect(myCustomerBean.getData().get_$3b().get_$3i());
                                            }

                                        } else if (i==3){
                                            if (myCustomerBean.getData().get_$4b() != null) {
                                                customerBean.setDirect(myCustomerBean.getData().get_$4b().get_$4d());
                                                customerBean.setInDirect(myCustomerBean.getData().get_$4b().get_$4i());
                                            }

                                        } else if (i==4){
                                            if (myCustomerBean.getData().get_$5b() != null) {
                                                customerBean.setDirect(myCustomerBean.getData().get_$5b().get_$5d());
                                                customerBean.setInDirect(myCustomerBean.getData().get_$5b().get_$5i());
                                            }
                                        }
                                        customerBeanList.add(customerBean);
                                        mView.getAdapter().setNewData(customerBeanList);
                                    }
                                }

                            }
                        } else {
                            mView.showFail(myCustomerBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getCustomerFailure();
                            mView.getAdapter().setEmptyView(R.layout.view_error, mView.getRecycler());
                        }
                    }
                }, this::loadError);
    }


    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误...");
        mView.isCanRefresh(false);
        mView.getAdapter().setEmptyView(R.layout.view_error, mView.getRecycler());
    }
}
