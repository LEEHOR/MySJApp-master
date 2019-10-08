package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.common.Constant;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.net.services.IndexFragmentApi;
import com.shenjing.dengyuejinfu.net.services.TestApi;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.entity.BannerBean;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.VersionBean;
import com.shenjing.dengyuejinfu.ui.contract.IndexFragmentContract;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
                .compose(mView.<BannerBean>bindToLife())
                .compose(RxSchedulers.<BannerBean>applySchedulers())
                .subscribe(new Consumer<BannerBean>() {
                    @Override
                    public void accept(BannerBean bannerBean) {
                        mView.hideLoading();
                        if (bannerBean.getCode() != null && bannerBean.getCode().equals("0000")) {
                            mView.showSuccess(bannerBean.getMsg());
                            mView.Refresh(false);
                            mView.getBannerSuccess(bannerBean);

                        } else {
                            mView.showFail(bannerBean.getMsg());
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
                .compose(mView.<BaseBean>bindToLife())
                .compose(RxSchedulers.<BaseBean>applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) {
                        if (baseBean.getCode() != null && baseBean.getCode().equals("0000")) {
                            mView.showSuccess(baseBean.getMsg());
                           // mView.Refresh(false);
                          //  mView.getBannerSuccess(bannerModel);

                        } else {
                            mView.showFail(baseBean.getMsg());
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
                .compose(mView.<VersionBean>bindToLife())
                .compose(RxSchedulers.<VersionBean>applySchedulers())
                .subscribe(new Consumer<VersionBean>() {
                    @Override
                    public void accept(VersionBean versionBean) {
                       // mView.hideLoading();
                        if (versionBean.getCode() != null && versionBean.getCode().equals("0000")) {
                            mView.showSuccess(versionBean.getMsg());
                            mView.getVersionSuccess(versionBean);
                        } else {
                            mView.showFail(versionBean.getMsg());
                            mView.getVersionFailure();
                        }
                    }
                },this::checkVersionError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void testApi() {
        RetrofitManager.create(TestApi.class).testNet()
                .compose(mView.<BaseBean>bindToLife())
                .compose(RxSchedulers.<BaseBean>applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) {

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
