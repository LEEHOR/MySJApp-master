package com.shenjing.mytextapp.di.component;

import android.content.Context;

import com.shenjing.mytextapp.di.module.ApplicationModule;
import com.shenjing.mytextapp.di.scope.ContextLife;
import com.shenjing.mytextapp.di.scope.PerApp;

import dagger.Component;


@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}