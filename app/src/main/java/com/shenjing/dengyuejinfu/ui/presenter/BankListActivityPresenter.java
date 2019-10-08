package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.BankListBean;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.IndexFragmentApi;
import com.shenjing.dengyuejinfu.ui.contract.BankListActivityContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class BankListActivityPresenter extends BasePresenter<BankListActivityContract.View>
        implements BankListActivityContract.Presenter {

    @Inject
    public BankListActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getBankList() {
        mView.showLoading();
        RetrofitManager.create(IndexFragmentApi.class).getBankList()
                .compose(mView.<BankListBean>bindToLife())
                .compose(RxSchedulers.<BankListBean>applySchedulers())
                .subscribe(new Consumer<BankListBean>() {
                    @Override
                    public void accept(BankListBean bankListBean) {
                        mView.hideLoading();
                        if (bankListBean.getCode() != null && bankListBean.getCode().equals("0000")) {
                            mView.showSuccess(bankListBean.getMsg());
                            mView.getBankListSuccess(bankListBean.getData().getImg());
                            mView.isCanRefresh(false);
                            if (bankListBean.getData() != null && bankListBean.getData().getBankList() != null && bankListBean.getData().getBankList().size() > 0) {
                                mView.getAdapter().setNewData(bankListBean.getData().getBankList());
                            } else {
                                mView.getAdapter().setEmptyView(R.layout.view_empty, mView.getRecycler());
                            }

                        } else {
                            mView.showFail(bankListBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getBankListFailure();
                            mView.getAdapter().setEmptyView(R.layout.view_error, mView.getRecycler());
                        }
                    }
                }, this::loadError);
    }


    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanRefresh(false);
        mView.getAdapter().setEmptyView(R.layout.view_error, mView.getRecycler());
    }
}
