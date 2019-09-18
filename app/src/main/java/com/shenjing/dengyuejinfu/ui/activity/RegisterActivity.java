package com.shenjing.dengyuejinfu.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.EncryptUtils;
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
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.respondModule.RegisterModel;
import com.shenjing.dengyuejinfu.ui.contract.RegisterActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.RegisterActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/9/1210:28
 * version: 1.0
 * desc   :  注册页面
 */
@Route(path = ARouterUrl.RegisterActivityUrl)
public class RegisterActivity extends BaseActivity<RegisterActivityPresenter> implements RegisterActivityContract.View {

    @Autowired(name = BaseParams.RouterPath)
    String path;
    @Autowired(name = BaseParams.Router_type_mainActivity)
    int router_type;
    @Autowired(name = BaseParams.Router_position_mainActivity)
    int main_position;
    @BindView(R.id.register_mStatusBar)
    View registermStatusBar;
    @BindView(R.id.register_titleBar)
    TitleBar registerTitleBar;
    @BindView(R.id.register_userIDCardNo)
    TextInputEditText registerUserIDCardNo;
    @BindView(R.id.register_userShareCode)
    TextInputEditText registerUserShareCode;
    @BindView(R.id.register_userLoginPass)
    TextInputEditText registerUserLoginPass;
    @BindView(R.id.register_userPhone)
    TextInputEditText registerUserPhone;
    @BindView(R.id.register_userRealName)
    TextInputEditText registerUserRealName;
    @BindView(R.id.register_userLoginName)
    TextInputEditText registerUserLoginName;
    @BindView(R.id.register_submit)
    TextView registerSubmit;
    private String imei;
    private SPUtils spUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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
        setStatusBar(registermStatusBar, R.color.textColor4);
        setPageTitle(registerTitleBar, "注册页面", R.color.white);
        setTittleBarBackground(registerTitleBar, R.color.textColor4);
        setPageLeftText(registerTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void initFunc() {
        spUtils = SPUtils.getInstance();
        getPermissions();

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

    @OnClick(R.id.register_submit)
    public void onClick() {
        if (StringUtils.isSpace(registerUserIDCardNo.getText().toString().trim())) {
            ToastUtils.showLong("请输入身份证号");
            return;
        }
        if (!RegexUtils.isIDCard18(registerUserIDCardNo.getText().toString().trim())) {
            ToastUtils.showLong("请输入正确的身份证号");
            return;
        }
        if (StringUtils.isSpace(registerUserLoginPass.getText().toString().trim())) {
            ToastUtils.showLong("请输入登录密码");
            return;
        }
        if (StringUtils.isSpace(registerUserPhone.getText().toString().trim())) {
            ToastUtils.showLong("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileSimple(registerUserPhone.getText().toString().trim())) {
            ToastUtils.showLong("请输入正确的手机号");
            return;
        }
        if (StringUtils.isSpace(registerUserRealName.getText().toString().trim())) {
            ToastUtils.showLong("请输入真实姓名");
            return;
        }
        if (StringUtils.isSpace(registerUserLoginName.getText().toString().trim())) {
            ToastUtils.showLong("请输入用户名");
            return;
        }
//        UserRegisterBean userRegisterBean = new UserRegisterBean();
//        userRegisterBean.setIdCardNo(registerUserIDCardNo.getText().toString().trim());
        String md5_pass = EncryptUtils.encryptMD5ToString(registerUserLoginPass.getText().toString().trim());
//        userRegisterBean.setPassword(md5_pass);
//        userRegisterBean.setPhoneNumber(registerUserPhone.getText().toString().trim());
//        userRegisterBean.setRealName(registerUserRealName.getText().toString().trim());
//        userRegisterBean.setUserName(registerUserLoginName.getText().toString().trim());
//        userRegisterBean.setFacilityCode(imei);
        Map map = new HashMap();
        map.put("idCardNo", registerUserIDCardNo.getText().toString().trim());
        map.put("password", md5_pass);
        map.put("phoneNumber", registerUserPhone.getText().toString().trim());
        map.put("realName", registerUserRealName.getText().toString().trim());
        map.put("userName", registerUserLoginName.getText().toString().trim());
        map.put("facilityCode", imei);
        mPresenter.register(map);
    }

    @Override
    public void registerSuccess(RegisterModel registerModel) {
        Map map = new HashMap();
        String md5_pass = EncryptUtils.encryptMD5ToString(registerUserLoginPass.getText().toString().trim());
        map.put("password", md5_pass);
        map.put("phoneNumber", registerUserPhone.getText().toString().trim());
        map.put("facilityCode", imei);
//        AccountLoginBean accountLoginBean = new AccountLoginBean();
//        accountLoginBean.setFacilityCode(imei);
//        accountLoginBean.setUserName(registerUserLoginName.getText().toString().trim());
//        accountLoginBean.setPassword(registerUserLoginPass.getText().toString().trim());
        mPresenter.login_account(map);
    }

    @Override
    public void registerFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void loginSuccess(LoginModel loginModel) {
        spUtils.put(BaseParams.USER_NAME_KEY, loginModel.getData().getName(), true);
        spUtils.put(BaseParams.USER_TOKEN_KEY, loginModel.getData().getToken(), true);
        spUtils.put(BaseParams.USER_ID_KEY, loginModel.getData().getId(), false);
        BaseParams.userName = loginModel.getData().getName();
        BaseParams.userId = loginModel.getData().getId();
        BaseParams.userToken = loginModel.getData().getToken();
        if (router_type == BaseParams.Router_code_mainActivity) {
            Intent intent = new Intent();
            intent.putExtra("position", main_position);
            setResult(1001, intent);
        } else {
            ARouter.getInstance().build(path).navigation();
        }
        //关闭前一个Activity(即：LoginActivity)
        Activity preActivity = getPreActivity();
        closeActivity(preActivity);
        //关闭当前
        releaseMemory();

    }

    @Override
    public void loginFailure(String msg) {

    }
}
