package com.shenjing.dengyuejinfu.ui.contract;

import com.amap.api.location.AMapLocation;
import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.LoginBean;
import com.shenjing.dengyuejinfu.widgte.TimingButton;

import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class LoginActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        /**
         * 登陆成功
         * @param loginBean
         * @param type //登录方式
         */
        void showLoginSuccess(LoginBean loginBean, int type);

        /** 登陆失败
         *
         * @param loginBean
         * @param type    //登录方式
         */
        void  shownLoginFailure(LoginBean loginBean, int type);

        /**
         * 获取验证码成功
         * @param sms
         */
        void showSmsSuccess(String sms);

        /**
         * 获取验证码失败
         */
        void shownSmsFailure(String msg);


        /**
         * 定位
         * @param aMapLocation
         */
        void LocationSuccess(AMapLocation aMapLocation);

        void LocationFailure(int errorCode);

        TimingButton getTimeButtonView();
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 账户密码登录
         */
        void LoginAccount(Map<String,Object> map);

        /**
         * 手机号验证码登录
         */
        void LoginPhone(Map<String,Object> map);

        /**
         * 获取验证码
         * @param phone
         */
        void loginSms(String phone);

        /**
         * 开启定位
         */
        void startLocation();

        void closeLocation();

        /**
         * 上传登录日志
         * @param map
         */
        void uploadUserInfo(Map<String,Object> map);
    }
}
