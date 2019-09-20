package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CreditCardApi;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.CreditCardListModel;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.respondModule.LostPassModel;
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
                .compose(mView.<CreditCardListModel>bindToLife())
                .compose(RxSchedulers.<CreditCardListModel>applySchedulers())
                .subscribe((Consumer<CreditCardListModel>) creditCardListModel -> {
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
                .compose(mView.<BaseModel>bindToLife())
                .compose(RxSchedulers.<BaseModel>applySchedulers())
                .subscribe(new Consumer<BaseModel>() {
                    @Override
                    public void accept(BaseModel baseModel)  {
                        mView.hideLoading();
                        if (baseModel.getCode() != null && baseModel.getCode().equals("0000")) {
                            mView.showSuccess(baseModel.getMsg());
                            mView.setBankStatusSuccess();
                        } else {
                            mView.showFail(baseModel.getMsg());
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
    }

    private void setStatusError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
    }
}
