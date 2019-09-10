package com.shenjing.mytextapp.di.module;

import android.app.Activity;
import android.content.Context;

import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;



@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }
    @PerActivity
    @Provides
    @ContextLife("Activity")
    public Context provideActivityContext(){
        return mActivity;
    }
    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
