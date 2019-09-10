package com.shenjing.mytextapp.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.ui.contract.CardEvaluationRecordActivityContract;
import com.shenjing.mytextapp.ui.presenter.CardEvaluationRecordActivityPresenter;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/199:57
 * version: 1.0
 * desc   :  信用卡评测记录
 */
@Route(path = ARouterUrl.CardEvaluationRecordActivityUrl)
public class CardEvaluationRecordActivity extends BaseActivity<CardEvaluationRecordActivityPresenter>
        implements CardEvaluationRecordActivityContract.View {
    @BindView(R.id.card_evaluation_record_mStatusBar)
    View cardEvaluationRecordMStatusBar;
    @BindView(R.id.card_evaluation_record_titleBar)
    TitleBar cardEvaluationRecordTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_card_evaluation_record;
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
        setStatusBar(cardEvaluationRecordMStatusBar, R.color.blue1);
        setTittleBarBackground(cardEvaluationRecordTitleBar, R.color.blue1);
        setPageTitle(cardEvaluationRecordTitleBar, "信用卡评测记录", R.color.white);
        setPageLeftText(cardEvaluationRecordTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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
