package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.QRBean;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class QRActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {
        void getSuccess(QRBean qrBean);
        void getFailure();

        /**
         * 是否可以分享
         * @param isCanShare
         */
        void isCanShare(boolean isCanShare);

    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{


        void getQRCode(String userId);
    }
}
