package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.ui.contract.SettingActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.SettingActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/1410:04
 * version: 1.0
 * desc   :设置页面
 */
@Route(path = ARouterUrl.SettingActivityUrl)
public class SettingActivity extends BaseActivity<SettingActivityPresenter> implements SettingActivityContract.View {
    @BindView(R.id.setting_mStatusBar)
    View settingMStatusBar;
    @BindView(R.id.setting_titleBar)
    TitleBar settingTitleBar;
    @BindView(R.id.setting_changePassWord)
    RelativeLayout settingChangePassWord;
    @BindView(R.id.setting_exit)
    TextView settingExit;
    private SPUtils spUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
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
        setStatusBar(settingMStatusBar, R.color.blue1);
        setTittleBarBackground(settingTitleBar, R.color.blue1);
        setPageTitle(settingTitleBar, "设置", R.color.white);
        setPageLeftText(settingTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        spUtils = SPUtils.getInstance();
    }

    @OnClick({R.id.setting_changePassWord,R.id.setting_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_changePassWord:
                ARouter.getInstance().build(ARouterUrl.ChangePassWordActivityUrl).navigation(this, new LoginNavigationCallback());
                break;
            case R.id.setting_exit:
                String userId = spUtils.getString(BaseParams.USER_ID_KEY);
                if (!StringUtils.isSpace(userId)
                        && !StringUtils.isSpace(spUtils.getString(BaseParams.USER_TOKEN_KEY))
                        && !StringUtils.isSpace(spUtils.getString(BaseParams.USER_NAME_KEY))
                        && !StringUtils.isSpace(spUtils.getString(BaseParams.USER_NAME_KEY))){
                    mPresenter.loginOut(userId);
                } else {
                    ToastUtils.showLong(R.string.app_login_state);
                }
                break;
        }
    }

    @Override
    public void LoginOutSuccess() {
        spUtils.remove(BaseParams.USER_ID_KEY,true);
        spUtils.remove(BaseParams.USER_NAME_KEY,true);
        spUtils.remove(BaseParams.USER_TOKEN_KEY,false);
        BaseParams.userToken="";
        BaseParams.userId="";
        BaseParams.userName="";
        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
                .withInt(BaseParams.Router_type,BaseParams.SettingActivity_Type)
                .navigation();
        releaseMemory();
    }

    @Override
    public void LoginOutFailure() {

    }
}
