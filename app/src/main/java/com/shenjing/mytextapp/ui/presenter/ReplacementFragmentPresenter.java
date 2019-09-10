package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.ui.contract.ReplacementFragmentContract;

import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class ReplacementFragmentPresenter extends BasePresenter<ReplacementFragmentContract.View> implements ReplacementFragmentContract.Presenter {

    private Context mContext;

    @Inject
    public ReplacementFragmentPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

}
