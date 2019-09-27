package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.ShareApi;
import com.shenjing.dengyuejinfu.entity.QRBean;
import com.shenjing.dengyuejinfu.ui.contract.QRActivityContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class QRActivityPresenter extends BasePresenter<QRActivityContract.View>
        implements QRActivityContract.Presenter {
    @Inject
    public QRActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getQRCode(String userId) {
        mView.showLoading();
        RetrofitManager.create(ShareApi.class).getCode(Long.parseLong(userId))
                .compose(mView.bindToLife())
                .compose(RxSchedulers.applySchedulers())
                .subscribe(new Consumer<QRBean>() {
                    @Override
                    public void accept(QRBean qrBean) {
                        if (qrBean.getCode() != null && qrBean.getCode().equals("0000")) {
                            mView.showSuccess(qrBean.getMsg());
                            mView.getSuccess(qrBean);
                            mView.isCanShare(true);
                        }else {
                            mView.showFail(qrBean.getMsg());
                            mView.getFailure();
                            mView.isCanShare(false);
                        }
                        mView.hideLoading();
                    }
                },this::LoadingError);
    }

    private void LoadingError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        mView.isCanShare(false);
        ToastUtils.showShort("加载错误..");
    }
}
