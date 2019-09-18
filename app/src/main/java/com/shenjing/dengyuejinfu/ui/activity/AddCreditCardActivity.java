package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.ui.contract.AddCreditCardActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.AddCreditCardActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/1419:35
 * version: 1.0
 * desc   :添加信用卡页面
 */
@Route(path = ARouterUrl.AddCardActivityUrl)
public class AddCreditCardActivity extends BaseActivity<AddCreditCardActivityPresenter>
        implements AddCreditCardActivityContract.View {
    @BindView(R.id.add_creditCard_mStatusBar)
    View addCardMStatusBar;
    @BindView(R.id.add_creditCard_titleBar)
    TitleBar addCardTitleBar;
    @BindView(R.id.add_creditCard_userName)
    EditText addCreditCardUserName;
    @BindView(R.id.add_creditCard_userIdCardNo)
    EditText addCreditCardUserIdCardNo;
    @BindView(R.id.add_creditCard_bankNo)
    EditText addCreditCardBankNo;
    @BindView(R.id.add_creditCard_bankPhone)
    EditText addCreditCardBankPhone;
    @BindView(R.id.add_creditCard_selectBank)
    TextView addCreditCardSelectBank;
    @BindView(R.id.add_creditCard_cvvn)
    EditText addCreditCardCvvn;
    @BindView(R.id.add_creditCard_bankValidity)
    EditText addCreditCardBankValidity;
    @BindView(R.id.add_creditCard_submit)
    TextView addCreditCardSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_credit_card;
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
        setStatusBar(addCardMStatusBar, R.color.blue1);
        setTittleBarBackground(addCardTitleBar, R.color.blue1);
        setPageTitle(addCardTitleBar, "添加信用卡", R.color.white);
        setPageLeftText(addCardTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {

    }

    @Override
    public void uploadInfoSuccess() {
        addCreditCardSubmit.setEnabled(false);
    }

    @Override
    public void uploadInfoFailure() {
        addCreditCardSubmit.setEnabled(true);
    }

    @OnClick(R.id.add_creditCard_submit)
    public void onClick() {
        if (StringUtils.isSpace(addCreditCardUserName.getText().toString().trim())){
            ToastUtils.showLong("请输入收款人姓名");
            return;
        }
        if (StringUtils.isSpace(addCreditCardUserIdCardNo.toString().trim())){
            ToastUtils.showLong("请输入身份证号");
            return;
        }
        if (!RegexUtils.isIDCard18(addCreditCardUserIdCardNo.getText().toString().trim())){
            ToastUtils.showLong("请输入有效的身份证号");
            return;
        }
        if (StringUtils.isSpace(addCreditCardBankNo.getText().toString().trim())){
            ToastUtils.showLong("请输入银行卡号");
            return;
        }
        if (StringUtils.isSpace(addCreditCardBankPhone.getText().toString().trim())){
            ToastUtils.showLong("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileSimple(addCreditCardBankPhone.getText().toString().trim())){
            ToastUtils.showLong("请输入正确的手机号");
            return;
        }
        if (StringUtils.isSpace(addCreditCardCvvn.getText().toString().trim()) && addCreditCardCvvn.getText().toString().trim().length()!=3){
            ToastUtils.showLong("请输入银行卡签名栏后三位");
            return;
        }
        if (StringUtils.isSpace(addCreditCardBankValidity.getText().toString().trim())){
            ToastUtils.showLong("请输入银行卡有效期");
            return;
        }
        Map map=new HashMap();
        map.put("userId", Long.parseLong(BaseParams.userId));
        map.put("name",addCreditCardUserName.getText().toString().trim());
        map.put("idNo",addCreditCardUserIdCardNo.getText().toString().trim());
        map.put("creditCardNo",addCreditCardBankNo.getText().toString().trim());
        map.put("bankPhone",addCreditCardBankPhone.getText().toString().trim());
        map.put("bank","");
        map.put("CVVN2",addCreditCardCvvn.getText().toString().trim());
        map.put("validityPeriod",addCreditCardBankValidity.getText().toString().trim());
        mPresenter.uploadCreditCard(map);
    }
}
