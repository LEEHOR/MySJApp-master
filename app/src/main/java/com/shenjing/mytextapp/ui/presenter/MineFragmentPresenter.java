package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.ui.contract.MineFragmentContract;

import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class MineFragmentPresenter extends BasePresenter<MineFragmentContract.View> implements MineFragmentContract.Presenter {

    private Context mContext;

    @Inject
    public MineFragmentPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

}
