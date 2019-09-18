package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.ui.contract.LostPassActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.LostPassActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TimingButton;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/9/1517:15
 * version: 1.0
 * desc   :忘记密码
 */

@Route(path = ARouterUrl.LostPassActivityUrl)
public class LostPassActivity extends BaseActivity<LostPassActivityPresenter> implements LostPassActivityContract.View {

    @BindView(R.id.lost_pass_mStatusBar)
    View lostPassMStatusBar;
    @BindView(R.id.lost_pass_titleBar)
    TitleBar lostPassTitleBar;
    @BindView(R.id.lost_pass_phone)
    TextInputEditText lostPassPhone;
    @BindView(R.id.lost_pass_vcode)
    TextInputEditText lostPassVcode;
    @BindView(R.id.lost_pass_timeButton)
    TimingButton lostPassTimeButton;
    @BindView(R.id.lost_pass_new)
    TextInputEditText lostPassNew;
    @BindView(R.id.lost_pass_sure)
    TextInputEditText lostPassSure;
    @BindView(R.id.lost_pass_submit)
    TextView lostPassSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lost_pass;
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
        setStatusBar(lostPassMStatusBar, R.color.blue1);
        setPageTitle(lostPassTitleBar, "忘记密码", R.color.white);
        setTittleBarBackground(lostPassTitleBar, R.color.blue1);
        setPageLeftText(lostPassTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void initFunc() {

    }

    @OnClick({R.id.lost_pass_submit,R.id.lost_pass_timeButton})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lost_pass_timeButton:
                lostPassVcode.setText("");
                if (StringUtils.isSpace(lostPassPhone.getText().toString().trim())){
                    ToastUtils.showLong("请输入手机号");
                    return;
                }
                if (!RegexUtils.isMobileSimple(lostPassPhone.getText().toString().trim())){
                    ToastUtils.showLong("请输入正确的手机号");
                    return;
                }
                mPresenter.sendSms(lostPassPhone.getText().toString().trim());
                lostPassTimeButton.start();
                break;
            case R.id.lost_pass_submit:
                if (StringUtils.isSpace(lostPassPhone.getText().toString().trim())){
                    ToastUtils.showLong("请输入手机号");
                    return;
                }
                if (!RegexUtils.isMobileSimple(lostPassPhone.getText().toString().trim())){
                    ToastUtils.showLong("请输入正确的手机号");
                    return;
                }
                if (StringUtils.isSpace(lostPassVcode.getText().toString().trim())){
                    ToastUtils.showLong("验证码");
                    return;
                }
                if (StringUtils.isSpace(lostPassNew.getText().toString().trim())){
                    ToastUtils.showLong("请输入新密码");
                    return;
                }
                if (StringUtils.isSpace(lostPassSure.getText().toString().trim())){
                    ToastUtils.showLong("请输入确认密码");
                    return;
                }
                if (!StringUtils.equalsIgnoreCase(lostPassNew.getText().toString().trim(),lostPassSure.getText().toString().trim())){
                    ToastUtils.showLong("两次的密码不相同");
                    return;
                }
                Map map=new HashMap();
                map.put("phoneNumber",lostPassPhone.getText().toString().trim());
                map.put("vcode",lostPassVcode.getText().toString().trim());
                String md5_newPass = EncryptUtils.encryptMD5ToString(lostPassNew.getText().toString().trim());
                map.put("newPwd",md5_newPass);
                mPresenter.lostPassSubmit(map);

                break;
        }
    }

    @Override
    public void getSmsSuccess() {

    }

    @Override
    public void getSmsFailure() {

    }

    @Override
    public void getPassSuccess() {
        SPUtils spUtils = SPUtils.getInstance();
        spUtils.remove(BaseParams.USER_TOKEN_KEY,true);
        spUtils.remove(BaseParams.USER_ID_KEY,true);
        spUtils.remove(BaseParams.USER_NAME_KEY,false);
        BaseParams.userName="";
        BaseParams.userId="";
        BaseParams.userToken="";
        releaseMemory();
    }

    @Override
    public void getPassFailure() {

    }

    @Override
    public TimingButton getTimeButton() {
        return lostPassTimeButton;
    }
}
