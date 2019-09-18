package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.respondModule.SmsModel;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.ui.contract.LoginActivityContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class LoginActivityPresenter extends BasePresenter<LoginActivityContract.View>
        implements LoginActivityContract.Presenter{
    @Inject
    public LoginActivityPresenter() {

    }


    @SuppressLint("CheckResult")
    @Override
    public void LoginAccount(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).login_account(map)
                .compose(mView.<LoginModel>bindToLife())
                .compose(RxSchedulers.<LoginModel>applySchedulers())
                .subscribe(new Consumer<LoginModel>() {
                    @Override
                    public void accept(LoginModel loginModel){
                        mView.hideLoading();
                        if (loginModel.getCode() != null && loginModel.getCode().equals("0000")) {
                            mView.showSuccess(loginModel.getMsg());
                            mView.showLoginSuccess(loginModel,1);
                        } else {
                            mView.showFail(loginModel.getMsg());
                            mView.shownLoginFailure(loginModel,1);
                        }
                    }
                },this::loadError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void LoginPhone(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).login_phone(map)
                .compose(mView.<LoginModel>bindToLife())
                .compose(RxSchedulers.<LoginModel>applySchedulers())
                .subscribe(new Consumer<LoginModel>() {
                    @Override
                    public void accept(LoginModel loginModel)  {
                        mView.hideLoading();
                        if (loginModel.getCode() != null && loginModel.getCode().equals("0000")) {
                            mView.showSuccess(loginModel.getMsg());
                            mView.showLoginSuccess(loginModel,2);
                        } else {
                            mView.showFail(loginModel.getMsg());
                            mView.getTimeButtonView().TimeOnFinished();
                            mView.shownLoginFailure(loginModel,2);
                        }
                    }
                },this::loadPhoneLoginError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loginSms(String phone) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).get_sms(phone)
                .compose(mView.<SmsModel>bindToLife())
                .compose(RxSchedulers.<SmsModel>applySchedulers())
                .subscribe(new Consumer<SmsModel>() {
                    @Override
                    public void accept(SmsModel smsModel){
                        mView.hideLoading();
                        if (smsModel.getCode() != null && smsModel.getCode().equals("0000")) {
                            mView.showSuccess(smsModel.getMsg());
                            mView.showSmsSuccess("");
                        } else {
                            mView.showFail(smsModel.getMsg());
                            mView.getTimeButtonView().TimeOnFinished();
                            mView.shownSmsFailure("");
                        }
                    }
                },this::loadSmsError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
    }

    private void loadSmsError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.getTimeButtonView().TimeOnFinished();
    }


    private void loadPhoneLoginError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.getTimeButtonView().TimeOnFinished();
    }
}
