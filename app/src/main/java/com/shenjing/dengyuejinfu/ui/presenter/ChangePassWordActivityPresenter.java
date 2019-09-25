package com.shenjing.dengyuejinfu.ui.presenter;
import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.entity.ChangePassBean;
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
                .compose(mView.<ChangePassBean>bindToLife())
                .compose(RxSchedulers.<ChangePassBean>applySchedulers())
                .subscribe(new Consumer<ChangePassBean>() {
                    @Override
                    public void accept(ChangePassBean changePassBean) throws Exception {
                        mView.hideLoading();
                        if (changePassBean.getCode() != null && changePassBean.getCode().equals("0000")) {
                            mView.showSuccess(changePassBean.getMsg());
                            mView.showSuccess(changePassBean);
                        } else {
                            mView.showFail(changePassBean.getMsg());
                            mView.showFailure(changePassBean.getMsg());
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
