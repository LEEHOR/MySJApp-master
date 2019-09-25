package com.shenjing.dengyuejinfu.ui.presenter;

import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.ui.contract.ShareDialogFragmentContract;

import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class ShareDialogFragmentPresenter extends BasePresenter<ShareDialogFragmentContract.View>
        implements ShareDialogFragmentContract.Presenter {
    @Inject
    public ShareDialogFragmentPresenter() {

    }

    @Override
    public void getShareInfo(String userId) {

    }
}
