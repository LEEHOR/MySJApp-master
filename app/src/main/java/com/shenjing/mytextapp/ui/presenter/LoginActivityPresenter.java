package com.shenjing.mytextapp.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.entity.AccountLoginBean;
import com.shenjing.mytextapp.entity.PhoneLoginBean;
import com.shenjing.mytextapp.respondModule.LoginModule;
import com.shenjing.mytextapp.respondModule.SmsModule;
import com.shenjing.mytextapp.net.RetrofitManager;
import com.shenjing.mytextapp.net.RxSchedulers;
import com.shenjing.mytextapp.net.services.UserApi;
import com.shenjing.mytextapp.ui.contract.LoginActivityContract;

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
                .compose(mView.<LoginModule>bindToLife())
                .compose(RxSchedulers.<LoginModule>applySchedulers())
                .subscribe(new Consumer<LoginModule>() {
                    @Override
                    public void accept(LoginModule loginModule){
                        mView.hideLoading();
                        if (loginModule.getCode() != null && loginModule.getCode().equals("0000")) {
                            mView.showSuccess(loginModule.getMsg());
                            mView.showLoginSuccess(loginModule,1);
                        } else {
                            mView.showFail(loginModule.getMsg());
                            mView.shownLoginFailure(loginModule,1);
                        }
                    }
                },this::loadError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void LoginPhone(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).login_phone(map)
                .compose(mView.<LoginModule>bindToLife())
                .compose(RxSchedulers.<LoginModule>applySchedulers())
                .subscribe(new Consumer<LoginModule>() {
                    @Override
                    public void accept(LoginModule loginModule)  {
                        mView.hideLoading();
                        if (loginModule.getCode() != null && loginModule.getCode().equals("0000")) {
                            mView.showSuccess(loginModule.getMsg());
                            mView.showLoginSuccess(loginModule,2);
                        } else {
                            mView.showFail(loginModule.getMsg());
                            mView.getTimeButtonView().TimeOnFinished();
                            mView.shownLoginFailure(loginModule,2);
                        }
                    }
                },this::loadPhoneLoginError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loginSms(String phone) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).get_sms(phone)
                .compose(mView.<SmsModule>bindToLife())
                .compose(RxSchedulers.<SmsModule>applySchedulers())
                .subscribe(new Consumer<SmsModule>() {
                    @Override
                    public void accept(SmsModule smsModule){
                        mView.hideLoading();
                        if (smsModule.getCode() != null && smsModule.getCode().equals("0000")) {
                            mView.showSuccess(smsModule.getMsg());
                            mView.showSmsSuccess("");
                        } else {
                            mView.showFail(smsModule.getMsg());
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
