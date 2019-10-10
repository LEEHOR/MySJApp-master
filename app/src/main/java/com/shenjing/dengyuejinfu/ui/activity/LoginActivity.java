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
import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.DeviceUtils;
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
import com.shenjing.dengyuejinfu.entity.LoginBean;
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
    private String deviceId; //
    private String product_model;

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
        LogUtils.d(path, main_position, router_type);
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
                    , Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE
                    , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
                imei = PhoneUtils.getIMEI();
                mPresenter.startLocation();
                deviceId = PhoneUtils.getDeviceId();;
                product_model = DeviceUtils.getManufacturer() + DeviceUtils.getModel();
            } else {
                PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.LOCATION)
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
                                deviceId = PhoneUtils.getDeviceId();
                                product_model = DeviceUtils.getManufacturer() + DeviceUtils.getModel();
                                mPresenter.startLocation();

                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong(R.string.toast_14);
                            }
                        }).request();
            }
        } else {
            imei = PhoneUtils.getIMEI();
            mPresenter.startLocation();
            deviceId = PhoneUtils.getDeviceId();;
            product_model = DeviceUtils.getManufacturer() + DeviceUtils.getModel();
        }
    }

    @Override
    public void showLoginSuccess(LoginBean loginBean, int type) {
        spUtils.put(BaseParams.USER_TOKEN_KEY, loginBean.getData().getToken(), true);
        spUtils.put(BaseParams.USER_NAME_KEY, loginBean.getData().getName(), true);
        spUtils.put(BaseParams.USER_ID_KEY, loginBean.getData().getId(), false);
        BaseParams.userName = loginBean.getData().getName();
        BaseParams.userId = loginBean.getData().getId();
        BaseParams.userToken = loginBean.getData().getToken();
        //上传日志
        upLoadUserInfo();
        if (router_type == BaseParams.MainActivity_Type) {  //首页
            Intent intent = new Intent();
            intent.putExtra("position", main_position);
            setResult(1001, intent);
        } else if (router_type == BaseParams.ChangePass_Type) { //修改密码
            ARouter.getInstance().build(ARouterUrl.MainActivityUrl).navigation();
        } else if (router_type == BaseParams.SettingActivity_Type) {  //安全退出
            ARouter.getInstance().build(ARouterUrl.MainActivityUrl).navigation();
        } else {
            ARouter.getInstance().build(path).navigation(this, new LoginNavigationCallback());
        }
        releaseMemory();
    }

    @Override
    public void shownLoginFailure(LoginBean loginBean, int type) {

    }

    @Override
    public void showSmsSuccess(String sms) {

    }

    @Override
    public void shownSmsFailure(String smsModule) {
        loginGetCode.TimeOnCancel();
    }


    @Override
    public void LocationSuccess(AMapLocation aMapLocation) {
        BaseParams.location_latitude = aMapLocation.getLatitude();
        BaseParams.location_longitude = aMapLocation.getLongitude();
        BaseParams.location_address = aMapLocation.getAddress();
    }

    @Override
    public void LocationFailure(int errorCode) {

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
                    ToastUtils.showLong(R.string.toast_5);
                    return;
                }
                if (!RegexUtils.isMobileSimple(loginPhone.getText().toString().trim())) {
                    ToastUtils.showLong(R.string.toast_6);
                    return;
                }
                mPresenter.loginSms(loginPhone.getText().toString().trim());
                loginGetCode.start();
                break;
            case R.id.login_submit:
                if (loginType.getTag() == null || loginType.getTag().equals("account")) {  //账户登录
                    if (StringUtils.isSpace(loginAccount.getText().toString().trim())) {
                        ToastUtils.showLong(R.string.toast_22);
                        return;
                    }
                    if (StringUtils.isSpace(loginPass.getText().toString().trim())) {
                        ToastUtils.showLong(R.string.toast_23);
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
                        ToastUtils.showLong(R.string.toast_5);
                        return;
                    }
                    if (!RegexUtils.isMobileSimple(loginPhone.getText().toString().trim())) {
                        ToastUtils.showLong(R.string.toast_6);
                        return;
                    }
                    if (StringUtils.isSpace(loginCode.getText().toString().trim())) {
                        ToastUtils.showLong(R.string.toast_24);
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
//                        .withInt(BaseParams.Router_type_mainActivity, router_type)
//                        .withInt(BaseParams.Router_position_mainActivity, main_position)
//                        .withString(BaseParams.RouterPath, path)
                        .navigation();
                break;
            case R.id.login_type:
                if (loginType.getTag() == null || loginType.getTag().equals("account")) {
                    loginType.setTag("phone");
                    loginType.setText(getResources().getString(R.string.account_19));
                    loginAccountType.setVisibility(View.GONE);
                    loginPhoneType.setVisibility(View.VISIBLE);
                } else {
                    loginType.setTag("account");
                    loginType.setText(getResources().getString(R.string.account_11));
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==1002 && resultCode==1002){
//            int position = data.getIntExtra("position", 0);
//            Intent intent = new Intent();
//            intent.putExtra("position", position);
//            setResult(1001, intent);
//            releaseMemory();
//        }
//    }

    /**
     * 上传日志信息
     */
    private void upLoadUserInfo() {
        Map map = new HashMap();
        map.put("userId", BaseParams.userId);
        map.put("type", "20");
        map.put("coordinate", BaseParams.location_longitude + "," + BaseParams.location_latitude);
        map.put("address", BaseParams.location_address);
        map.put("client", "Android");
        map.put("deviceId", deviceId);
        map.put("termModel", product_model);
        LogUtils.d(map.get("userId"),map.get("type"),map.get("coordinate"),map.get("address"),map.get("client")
        ,map.get("deviceId"),map.get("termModel"));
        mPresenter.uploadUserInfo(map);
    }

    @Override
    protected void onDestroy() {
        mPresenter.closeLocation();
        super.onDestroy();
    }
}
