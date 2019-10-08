package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.ui.activity.adapter.BankListAdapter;
import com.shenjing.dengyuejinfu.ui.activity.adapter.LoanListAdapter;

/**
 * author : Leehor
 * date   : 2019/9/3010:30
 * version: 1.0
 * desc   :
 */
public class LoanListActivityContract {
    public interface View extends BaseContract.BaseView {

        void getLoanListSuccess(String imageUrl);

        void getLoanListFailure();

        RecyclerView getRecycler();

        LoanListAdapter getAdapter();

        void isCanRefresh(boolean refresh);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getLoanList();

    }
}
