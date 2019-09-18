package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class AddCreditCardActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        void uploadInfoSuccess();

        void uploadInfoFailure();
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

        /**
         * 信用卡认证
         * @param map
         */
       void uploadCreditCard(Map<String,Object> map);

    }
}
