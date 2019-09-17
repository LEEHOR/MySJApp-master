package com.shenjing.mytextapp.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.net.RetrofitManager;
import com.shenjing.mytextapp.net.RxSchedulers;
import com.shenjing.mytextapp.net.services.IndexFragmentApi;
import com.shenjing.mytextapp.respondModule.BannerModule;
import com.shenjing.mytextapp.ui.contract.IndexFragmentContract;

import java.util.Map;

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
                .compose(mView.<BannerModule>bindToLife())
                .compose(RxSchedulers.<BannerModule>applySchedulers())
                .subscribe(new Consumer<BannerModule>() {
                    @Override
                    public void accept(BannerModule bannerModule) {
                        mView.hideLoading();
                        if (bannerModule.getCode() != null && bannerModule.getCode().equals("0000")) {
                            mView.showSuccess(bannerModule.getMsg());
                            mView.Refresh(false);
                            mView.getBannerSuccess(bannerModule);

                        } else {
                            mView.showFail(bannerModule.getMsg());
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
