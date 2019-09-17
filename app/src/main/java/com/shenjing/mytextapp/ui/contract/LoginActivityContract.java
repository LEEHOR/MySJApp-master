package com.shenjing.mytextapp.ui.contract;

import com.shenjing.mytextapp.base.BaseContract;
import com.shenjing.mytextapp.entity.AccountLoginBean;
import com.shenjing.mytextapp.entity.PhoneLoginBean;
import com.shenjing.mytextapp.respondModule.LoginModule;
import com.shenjing.mytextapp.widgte.TimingButton;

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
         * @param loginModule
         * @param type //登录方式
         */
        void showLoginSuccess(LoginModule loginModule,int type);

        /** 登陆失败
         *
         * @param loginModule
         * @param type    //登录方式
         */
        void  shownLoginFailure(LoginModule loginModule,int type);

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
