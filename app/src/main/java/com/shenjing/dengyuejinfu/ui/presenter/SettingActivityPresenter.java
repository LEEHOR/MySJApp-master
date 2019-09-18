package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.respondModule.LoginOutModel;
import com.shenjing.dengyuejinfu.ui.contract.SettingActivityContract;

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
                .compose(mView.<LoginOutModel>bindToLife())
                .compose(RxSchedulers.<LoginOutModel>applySchedulers())
                .subscribe(new Consumer<LoginOutModel>() {
                    @Override
                    public void accept(LoginOutModel loginOutModel) throws Exception {
                        mView.hideLoading();
                        if (loginOutModel.getCode() != null && loginOutModel.getCode().equals("0000")) {
                            mView.showSuccess(loginOutModel.getMsg());
                            mView.LoginOutSuccess();
                        } else {
                            mView.showFail(loginOutModel.getMsg());
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
