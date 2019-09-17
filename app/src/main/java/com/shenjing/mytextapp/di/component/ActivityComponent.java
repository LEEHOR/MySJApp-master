package com.shenjing.mytextapp.di.component;

import android.app.Activity;

import com.shenjing.mytextapp.di.module.ActivityModule;
import com.shenjing.mytextapp.di.scope.ActivityScope;
import com.shenjing.mytextapp.ui.activity.AddCreditCardActivity;
import com.shenjing.mytextapp.ui.activity.AnnouncementActivity;
import com.shenjing.mytextapp.ui.activity.BankCardCertificationActivity;
import com.shenjing.mytextapp.ui.activity.CardEvaluationActivity;
import com.shenjing.mytextapp.ui.activity.CardEvaluationRecordActivity;
import com.shenjing.mytextapp.ui.activity.CashWithdrawalActivity;
import com.shenjing.mytextapp.ui.activity.CertificationActivity;
import com.shenjing.mytextapp.ui.activity.ChangePassWordActivity;
import com.shenjing.mytextapp.ui.activity.CreditCardCertificationActivity;
import com.shenjing.mytextapp.ui.activity.CreditInquiryActivity;
import com.shenjing.mytextapp.ui.activity.DiscountRecordActivity;
import com.shenjing.mytextapp.ui.activity.LoanPageActivity;
import com.shenjing.mytextapp.ui.activity.LoginActivity;
import com.shenjing.mytextapp.ui.activity.LostPassActivity;
import com.shenjing.mytextapp.ui.activity.MainActivity;
import com.shenjing.mytextapp.ui.activity.PaymentVerificationActivity;
import com.shenjing.mytextapp.ui.activity.RegisterActivity;
import com.shenjing.mytextapp.ui.activity.SettingActivity;
import com.shenjing.mytextapp.ui.activity.ShareActivity;
import com.shenjing.mytextapp.ui.activity.TransactionDetailsActivity;
import com.shenjing.mytextapp.ui.activity.MyCustomerActivity;
import com.shenjing.mytextapp.ui.activity.CreditInquiryRecordActivity;

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

}
