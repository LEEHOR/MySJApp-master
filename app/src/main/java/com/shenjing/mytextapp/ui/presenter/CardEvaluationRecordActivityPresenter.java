package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.ui.contract.CardEvaluationRecordActivityContract;
import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class CardEvaluationRecordActivityPresenter extends BasePresenter<CardEvaluationRecordActivityContract.View>
        implements CardEvaluationRecordActivityContract.Presenter {

    private Context mContext;

    @Inject
    public CardEvaluationRecordActivityPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

}
