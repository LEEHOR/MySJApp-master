package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.ui.activity.adapter.IncreaseQuotaAdapter;
import com.shenjing.dengyuejinfu.ui.activity.adapter.IncreaseQuotaInformationAdapter;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class IncreaseTheQuotaInformationActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        IncreaseQuotaInformationAdapter getAdapter();

        SwipeRefreshLayout getSwipe();

        RecyclerView getRecycler();

        void isCanRefresh(boolean refresh);


    }

    /**
     * 逻辑
     */
    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getInformation(String userId,String type,int page);

        void refresh();

        void loadMore();


    }
}
