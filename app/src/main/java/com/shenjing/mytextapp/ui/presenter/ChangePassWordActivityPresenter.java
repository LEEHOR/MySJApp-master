package com.shenjing.mytextapp.ui.presenter;
import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.entity.ChangePassBean;
import com.shenjing.mytextapp.net.RetrofitManager;
import com.shenjing.mytextapp.net.RxSchedulers;
import com.shenjing.mytextapp.net.services.UserApi;
import com.shenjing.mytextapp.respondModule.ChangePassModule;
import com.shenjing.mytextapp.respondModule.RegisterModule;
import com.shenjing.mytextapp.ui.contract.ChangePassWordActivityContract;

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
                .compose(mView.<ChangePassModule>bindToLife())
                .compose(RxSchedulers.<ChangePassModule>applySchedulers())
                .subscribe(new Consumer<ChangePassModule>() {
                    @Override
                    public void accept(ChangePassModule changePassModule) throws Exception {
                        mView.hideLoading();
                        if (changePassModule.getCode() != null && changePassModule.getCode().equals("0000")) {
                            mView.showSuccess(changePassModule.getMsg());
                            mView.showSuccess(changePassModule);
                        } else {
                            mView.showFail(changePassModule.getMsg());
                            mView.showFailure(changePassModule.getMsg());
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
