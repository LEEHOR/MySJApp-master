package com.shenjing.mytextapp.ui.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.net.RetrofitManager;
import com.shenjing.mytextapp.net.RxSchedulers;
import com.shenjing.mytextapp.net.services.UserApi;
import com.shenjing.mytextapp.respondModule.LoginOutModule;
import com.shenjing.mytextapp.respondModule.RegisterModule;
import com.shenjing.mytextapp.ui.contract.SettingActivityContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class SettingActivityPresenter extends BasePresenter<SettingActivityContract.View>
        implements SettingActivityContract.Presenter {


    @Inject
    public SettingActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loginOut(String userId) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).loginOut(userId)
                .compose(mView.<LoginOutModule>bindToLife())
                .compose(RxSchedulers.<LoginOutModule>applySchedulers())
                .subscribe(new Consumer<LoginOutModule>() {
                    @Override
                    public void accept(LoginOutModule loginOutModule) throws Exception {
                        mView.hideLoading();
                        if (loginOutModule.getCode() != null && loginOutModule.getCode().equals("0000")) {
                            mView.showSuccess(loginOutModule.getMsg());
                            mView.LoginOutSuccess();
                        } else {
                            mView.showFail(loginOutModule.getMsg());
                            mView.LoginOutFailure();
                        }
                    }
                }, this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
    }
}
