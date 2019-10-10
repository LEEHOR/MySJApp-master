package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CreditCardApi;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.CreditCardListBean;
import com.shenjing.dengyuejinfu.ui.contract.CreditCardCertificationActivityContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class CreditCardCertificationActivityPresenter extends BasePresenter<CreditCardCertificationActivityContract.View>
        implements CreditCardCertificationActivityContract.Presenter {

    @Inject
    public CreditCardCertificationActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCardList(String userId) {
        mView.showLoading();
        RetrofitManager.create(CreditCardApi.class).getCreditCardList(Long.parseLong(userId))
                .compose(mView.<CreditCardListBean>bindToLife())
                .compose(RxSchedulers.<CreditCardListBean>applySchedulers())
                .subscribe((Consumer<CreditCardListBean>) creditCardListModel -> {
                    if (creditCardListModel.getCode() != null && creditCardListModel.getCode().equals("0000")) {
                        mView.showSuccess(creditCardListModel.getMsg());
                        mView.Refresh(false);
                        mView.getViewListAdapter().setNewData(creditCardListModel.getData()!=null?creditCardListModel.getData().getCreditCard():null);
                        mView.getCardListSuccess(creditCardListModel);
                    } else {
                        mView.showFail(creditCardListModel.getMsg());
                        mView.Refresh(false);
                        mView.getCardListFailure();
                    }
                    mView.hideLoading();
                },this::CardListError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void setCardStatus(String userId, String bankNo) {
        mView.showLoading();
        RetrofitManager.create(CreditCardApi.class).setCreditCardStatus(Long.parseLong(userId),bankNo)
                .compose(mView.<BaseBean>bindToLife())
                .compose(RxSchedulers.<BaseBean>applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean)  {
                        mView.hideLoading();
                        if (baseBean.getCode() != null && baseBean.getCode().equals("0000")) {
                            mView.showSuccess(baseBean.getMsg());
                            mView.setBankStatusSuccess();
                        } else {
                            mView.showFail(baseBean.getMsg());
                            mView.setBankStatusFailure();

                        }
                    }
                },this::setStatusError);
    }

    private void CardListError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
        mView.Refresh(false);
        mView.getViewListAdapter().setEmptyView(R.layout.view_error, mView.getRecycler());
    }

    private void setStatusError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
    }
}
