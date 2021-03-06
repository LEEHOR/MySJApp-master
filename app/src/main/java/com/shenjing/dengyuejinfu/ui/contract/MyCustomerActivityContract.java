package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.MyCustomerBean;
import com.shenjing.dengyuejinfu.ui.activity.adapter.MyCustomerAdapter;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class MyCustomerActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        void  getCustomerSuccess();

        void  getCustomerFailure();

        void isCanRefresh(boolean refresh);

        MyCustomerAdapter getAdapter();

        RecyclerView getRecycler();
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{
        void getCustomer(long userId);
    }
}
