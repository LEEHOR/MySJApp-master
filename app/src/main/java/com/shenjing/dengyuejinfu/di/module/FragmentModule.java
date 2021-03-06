package com.shenjing.dengyuejinfu.di.module;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.shenjing.dengyuejinfu.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {
private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return mFragment.getActivity();
    }
}
