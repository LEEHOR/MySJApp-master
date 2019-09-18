package com.shenjing.dengyuejinfu.ui.presenter;
import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.respondModule.LostPassModel;
import com.shenjing.dengyuejinfu.respondModule.SmsModel;
import com.shenjing.dengyuejinfu.ui.contract.LostPassActivityContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class LostPassActivityPresenter extends BasePresenter<LostPassActivityContract.View>
        implements LostPassActivityContract.Presenter {


    @Inject
    public LostPassActivityPresenter() {

    }



    @SuppressLint("CheckResult")
    @Override
    public void lostPassSubmit(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).lostPass(map)
                .compose(mView.<LostPassModel>bindToLife())
                .compose(RxSchedulers.<LostPassModel>applySchedulers())
                .subscribe(new Consumer<LostPassModel>() {
                    @Override
                    public void accept(LostPassModel lostPassModel) {
                        mView.hideLoading();
                        if (lostPassModel.getCode() != null && lostPassModel.getCode().equals("0000")) {
                            mView.showSuccess(lostPassModel.getMsg());
                            mView.getTimeButton().TimeOnFinished();
                            mView.getPassSuccess();
                        } else {
                            mView.showFail(lostPassModel.getMsg());
                            mView.getTimeButton().TimeOnFinished();
                            mView.getPassFailure();
                        }
                    }
                },this::lostPassError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void sendSms(String phone) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).get_sms2(phone)
                .compose(mView.<SmsModel>bindToLife())
                .compose(RxSchedulers.<SmsModel>applySchedulers())
                .subscribe(new Consumer<SmsModel>() {
                    @Override
                    public void accept(SmsModel smsModel) {
                        mView.hideLoading();
                        if (smsModel.getCode() != null && smsModel.getCode().equals("0000")) {

                            mView.showSuccess(smsModel.getMsg());
                            mView.getTimeButton().TimeOnFinished();
                            mView.getSmsSuccess();
                        } else {
                            mView.showFail(smsModel.getMsg());

                            mView.getTimeButton().TimeOnFinished();
                            mView.getSmsFailure();
                        }
                    }
                },this::lostPassError);
    }


    private void lostPassError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
        mView.getTimeButton().TimeOnCancel();
    }
}
