package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class ShareDialogFragmentContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        void  getShareSuccess();

        void getShareFailure();

    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

       void getShareInfo(String userId);

    }
}
