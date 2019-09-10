package com.shenjing.mytextapp.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.AnnouncementActivityContract;
import com.shenjing.mytextapp.ui.presenter.AnnouncementActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1910:51
 * version: 1.0
 * desc   :  公告列表
 */
@Route(path = ARouterUrl.AnnouncementActivityUrl)
public class AnnouncementActivity extends BaseActivity<AnnouncementActivityPresenter>
        implements AnnouncementActivityContract.View {
    @BindView(R.id.announcement_mStatusBar)
    View announcementMStatusBar;
    @BindView(R.id.announcement_titleBar)
    TitleBar announcementTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_announcement;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        setStatusBar(announcementMStatusBar, R.color.blue1);
        setTittleBarBackground(announcementTitleBar, R.color.blue1);
        setPageTitle(announcementTitleBar, "公告列表", R.color.white);
        setPageLeftText(announcementTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {

    }
}
