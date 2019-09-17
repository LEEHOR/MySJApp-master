package com.shenjing.mytextapp.di.component;

import android.content.Context;

import com.shenjing.mytextapp.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getApplication();
}

