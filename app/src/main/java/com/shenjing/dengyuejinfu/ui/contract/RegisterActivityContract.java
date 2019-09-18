package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.respondModule.RegisterModel;

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
         * @param registerModel
         */
        void  registerSuccess(RegisterModel registerModel);

        void  registerFailure(String failure);

        /**
         * 登录
         */
        void  loginSuccess(LoginModel loginModel);

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
