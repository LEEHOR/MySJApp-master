package com.shenjing.mytextapp.ui.contract;

import com.shenjing.mytextapp.base.BaseContract;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class CashWithdrawalActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

    }
}
