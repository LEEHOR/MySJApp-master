package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.BannerBean;
import com.shenjing.dengyuejinfu.ui.activity.adapter.IncreaseQuotaAdapter;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class IncreaseTheQuotaActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {
        /**
         * 底部recycler数据
         */
        void getDataSuccess();

        void getDataFailure();

        IncreaseQuotaAdapter getAdapter();

        RecyclerView getRecycler();

        void isCanRefresh(boolean refresh);

        /**
         * banner
         */
        void getBannerSuccess(BannerBean bannerBean);

        void getBannerFailure();

    }

    /**
     * 逻辑
     */
    public interface Presenter extends BaseContract.BasePresenter<View> {


        void getIncreaseBanner(String actionScope);

        void getIncreaseData();

        void Refresh();


    }
}
