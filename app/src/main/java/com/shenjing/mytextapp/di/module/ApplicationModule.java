package com.shenjing.mytextapp.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }
}
