package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.ui.contract.BankCardCertificationActivityContract;
import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class BankCardCertificationActivityPresenter extends BasePresenter<BankCardCertificationActivityContract.View>
        implements BankCardCertificationActivityContract.Presenter {
    @Inject
    public BankCardCertificationActivityPresenter() {
    }

}
