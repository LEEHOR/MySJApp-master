package com.shenjing.mytextapp.di.module;

import android.content.Context;

import com.shenjing.mytextapp.App;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext(){
        return mApplication.getApplicationContext();
    }
}
