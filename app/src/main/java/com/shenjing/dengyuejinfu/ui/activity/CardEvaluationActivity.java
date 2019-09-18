package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.contract.CardEvaluationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.CardEvaluationActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/199:49
 * version: 1.0
 * desc   :  信用卡测评
 */
@Route(path = ARouterUrl.CardEvaluationActivityUrl)
public class CardEvaluationActivity extends BaseActivity<CardEvaluationActivityPresenter>
        implements CardEvaluationActivityContract.View {
    @BindView(R.id.card_evaluation_mStatusBar)
    View cardEvaluationMStatusBar;
    @BindView(R.id.card_evaluation_titleBar)
    TitleBar cardEvaluationTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_card_evaluation;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        initActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        setStatusBar(cardEvaluationMStatusBar, R.color.blue1);
        setTittleBarBackground(cardEvaluationTitleBar, R.color.blue1);
        setPageTitle(cardEvaluationTitleBar, "信用卡测评", R.color.white);
        setPageLeftText(cardEvaluationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
        setTitleRight(cardEvaluationTitleBar, "历史记录", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                ARouter.getInstance().build(ARouterUrl.CardEvaluationRecordActivityUrl).navigation();
            }
        });
    }

    @Override
    protected void initFunc() {

    }
}
