package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.respondModule.CreditCardListModel;
import com.shenjing.dengyuejinfu.ui.activity.adapter.CreditficationCardListAdapter;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class CreditCardCertificationActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        void getCardListSuccess(CreditCardListModel creditCardListModel);

        void getCardListFailure();

        void setBankStatusSuccess();

        void setBankStatusFailure();

        void Refresh(boolean Refresh);

        CreditficationCardListAdapter getViewListAdapter();
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 获取银行卡列表
         * @param userId
         */
       void getCardList(String userId);

        /**
         * 设置银行卡状态
         * @param userId
         * @param bankNo
         */
       void setCardStatus(String userId,String bankNo);

    }
}
