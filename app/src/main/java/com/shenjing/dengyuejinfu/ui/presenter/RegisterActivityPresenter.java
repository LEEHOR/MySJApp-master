package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.respondModule.RegisterModel;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.ui.contract.RegisterActivityContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class RegisterActivityPresenter extends BasePresenter<RegisterActivityContract.View>
        implements RegisterActivityContract.Presenter {


    @Inject
    public RegisterActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void register(Map<String,Object> map) {
        mView.showLoading();
       RetrofitManager.create(UserApi.class).register(map)
               .compose(mView.<RegisterModel>bindToLife())
               .compose(RxSchedulers.<RegisterModel>applySchedulers())
               .subscribe(new Consumer<RegisterModel>() {
                   @Override
                   public void accept(RegisterModel registerModel) throws Exception {
                                  mView.hideLoading();
                       if (registerModel.getCode() != null && registerModel.getCode().equals("0000")) {
                           mView.showSuccess(registerModel.getMsg());
                           mView.registerSuccess(registerModel);
                       } else {
                           mView.showFail(registerModel.getMsg());
                           mView.registerFailure(registerModel.getMsg());
                       }
                   }
               },this::loadError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void login_account(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).login_account(map)
                .compose(mView.<LoginModel>bindToLife())
                .compose(RxSchedulers.<LoginModel>applySchedulers())
                .subscribe(new Consumer<LoginModel>() {
                    @Override
                    public void accept(LoginModel loginModel) throws Exception {
                        mView.hideLoading();
                        if (loginModel.getCode() != null && loginModel.getCode().equals("0000")) {
                            mView.loginSuccess(loginModel);
                        } else {
                            mView.loginFailure(loginModel.getMsg());
                        }
                    }
                },this::loadError);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
    }
}
