package com.shenjing.mytextapp.di.component;

import android.app.Activity;
import android.content.Context;

import com.shenjing.mytextapp.di.module.FragmentModule;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.di.scope.PerFragment;
import com.shenjing.mytextapp.ui.fragment.IndexFragment;
import com.shenjing.mytextapp.ui.fragment.MemberFragment;
import com.shenjing.mytextapp.ui.fragment.MineFragment;
import com.shenjing.mytextapp.ui.fragment.ReceiptFragment;
import com.shenjing.mytextapp.ui.fragment.ReplacementFragment;
import com.shenjing.mytextapp.ui.fragment.ViewPagerTransaction;

import dagger.Component;


@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getAcitivtyContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getAcitivty();

    void inject(IndexFragment indexFragment);

    void inject(ReplacementFragment replacementFragment);

    void inject(MemberFragment memberFragment);

    void inject(ReceiptFragment receiptFragment);

    void inject(MineFragment mineFragment);

    void inject(ViewPagerTransaction viewPagerTransaction);

}
