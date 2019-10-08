package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.CardListBean;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.IndexFragmentApi;
import com.shenjing.dengyuejinfu.ui.contract.CardListActivityContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class CardListActivityPresenter extends BasePresenter<CardListActivityContract.View>
        implements CardListActivityContract.Presenter {

    @Inject
    public CardListActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCardList(int bankId) {
        mView.showLoading();
        RetrofitManager.create(IndexFragmentApi.class).getCardList(bankId)
                .compose(mView.<CardListBean>bindToLife())
                .compose(RxSchedulers.<CardListBean>applySchedulers())
                .subscribe(new Consumer<CardListBean>() {
                    @Override
                    public void accept(CardListBean cardListBean) {
                        mView.hideLoading();
                        if (cardListBean.getCode() != null && cardListBean.getCode().equals("0000")) {
                            mView.showSuccess(cardListBean.getMsg());
                            mView.getCardListSuccess();
                            mView.isCanRefresh(false);
                            if (cardListBean.getData() != null && cardListBean.getData().getCardList() != null && cardListBean.getData().getCardList().size() > 0) {
                                mView.getAdapter().setNewData(cardListBean.getData().getCardList());
                            } else {
                                mView.getAdapter().setEmptyView(R.layout.view_empty, mView.getRecycler());
                            }

                        } else {
                            mView.showFail(cardListBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getCardListFailure();
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
