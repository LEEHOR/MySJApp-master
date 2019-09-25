package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.LoginBean;
import com.shenjing.dengyuejinfu.entity.RegisterBean;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.ui.contract.RegisterActivityContract;
import com.shenjing.dengyuejinfu.utils.GaodeMapLocationHelper;

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


    private GaodeMapLocationHelper.MyLocationListener locationListener;

    @Inject
    public RegisterActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void register(Map<String,Object> map) {
        mView.showLoading();
       RetrofitManager.create(UserApi.class).register(map)
               .compose(mView.<RegisterBean>bindToLife())
               .compose(RxSchedulers.<RegisterBean>applySchedulers())
               .subscribe(new Consumer<RegisterBean>() {
                   @Override
                   public void accept(RegisterBean registerBean) throws Exception {
                                  mView.hideLoading();
                       if (registerBean.getCode() != null && registerBean.getCode().equals("0000")) {
                           mView.showSuccess(registerBean.getMsg());
                           mView.registerSuccess(registerBean);
                       } else {
                           mView.showFail(registerBean.getMsg());
                           mView.registerFailure(registerBean.getMsg());
                       }
                   }
               },this::loadError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void login_account(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).login_account(map)
                .compose(mView.<LoginBean>bindToLife())
                .compose(RxSchedulers.<LoginBean>applySchedulers())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        mView.hideLoading();
                        if (loginBean.getCode() != null && loginBean.getCode().equals("0000")) {
                            mView.loginSuccess(loginBean);
                        } else {
                            mView.loginFailure(loginBean.getMsg());
                        }
                    }
                },this::loadError);
    }

    @Override
    public void startLocation() {
        GaodeMapLocationHelper.startLocation();
        locationListener = new GaodeMapLocationHelper.MyLocationListener() {
            @Override
            public void onLocationSuccess(AMapLocation location) {
                Log.d("定位", location.getAddress());
                mView.LocationSuccess(location);
            }

            @Override
            public void onLocationFailure(int locType) {
                mView.LocationFailure(locType);
                ToastUtils.showLong("定位失败"+locType);
            }
        };
        GaodeMapLocationHelper.registerLocationCallback(locationListener);
    }

    @Override
    public void closeLocation() {
        GaodeMapLocationHelper.closeLocation();
        GaodeMapLocationHelper.unRegisterLocationCallback(locationListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadUserInfo(Map<String, Object> map) {
        RetrofitManager.create(UserApi.class).uploadDeviceLocation(map)
                .compose(mView.<BaseBean>bindToLife())
                .compose(RxSchedulers.<BaseBean>applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean){
                        if (baseBean.getCode() != null && baseBean.getCode().equals("0000")) {

                        } else {

                        }
                    }
                },this::loadUserInfo);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
    }

    private void loadUserInfo(Throwable throwable) {
        throwable.printStackTrace();
        LogUtils.e(throwable.getMessage());
    }
}
