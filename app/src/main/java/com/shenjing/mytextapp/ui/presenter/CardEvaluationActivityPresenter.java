package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.ui.contract.CardEvaluationActivityContract;

import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class CardEvaluationActivityPresenter extends BasePresenter<CardEvaluationActivityContract.View>
        implements CardEvaluationActivityContract.Presenter {
    @Inject
    public CardEvaluationActivityPresenter() {

    }

}
