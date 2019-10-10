package com.shenjing.dengyuejinfu.di.component;

import android.app.Activity;
import com.shenjing.dengyuejinfu.di.module.DialogModule;
import com.shenjing.dengyuejinfu.di.scope.FragmentDialogScope;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.SelectBankDialogFragment;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.ShareDialogFragment;

import dagger.Component;

/**
 * author : Leehor
 * date   : 2019/9/2017:16
 * version: 1.0
 * desc   :
 */
@FragmentDialogScope
@Component(dependencies = ApplicationComponent.class,modules = DialogModule.class)
public interface DialogComponent {
    Activity getActivity();
    void inject(ShareDialogFragment shareDialogFragment);
    void inject(SelectBankDialogFragment selectBankDialogFragment);
}
