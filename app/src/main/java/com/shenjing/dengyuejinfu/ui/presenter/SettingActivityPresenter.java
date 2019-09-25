package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.entity.LoginOutBean;
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
                .compose(mView.<LoginOutBean>bindToLife())
                .compose(RxSchedulers.<LoginOutBean>applySchedulers())
                .subscribe(new Consumer<LoginOutBean>() {
                    @Override
                    public void accept(LoginOutBean loginOutBean) throws Exception {
                        mView.hideLoading();
                        if (loginOutBean.getCode() != null && loginOutBean.getCode().equals("0000")) {
                            mView.showSuccess(loginOutBean.getMsg());
                            mView.LoginOutSuccess();
                        } else {
                            mView.showFail(loginOutBean.getMsg());
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
