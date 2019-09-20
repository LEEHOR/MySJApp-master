package com.shenjing.dengyuejinfu.di.component;

import android.app.Activity;

import com.shenjing.dengyuejinfu.di.module.ActivityModule;
import com.shenjing.dengyuejinfu.di.scope.ActivityScope;
import com.shenjing.dengyuejinfu.ui.activity.AddCreditCardActivity;
import com.shenjing.dengyuejinfu.ui.activity.AnnouncementActivity;
import com.shenjing.dengyuejinfu.ui.activity.BankCardCertificationActivity;
import com.shenjing.dengyuejinfu.ui.activity.CardEvaluationActivity;
import com.shenjing.dengyuejinfu.ui.activity.CardEvaluationRecordActivity;
import com.shenjing.dengyuejinfu.ui.activity.CashWithdrawalActivity;
import com.shenjing.dengyuejinfu.ui.activity.CertificationActivity;
import com.shenjing.dengyuejinfu.ui.activity.ChangePassWordActivity;
import com.shenjing.dengyuejinfu.ui.activity.CreditCardCertificationActivity;
import com.shenjing.dengyuejinfu.ui.activity.CreditInquiryActivity;
import com.shenjing.dengyuejinfu.ui.activity.DiscountRecordActivity;
import com.shenjing.dengyuejinfu.ui.activity.LoanPageActivity;
import com.shenjing.dengyuejinfu.ui.activity.LoginActivity;
import com.shenjing.dengyuejinfu.ui.activity.LostPassActivity;
import com.shenjing.dengyuejinfu.ui.activity.PaymentVerificationActivity;
import com.shenjing.dengyuejinfu.ui.activity.QRShareActivity;
import com.shenjing.dengyuejinfu.ui.activity.RegisterActivity;
import com.shenjing.dengyuejinfu.ui.activity.SettingActivity;
import com.shenjing.dengyuejinfu.ui.activity.ShareActivity;
import com.shenjing.dengyuejinfu.ui.activity.TransactionDetailsActivity;
import com.shenjing.dengyuejinfu.ui.activity.MyCustomerActivity;
import com.shenjing.dengyuejinfu.ui.activity.CreditInquiryRecordActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(LoginActivity loginActivity);

    void inject(SettingActivity settingActivity);

    void inject(TransactionDetailsActivity transactionDetailsActivity);

    void inject(MyCustomerActivity myCustomerActivity);

    void inject(ShareActivity shareActivity);

    void inject(CertificationActivity certificationActivity);

    void inject(BankCardCertificationActivity bankCardCertificationActivity);

    void inject(PaymentVerificationActivity paymentVerificationActivity);

    void inject(CreditCardCertificationActivity creditCardCertificationActivity);

    void inject(AddCreditCardActivity addCreditCardActivity);

    void inject(ChangePassWordActivity changePassWordActivity);

    void inject(CashWithdrawalActivity cashWithdrawalActivity);

    void inject(DiscountRecordActivity discountRecordActivity);

    void inject(LoanPageActivity loanPageActivity);

    void inject(CardEvaluationActivity cardEvaluationActivity);

    void inject(CardEvaluationRecordActivity cardEvaluationRecordActivity);

    void inject(CreditInquiryActivity creditInquiryActivity);

    void inject(CreditInquiryRecordActivity creditInquiryRecordActivity);

    void inject(AnnouncementActivity announcementActivity);

    void inject(RegisterActivity registerActivity);

    void inject(LostPassActivity  lostPassActivity);

    void inject(QRShareActivity qrShareActivity);

}
