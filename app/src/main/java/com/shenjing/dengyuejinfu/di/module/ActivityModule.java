package com.shenjing.dengyuejinfu.di.module;

import android.app.Activity;

import com.shenjing.dengyuejinfu.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;



@Module
public class ActivityModule {

private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
