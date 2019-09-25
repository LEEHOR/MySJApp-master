package com.shenjing.dengyuejinfu.di.module;

import android.app.Activity;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.shenjing.dengyuejinfu.di.scope.FragmentDialogScope;
import dagger.Module;
import dagger.Provides;

/**
 * author : Leehor
 * date   : 2019/9/2017:15
 * version: 1.0
 * desc   :
 */
@Module
public class DialogModule {
    private AppCompatDialogFragment mFragment;

    public DialogModule(AppCompatDialogFragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentDialogScope
    public Activity provideActivity() {
        return mFragment.getActivity();
    }
}
