package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.entity.ChangePassBean;
import com.shenjing.dengyuejinfu.ui.contract.ChangePassWordActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.ChangePassWordActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/159:39
 * version: 1.0
 * desc   :修改密码/忘记密码
 */
@Route(path = ARouterUrl.ChangePassWordActivityUrl)
public class ChangePassWordActivity extends BaseActivity<ChangePassWordActivityPresenter>
        implements ChangePassWordActivityContract.View {
    @BindView(R.id.change_pass_mStatusBar)
    View changePassMStatusBar;
    @BindView(R.id.change_pass_titleBar)
    TitleBar changePassTitleBar;
    @BindView(R.id.change_pass_oldPass)
    EditText changePassOldPass;
    @BindView(R.id.change_pass_newPass1)
    EditText changePassNewPass1;
    @BindView(R.id.change_pass_newPass2)
    EditText changePassNewPass2;
    @BindView(R.id.change_pass_submit)
    TextView changePassSubmit;
    private SPUtils spUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
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
        setStatusBar(changePassMStatusBar, R.color.blue1);
        setTittleBarBackground(changePassTitleBar, R.color.blue1);
        setPageTitle(changePassTitleBar, "修改密码", R.color.white);
        setPageLeftText(changePassTitleBar, "返回", R.color.white, new OnOnceClickListener() {
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

    @OnClick(R.id.change_pass_submit)
    public void onClick() {
        if (StringUtils.isSpace(changePassOldPass.getText().toString().trim())){
            ToastUtils.showLong(R.string.toast_18);
            return;
        }
        if (StringUtils.isSpace(changePassNewPass1.getText().toString().trim())){
            ToastUtils.showLong(R.string.toast_19);
            return;
        }
        if (StringUtils.isSpace(changePassNewPass2.getText().toString().trim())){
            ToastUtils.showLong(R.string.toast_20);
            return;
        }
        if (!StringUtils.equals(changePassNewPass1.getText().toString().trim(),changePassNewPass2.getText().toString().trim())){
            ToastUtils.showLong(R.string.toast_21);
            return;
        }
        Map map=new HashMap();
        map.put("userId",Long.parseLong(spUtils.getString(BaseParams.USER_ID_KEY)));
        String md5_oldPass = EncryptUtils.encryptMD5ToString(changePassOldPass.getText().toString().trim());
        map.put("oldPwd",md5_oldPass);
        String md5_newPass = EncryptUtils.encryptMD5ToString(changePassNewPass1.getText().toString().trim());
        map.put("newPwd",md5_newPass);
        map.put("phoneNumber",BaseParams.userName);
        mPresenter.changePassWord(map);
    }


    @Override
    public void showSuccess(ChangePassBean changePassBean) {
        SPUtils spUtils = SPUtils.getInstance();
        spUtils.remove(BaseParams.USER_TOKEN_KEY,true);
        spUtils.remove(BaseParams.USER_ID_KEY,true);
        spUtils.remove(BaseParams.USER_NAME_KEY,false);
        BaseParams.userName="";
        BaseParams.userId="";
        BaseParams.userToken="";
        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
                .withInt(BaseParams.Router_type, BaseParams.ChangePass_Type)
                .navigation();
        releaseMemory();
    }
    @Override
    public void showFailure(String msg) {

    }
}
