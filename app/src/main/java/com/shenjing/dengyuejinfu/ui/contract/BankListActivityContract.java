package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.ui.activity.adapter.BankListAdapter;

/**
 * author : Leehor
 * date   : 2019/9/3010:30
 * version: 1.0
 * desc   :
 */
public class BankListActivityContract {
    public interface View extends BaseContract.BaseView {

        void getBankListSuccess(String imageUrl);

        void getBankListFailure();

        RecyclerView getRecycler();

        BankListAdapter getAdapter();

        void isCanRefresh(boolean refresh);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getBankList();

    }
}
