package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.ui.activity.adapter.BankListAdapter;
import com.shenjing.dengyuejinfu.ui.activity.adapter.CardListAdapter;

/**
 * author : Leehor
 * date   : 2019/9/3010:30
 * version: 1.0
 * desc   :
 */
public class CardListActivityContract {
    public interface View extends BaseContract.BaseView {

        void getCardListSuccess();

        void getCardListFailure();

        RecyclerView getRecycler();

        CardListAdapter getAdapter();

        void isCanRefresh(boolean refresh);
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {

        void getCardList(int bankId);

    }
}
