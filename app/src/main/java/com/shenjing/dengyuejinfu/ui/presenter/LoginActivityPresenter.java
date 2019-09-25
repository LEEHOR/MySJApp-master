package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.LoginBean;
import com.shenjing.dengyuejinfu.entity.SmsBean;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.ui.contract.LoginActivityContract;
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
public class LoginActivityPresenter extends BasePresenter<LoginActivityContract.View>
        implements LoginActivityContract.Presenter{

    private GaodeMapLocationHelper.MyLocationListener locationListener;

    @Inject
    public LoginActivityPresenter() {

    }


    @SuppressLint("CheckResult")
    @Override
    public void LoginAccount(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).login_account(map)
                .compose(mView.<LoginBean>bindToLife())
                .compose(RxSchedulers.<LoginBean>applySchedulers())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean){
                        mView.hideLoading();
                        if (loginBean.getCode() != null && loginBean.getCode().equals("0000")) {
                            mView.showSuccess(loginBean.getMsg());
                            mView.showLoginSuccess(loginBean,1);
                        } else {
                            mView.showFail(loginBean.getMsg());
                            mView.shownLoginFailure(loginBean,1);
                        }
                    }
                },this::loadError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void LoginPhone(Map<String,Object> map) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).login_phone(map)
                .compose(mView.<LoginBean>bindToLife())
                .compose(RxSchedulers.<LoginBean>applySchedulers())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean)  {
                        mView.hideLoading();
                        if (loginBean.getCode() != null && loginBean.getCode().equals("0000")) {
                            mView.showSuccess(loginBean.getMsg());
                            mView.showLoginSuccess(loginBean,2);
                        } else {
                            mView.showFail(loginBean.getMsg());
                            mView.getTimeButtonView().TimeOnFinished();
                            mView.shownLoginFailure(loginBean,2);
                        }
                    }
                },this::loadPhoneLoginError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void loginSms(String phone) {
        mView.showLoading();
        RetrofitManager.create(UserApi.class).get_sms(phone)
                .compose(mView.<SmsBean>bindToLife())
                .compose(RxSchedulers.<SmsBean>applySchedulers())
                .subscribe(new Consumer<SmsBean>() {
                    @Override
                    public void accept(SmsBean smsBean){
                        mView.hideLoading();
                        if (smsBean.getCode() != null && smsBean.getCode().equals("0000")) {
                            mView.showSuccess(smsBean.getMsg());
                            mView.showSmsSuccess("");
                        } else {
                            mView.showFail(smsBean.getMsg());
                            mView.getTimeButtonView().TimeOnFinished();
                            mView.shownSmsFailure("");
                        }
                    }
                },this::loadSmsError);
    }

    @Override
    public void startLocation() {
        GaodeMapLocationHelper.startLocation();
        locationListener = new GaodeMapLocationHelper.MyLocationListener() {
            @Override
            public void onLocationSuccess(AMapLocation location) {
                Log.d("定位", location.getAddress());
                mView.LocationSuccess(location);
                //GaodeMapLocationHelper.closeLocation();
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
        ToastUtils.showShort("加载错误..");
    }

    private void loadSmsError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.getTimeButtonView().TimeOnFinished();
    }


    private void loadPhoneLoginError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.getTimeButtonView().TimeOnFinished();
    }

    private void loadUserInfo(Throwable throwable) {
        throwable.printStackTrace();
        LogUtils.e(throwable.getMessage());
    }
}
