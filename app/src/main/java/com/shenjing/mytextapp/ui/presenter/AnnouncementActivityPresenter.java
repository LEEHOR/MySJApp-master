package com.shenjing.mytextapp.ui.presenter;

import android.content.Context;

import com.shenjing.mytextapp.base.BasePresenter;
import com.shenjing.mytextapp.ui.contract.AnnouncementActivityContract;
import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class AnnouncementActivityPresenter extends BasePresenter<AnnouncementActivityContract.View>
        implements AnnouncementActivityContract.Presenter {
    @Inject
    public AnnouncementActivityPresenter() {

    }

}
