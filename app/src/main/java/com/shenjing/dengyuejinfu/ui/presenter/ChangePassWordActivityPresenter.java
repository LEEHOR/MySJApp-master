package com.shenjing.dengyuejinfu.ui.presenter;
import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.respondModule.ChangePassModel;
import com.shenjing.dengyuejinfu.ui.contract.ChangePassWordActivityContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class ChangePassWordActivityPresenter extends BasePresenter<ChangePassWordActivityContract.View>
        implements ChangePassWordActivityContract.Presenter {



    @Inject
    public ChangePassWordActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void changePassWord(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).changePass(map)
                .compose(mView.<ChangePassModel>bindToLife())
                .compose(RxSchedulers.<ChangePassModel>applySchedulers())
                .subscribe(new Consumer<ChangePassModel>() {
                    @Override
                    public void accept(ChangePassModel changePassModel) throws Exception {
                        mView.hideLoading();
                        if (changePassModel.getCode() != null && changePassModel.getCode().equals("0000")) {
                            mView.showSuccess(changePassModel.getMsg());
                            mView.showSuccess(changePassModel);
                        } else {
                            mView.showFail(changePassModel.getMsg());
                            mView.showFailure(changePassModel.getMsg());
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
