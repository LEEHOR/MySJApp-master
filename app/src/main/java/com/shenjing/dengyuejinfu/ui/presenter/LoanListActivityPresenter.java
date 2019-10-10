package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.BankListBean;
import com.shenjing.dengyuejinfu.entity.LoanListBean;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.IndexFragmentApi;
import com.shenjing.dengyuejinfu.ui.contract.LoanListActivityContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class LoanListActivityPresenter extends BasePresenter<LoanListActivityContract.View>
        implements LoanListActivityContract.Presenter {

    @Inject
    public LoanListActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getLoanList() {
        mView.showLoading();
        RetrofitManager.create(IndexFragmentApi.class).getLoanList()
                .compose(mView.<LoanListBean>bindToLife())
                .compose(RxSchedulers.<LoanListBean>applySchedulers())
                .subscribe(new Consumer<LoanListBean>() {
                    @Override
                    public void accept(LoanListBean loanListBean) {
                        mView.hideLoading();
                        if (loanListBean.getCode() != null && loanListBean.getCode().equals("0000")) {
                            mView.showSuccess(loanListBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getAdapter().setNewData(loanListBean.getData() != null?loanListBean.getData().getLoadList():null);
                            if (loanListBean.getData() != null) {
                                mView.getLoanListSuccess(loanListBean.getData().getImg());
                            }
                        } else {
                            mView.showFail(loanListBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getLoanListFailure();
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
