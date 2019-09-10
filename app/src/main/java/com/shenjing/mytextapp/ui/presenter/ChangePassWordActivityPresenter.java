package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.ui.contract.ChangePassWordActivityContract;
import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class ChangePassWordActivityPresenter extends BasePresenter<ChangePassWordActivityContract.View> implements ChangePassWordActivityContract.Presenter {

    private Context mContext;

    @Inject
    public ChangePassWordActivityPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

}
