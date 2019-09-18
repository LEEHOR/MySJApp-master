package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.widgte.TimingButton;

import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class LostPassActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {
        void getSmsSuccess();
        void getSmsFailure();

        void getPassSuccess();
        void getPassFailure();

        TimingButton getTimeButton();

    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

       void lostPassSubmit(Map<String,Object> map);

       void sendSms(String phone);
    }
}
