package com.shenjing.dengyuejinfu.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.ui.contract.LoginActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.LoginActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.TimingButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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
    @Autowired(name = BaseParams.Router_type)
    int router_type;
    @Autowired(name = BaseParams.Router_position_mainActivity)
    int main_position;
    @BindView(R.id.login_account)
    TextInputEditText loginAccount;
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
    @BindView(R.id.login_account_type)
    LinearLayout loginAccountType;
    @BindView(R.id.login_phone)
    TextInputEditText loginPhone;
    @BindView(R.id.login_code)
    TextInputEditText loginCode;
    @BindView(R.id.login_get_code)
    TimingButton loginGetCode;
    @BindView(R.id.login_phone_type)
    LinearLayout loginPhoneType;
    @BindView(R.id.login_type)
    TextView loginType;
    private SPUtils spUtils;
    private String imei; //唯一识别码

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        initActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        //沉浸式标题栏
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        int i = ConvertUtils.px2sp(BarUtils.getActionBarHeight());
        loginToolbar.setPadding(0, i, 0, 0);
        spUtils = SPUtils.getInstance();
    }

    @Override
    protected void initFunc() {
        getPermissions();
        LogUtils.d(path,main_position,router_type);
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)) {
                imei = PhoneUtils.getIMEI();
            } else {
                PermissionUtils.permission(PermissionConstants.PHONE)
                        .rationale(new PermissionUtils.OnRationaleListener() {
                            @Override
                            public void rationale(ShouldRequest shouldRequest) {
                                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .callback(new PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {
                                imei = PhoneUtils.getIMEI();
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong("获取权限失败");
                            }
                        }).request();
            }
        }
    }

    @Override
    public void showLoginSuccess(LoginModel loginModel, int type) {
        spUtils.put(BaseParams.USER_TOKEN_KEY, loginModel.getData().getToken(), true);
        spUtils.put(BaseParams.USER_NAME_KEY, loginModel.getData().getName(), true);
        spUtils.put(BaseParams.USER_ID_KEY, loginModel.getData().getId(), false);
        BaseParams.userName = loginModel.getData().getName();
        BaseParams.userId = loginModel.getData().getId();
        BaseParams.userToken = loginModel.getData().getToken();
        if (router_type == BaseParams.MainActivity_Type) {  //首页
            Intent intent = new Intent();
            intent.putExtra("position", main_position);
            setResult(1001, intent);
        } else if(router_type==BaseParams.ChangePass_Type){ //修改密码

        } else if(router_type==BaseParams.SettingActivity_Type){  //安全退出
            ARouter.getInstance().build(ARouterUrl.MainActivityUrl).navigation();
        }else {
            ARouter.getInstance().build(path).navigation(this,new LoginNavigationCallback());
        }
        releaseMemory();
    }

    @Override
    public void shownLoginFailure(LoginModel loginModel, int type) {

    }

    @Override
    public void showSmsSuccess(String sms) {

    }

    @Override
    public void shownSmsFailure(String smsModule) {
        loginGetCode.TimeOnCancel();
    }

    @Override
    public TimingButton getTimeButtonView() {
        return loginGetCode;
    }

    @OnClick({R.id.login_get_code, R.id.login_submit, R.id.login_register, R.id.login_type, R.id.login_forget_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_get_code:
                loginCode.setText("");
                if (StringUtils.isSpace(loginPhone.getText().toString().trim())) {
                    ToastUtils.showLong("请输入手机号");
                    return;
                }
                if (!RegexUtils.isMobileSimple(loginPhone.getText().toString().trim())){
                    ToastUtils.showLong("请输入正确的手机号");
                    return;
                }
                mPresenter.loginSms(loginPhone.getText().toString().trim());
                loginGetCode.start();
                break;
            case R.id.login_submit:
                if (loginType.getTag() == null || loginType.getTag().equals("account")) {  //账户登录
                    if (StringUtils.isSpace(loginAccount.getText().toString().trim())) {
                        ToastUtils.showLong("请输入账户");
                        return;
                    }
                    if (StringUtils.isSpace(loginPass.getText().toString().trim())) {
                        ToastUtils.showLong("请输入密码");
                        return;
                    }
                    Map map = new HashMap();
                    String md5_pass = EncryptUtils.encryptMD5ToString(loginPass.getText().toString().trim());
                    map.put("password", md5_pass);
                    map.put("phoneNumber", loginAccount.getText().toString().trim());
                    map.put("facilityCode", imei);
                    mPresenter.LoginAccount(map);

                } else if (loginType.getTag() != null && loginType.getTag().equals("phone")) { //手机号码登录
                    if (StringUtils.isSpace(loginPhone.getText().toString().trim())) {
                        ToastUtils.showLong("请输入手机号");
                        return;
                    }
                    if (!RegexUtils.isMobileSimple(loginPhone.getText().toString().trim())) {
                        ToastUtils.showLong("请输入正确的手机号");
                        return;
                    }
                    if (StringUtils.isSpace(loginCode.getText().toString().trim())) {
                        ToastUtils.showLong("验证码不能为空");
                        return;
                    }

                    Map map = new HashMap();
                    map.put("facilityCode", imei);
                    map.put("yzm", loginCode.getText().toString().trim());
                    map.put("phoneNumber", loginPhone.getText().toString().trim());
                    mPresenter.LoginPhone(map);
                }
                break;
            case R.id.login_register:
                ARouter.getInstance().build(ARouterUrl.RegisterActivityUrl)
                        .withInt(BaseParams.Router_type_mainActivity, router_type)
                        .withInt(BaseParams.Router_position_mainActivity, main_position)
                        .withString(BaseParams.RouterPath, path)
                        .navigation();
                break;
            case R.id.login_type:
                if (loginType.getTag() == null || loginType.getTag().equals("account")) {
                    loginType.setTag("phone");
                    loginType.setText("账户登录");
                    loginAccountType.setVisibility(View.GONE);
                    loginPhoneType.setVisibility(View.VISIBLE);
                } else {
                    loginType.setTag("account");
                    loginType.setText("手机号登录");
                    loginAccountType.setVisibility(View.VISIBLE);
                    loginPhoneType.setVisibility(View.GONE);
                }
                break;
            case R.id.login_forget_pass:
                ARouter.getInstance().build(ARouterUrl.LostPassActivityUrl)
                        .navigation();
                break;
        }
    }

}
