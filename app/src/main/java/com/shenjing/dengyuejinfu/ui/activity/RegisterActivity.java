package com.shenjing.dengyuejinfu.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.DeviceUtils;
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
import com.shenjing.dengyuejinfu.entity.LoginBean;
import com.shenjing.dengyuejinfu.entity.RegisterBean;
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
    private String deviceId;
    private String product_model;

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
            if (PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
                    , Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE
                    , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) {
                imei = PhoneUtils.getIMEI();
                mPresenter.startLocation();
                deviceId=PhoneUtils.getDeviceId();
                product_model = DeviceUtils.getManufacturer() + DeviceUtils.getModel();
            } else {
                PermissionUtils.permission(PermissionConstants.PHONE,PermissionConstants.LOCATION)
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
                                deviceId = PhoneUtils.getDeviceId();;
                                product_model = DeviceUtils.getManufacturer() + DeviceUtils.getModel();
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong(R.string.toast_14);
                            }
                        }).request();
            }
        }
    }

    @OnClick(R.id.register_submit)
    public void onClick() {
        if (StringUtils.isSpace(registerUserIDCardNo.getText().toString().trim())) {
            ToastUtils.showLong(R.string.toast_2);
            return;
        }
        if (!RegexUtils.isIDCard18(registerUserIDCardNo.getText().toString().trim())) {
            ToastUtils.showLong(R.string.toast_3);
            return;
        }
        if (StringUtils.isSpace(registerUserLoginPass.getText().toString().trim())) {
            ToastUtils.showLong(R.string.toast_27);
            return;
        }
        if (StringUtils.isSpace(registerUserPhone.getText().toString().trim())) {
            ToastUtils.showLong(R.string.toast_5);
            return;
        }
        if (!RegexUtils.isMobileSimple(registerUserPhone.getText().toString().trim())) {
            ToastUtils.showLong(R.string.toast_6);
            return;
        }
        if (StringUtils.isSpace(registerUserRealName.getText().toString().trim())) {
            ToastUtils.showLong(R.string.toast_28);
            return;
        }
        if (StringUtils.isSpace(registerUserLoginName.getText().toString().trim())) {
            ToastUtils.showLong(R.string.toast_29);
            return;
        }
        String md5_pass = EncryptUtils.encryptMD5ToString(registerUserLoginPass.getText().toString().trim());
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
    public void registerSuccess(RegisterBean registerBean) {
        Map map = new HashMap();
        String md5_pass = EncryptUtils.encryptMD5ToString(registerUserLoginPass.getText().toString().trim());
        map.put("password", md5_pass);
        map.put("phoneNumber", registerUserPhone.getText().toString().trim());
        map.put("facilityCode", imei);
        mPresenter.login_account(map);
    }

    @Override
    public void registerFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        spUtils.put(BaseParams.USER_NAME_KEY, loginBean.getData().getName(), true);
        spUtils.put(BaseParams.USER_TOKEN_KEY, loginBean.getData().getToken(), true);
        spUtils.put(BaseParams.USER_ID_KEY, loginBean.getData().getId(), false);
        BaseParams.userName = loginBean.getData().getName();
        BaseParams.userId = loginBean.getData().getId();
        BaseParams.userToken = loginBean.getData().getToken();
        upLoadUserInfo();
        ARouter.getInstance().build(ARouterUrl.MainActivityUrl).navigation();
        releaseMemory();
    }

    @Override
    public void loginFailure(String msg) {

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

    /**
     * 上传日志信息
     */
    private void upLoadUserInfo() {
        Map map = new HashMap();
        map.put("userId", BaseParams.userId);
        map.put("type", "10");
        map.put("coordinate", BaseParams.location_longitude + "," + BaseParams.location_latitude);
        map.put("address", BaseParams.location_address);
        map.put("client", "Android");
        map.put("deviceId", deviceId);
        map.put("termModel", product_model);
        mPresenter.uploadUserInfo(map);
    }

    @Override
    protected void onDestroy() {
        mPresenter.closeLocation();
        super.onDestroy();
    }
}
