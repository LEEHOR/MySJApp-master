package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.entity.AddCreditCardBean;
import com.shenjing.dengyuejinfu.ui.contract.AddCreditCardActivityContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class AddCreditCardActivityPresenter extends BasePresenter<AddCreditCardActivityContract.View>
        implements AddCreditCardActivityContract.Presenter {

    @Inject
    public AddCreditCardActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadCreditCard(Map<String, Object> map) {
        mView.showLoading();
        RetrofitManager.create(CertificationApi.class).uploadCreditCardInfo(map)
                .compose(mView.<AddCreditCardBean>bindToLife())
                .compose(RxSchedulers.<AddCreditCardBean>applySchedulers())
                .subscribe(new Consumer<AddCreditCardBean>() {
                    @Override
                    public void accept(AddCreditCardBean loginModel){
                        mView.hideLoading();
                        if (loginModel.getCode() != null && loginModel.getCode().equals("0000")) {
                            mView.showSuccess(loginModel.getMsg());
                            mView.uploadInfoSuccess();
                        } else {
                            mView.showFail(loginModel.getMsg());
                            mView.uploadInfoFailure();
                        }
                    }
                },this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
    }
}
