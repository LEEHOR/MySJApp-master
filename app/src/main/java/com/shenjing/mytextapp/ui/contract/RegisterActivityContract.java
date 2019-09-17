package com.shenjing.mytextapp.ui.contract;

import com.shenjing.mytextapp.base.BaseContract;
import com.shenjing.mytextapp.entity.AccountLoginBean;
import com.shenjing.mytextapp.entity.UserRegisterBean;
import com.shenjing.mytextapp.respondModule.LoginModule;
import com.shenjing.mytextapp.respondModule.RegisterModule;

import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class RegisterActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        /**
         * 注册
         * @param registerModule
         */
        void  registerSuccess(RegisterModule registerModule);

        void  registerFailure(String failure);

        /**
         * 登录
         */
        void  loginSuccess(LoginModule loginModule);

        void  loginFailure(String msg);

    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

       void register(Map<String,Object> map);

       void login_account(Map<String,Object> map);

    }
}
