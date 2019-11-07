package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.ui.fragment.adapter.CustomerDetailAdapter;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class ViewPagerCustomerDetailContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {
        void  getCustomerDetailSuccess();

        void  getCustomerDetailFailure();

        void isCanRefresh(boolean refresh);

        CustomerDetailAdapter getAdapter();

        RecyclerView getRecycler();
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

       void getCustomDetail(String isString,String status);
    }
}
