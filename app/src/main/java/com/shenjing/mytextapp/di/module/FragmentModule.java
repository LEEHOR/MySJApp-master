package com.shenjing.mytextapp.di.module;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.shenjing.mytextapp.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {
//    private Fragment mFragment;
//
//    public FragmentModule(Fragment fragment) {
//        mFragment = fragment;
//    }
//    @PerFragment
//    @Provides
//    @ContextLife("Activity")
//    public Context provideActivityContext(){
//        return mFragment.getActivity();
//    }
//    @Provides
//    @PerFragment
//    public Activity provideActivity(){
//        return mFragment.getActivity();
//    }
//
//    @Provides
//    @PerFragment
//    public Fragment provideFragment(){
//        return mFragment;
//    }
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
