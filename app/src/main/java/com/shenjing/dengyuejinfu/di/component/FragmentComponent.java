package com.shenjing.dengyuejinfu.di.component;

import android.app.Activity;

import com.shenjing.dengyuejinfu.di.module.FragmentModule;
import com.shenjing.dengyuejinfu.di.scope.FragmentScope;
import com.shenjing.dengyuejinfu.ui.fragment.IndexFragment;
import com.shenjing.dengyuejinfu.ui.fragment.MemberFragment;
import com.shenjing.dengyuejinfu.ui.fragment.MineFragment;
import com.shenjing.dengyuejinfu.ui.fragment.ReceiptFragment;
import com.shenjing.dengyuejinfu.ui.fragment.ReplacementFragment;
import com.shenjing.dengyuejinfu.ui.fragment.ViewPagerTransaction;

import dagger.Component;


@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
//    @ContextLife("Activity")
//    Context getAcitivtyContext();
//
//    @ContextLife("Application")
//    Context getApplicationContext();

    Activity getAcitivty();

    void inject(IndexFragment indexFragment);

    void inject(ReplacementFragment replacementFragment);

    void inject(MemberFragment memberFragment);

    void inject(ReceiptFragment receiptFragment);

    void inject(MineFragment mineFragment);

    void inject(ViewPagerTransaction viewPagerTransaction);

}
