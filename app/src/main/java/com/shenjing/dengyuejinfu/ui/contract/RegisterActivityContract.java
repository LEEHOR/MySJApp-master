package com.shenjing.dengyuejinfu.ui.contract;

import com.amap.api.location.AMapLocation;
import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.LoginBean;
import com.shenjing.dengyuejinfu.entity.RegisterBean;

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
         * @param registerBean
         */
        void  registerSuccess(RegisterBean registerBean);

        void  registerFailure(String failure);

        /**
         * 登录
         */
        void  loginSuccess(LoginBean loginBean);

        void  loginFailure(String msg);

        /**
         * 定位
         * @param aMapLocation
         */
        void LocationSuccess(AMapLocation aMapLocation);

        void LocationFailure(int errorCode);

    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

       void register(Map<String,Object> map);

       void login_account(Map<String,Object> map);

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
