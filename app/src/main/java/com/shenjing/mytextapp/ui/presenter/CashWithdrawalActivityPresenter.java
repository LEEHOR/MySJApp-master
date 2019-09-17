package com.shenjing.mytextapp.ui.presenter;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.ui.contract.CashWithdrawalActivityContract;
import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class CashWithdrawalActivityPresenter extends BasePresenter<CashWithdrawalActivityContract.View>
        implements CashWithdrawalActivityContract.Presenter{

    @Inject
    public CashWithdrawalActivityPresenter() {

    }

}
