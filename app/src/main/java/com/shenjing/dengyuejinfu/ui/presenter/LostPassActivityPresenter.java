package com.shenjing.dengyuejinfu.ui.presenter;
import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.entity.LostPassBean;
import com.shenjing.dengyuejinfu.entity.SmsBean;
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
                .compose(mView.<LostPassBean>bindToLife())
                .compose(RxSchedulers.<LostPassBean>applySchedulers())
                .subscribe(new Consumer<LostPassBean>() {
                    @Override
                    public void accept(LostPassBean lostPassBean) {
                        mView.hideLoading();
                        if (lostPassBean.getCode() != null && lostPassBean.getCode().equals("0000")) {
                            mView.showSuccess(lostPassBean.getMsg());
                            mView.getTimeButton().TimeOnFinished();
                            mView.getPassSuccess();
                        } else {
                            mView.showFail(lostPassBean.getMsg());
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
                .compose(mView.<SmsBean>bindToLife())
                .compose(RxSchedulers.<SmsBean>applySchedulers())
                .subscribe(new Consumer<SmsBean>() {
                    @Override
                    public void accept(SmsBean smsBean) {
                        mView.hideLoading();
                        if (smsBean.getCode() != null && smsBean.getCode().equals("0000")) {

                            mView.showSuccess(smsBean.getMsg());
                            mView.getTimeButton().TimeOnFinished();
                            mView.getSmsSuccess();
                        } else {
                            mView.showFail(smsBean.getMsg());

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
