package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
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
         * @param loginModel
         * @param type //登录方式
         */
        void showLoginSuccess(LoginModel loginModel, int type);

        /** 登陆失败
         *
         * @param loginModel
         * @param type    //登录方式
         */
        void  shownLoginFailure(LoginModel loginModel, int type);

        /**
         * 获取验证码成功
         * @param sms
         */
        void showSmsSuccess(String sms);

        /**
         * 获取验证码失败
         */
        void shownSmsFailure(String msg);

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
    }
}
