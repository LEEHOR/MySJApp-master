package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.ui.contract.ShareActivityContract;

import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class ShareActivityPresenter extends BasePresenter<ShareActivityContract.View> implements ShareActivityContract.Presenter {

    private Context mContext;

    @Inject
    public ShareActivityPresenter(@ContextLife Context context) {
        this.mContext = context;
    }

}
