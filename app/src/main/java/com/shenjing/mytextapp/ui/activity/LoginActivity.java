package com.shenjing.mytextapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseActivity;
import com.shenjing.mytextapp.common.ARouterUrl;
import com.shenjing.mytextapp.common.BaseParams;
import com.shenjing.mytextapp.ui.contract.LoginActivityContract;
import com.shenjing.mytextapp.ui.presenter.LoginActivityPresenter;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/139:55
 * version: 1.0
 * desc   :登录页面
 */
@Route(path = ARouterUrl.LoginActivityUrl)
public class LoginActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityContract.View {
    @Autowired(name = BaseParams.RouterPath)
    String path;
    @Autowired(name = BaseParams.Router_type_mainActivity)
    int router_type;
    @Autowired(name = BaseParams.Router_position_mainActivity)
    int main_position;
    @BindView(R.id.login_account)
    TextInputEditText loginAccount;
    //    @BindView(R.id.login_clear_iv)
//    ImageView loginClearIv;
    @BindView(R.id.login_pass)
    TextInputEditText loginPass;
    @BindView(R.id.login_submit)
    TextView loginSubmit;
    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.login_forget_pass)
    TextView loginForgetPass;
    @BindView(R.id.login_version_code)
    TextView loginVersionCode;
    @BindView(R.id.login_toolbar)
    FrameLayout loginToolbar;
    private SPUtils spUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        mActivityComponent.inject(this);
        LogUtils.d("login",path+"/"+router_type+"/"+main_position);
    }

    @Override
    protected void initView() {
        //沉浸式标题栏
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        int i = ConvertUtils.px2sp(BarUtils.getActionBarHeight(this));
        loginToolbar.setPadding(0, i, 0, 0);
        spUtils = SPUtils.getInstance();
    }

    @Override
    protected void initFunc() {
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spUtils.put(BaseParams.USER_NAME_KEY,"李浩",true);
                spUtils.put(BaseParams.USER_TOKEN_KEY,"123456",false);
                if (router_type == 1001) {  //首页
                    Intent intent = new Intent();
                    intent.putExtra("position", main_position);
                    setResult(1001, intent);
                } else {    //其他页面
                    ARouter.getInstance().build(path).navigation();
                }
                releaseMemory();
            }
        });
    }

}
