package com.shenjing.mytextapp.di.module;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }
    @PerFragment
    @Provides
    @ContextLife("Activity")
    public Context provideActivityContext(){
        return mFragment.getActivity();
    }
    @Provides
    @PerFragment
    public Activity provideActivity(){
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment(){
        return mFragment;
    }
}
