package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.IndexFragmentApi;
import com.shenjing.dengyuejinfu.respondModule.BannerModel;
import com.shenjing.dengyuejinfu.ui.contract.IndexFragmentContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class IndexFragmentPresenter extends BasePresenter<IndexFragmentContract.View>
        implements IndexFragmentContract.Presenter {

    @Inject
    public IndexFragmentPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void Banner() {
        mView.showLoading();
       // mView.Refresh(false);
        RetrofitManager.create(IndexFragmentApi.class).getBanner()
                .compose(mView.<BannerModel>bindToLife())
                .compose(RxSchedulers.<BannerModel>applySchedulers())
                .subscribe(new Consumer<BannerModel>() {
                    @Override
                    public void accept(BannerModel bannerModel) {
                        mView.hideLoading();
                        if (bannerModel.getCode() != null && bannerModel.getCode().equals("0000")) {
                            mView.showSuccess(bannerModel.getMsg());
                            mView.Refresh(false);
                            mView.getBannerSuccess(bannerModel);

                        } else {
                            mView.showFail(bannerModel.getMsg());
                            mView.Refresh(false);
                            mView.getBannerFailure();

                        }
                    }
                },this::lostPassError);
    }

    private void lostPassError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
        mView.Refresh(false);
    }

}
