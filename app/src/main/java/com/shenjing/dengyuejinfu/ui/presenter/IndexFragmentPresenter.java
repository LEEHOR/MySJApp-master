package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.IndexFragmentApi;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.respondModule.BannerModel;
import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.VersionModel;
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
    public void Banner(String actionScope) {
        mView.showLoading();
       // mView.Refresh(false);
        RetrofitManager.create(IndexFragmentApi.class).getBanner(actionScope)
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

    @SuppressLint("CheckResult")
    @Override
    public void uploadCallRecord(String userId, String contacts) {
        RetrofitManager.create(UserApi.class).uploadCallRecord(Long.parseLong(userId),contacts)
                .compose(mView.<BaseModel>bindToLife())
                .compose(RxSchedulers.<BaseModel>applySchedulers())
                .subscribe(new Consumer<BaseModel>() {
                    @Override
                    public void accept(BaseModel baseModel) {
                        if (baseModel.getCode() != null && baseModel.getCode().equals("0000")) {
                            mView.showSuccess(baseModel.getMsg());
                           // mView.Refresh(false);
                          //  mView.getBannerSuccess(bannerModel);

                        } else {
                            mView.showFail(baseModel.getMsg());
                         //   mView.Refresh(false);
                          //  mView.getBannerFailure();

                        }
                    }
                },this::CallError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getVersion() {
        RetrofitManager.create(UserApi.class).checkVersion()
                .compose(mView.<VersionModel>bindToLife())
                .compose(RxSchedulers.<VersionModel>applySchedulers())
                .subscribe(new Consumer<VersionModel>() {
                    @Override
                    public void accept(VersionModel versionModel) {
                       // mView.hideLoading();
                        if (versionModel.getCode() != null && versionModel.getCode().equals("0000")) {
                            mView.showSuccess(versionModel.getMsg());
                            mView.getVersionSuccess(versionModel);
                        } else {
                            mView.showFail(versionModel.getMsg());
                            mView.getVersionFailure();
                        }
                    }
                },this::checkVersionError);
    }

    private void lostPassError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
        mView.Refresh(false);
    }

    private void CallError(Throwable throwable) {
        throwable.printStackTrace();
        ToastUtils.showShort("加载错误");
    }
    private void checkVersionError(Throwable throwable) {
        throwable.printStackTrace();
        ToastUtils.showShort("加载错误");
    }
}
