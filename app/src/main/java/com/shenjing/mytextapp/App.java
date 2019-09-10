package com.shenjing.mytextapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import androidx.multidex.MultiDex;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.shenjing.mytextapp.di.component.ApplicationComponent;
import com.shenjing.mytextapp.di.component.DaggerApplicationComponent;
import com.shenjing.mytextapp.di.module.ApplicationModule;

public class App extends Application {
    public static Context mContext;
    public static Handler handler;
    public static int mainThreadId;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initApplicationComponent();
        intARouter();
        MultiDex.install(this);
        //配置ToastUtils的相关的属性
        Utils.init(this);
        ToastUtils.setGravity(Gravity.TOP, 0, (int) (80 * Utils.getApp().getResources().getDisplayMetrics().density + 0.5));
        ToastUtils.setBgColor(getResources().getColor(R.color.white, null));
        ToastUtils.setMsgColor(getResources().getColor(R.color.colorAccent, null));

        handler = new Handler(Looper.getMainLooper());
        mainThreadId = android.os.Process.myTid();//获取当前线程的id
    }

    private void intARouter() {

        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return mContext.getApplicationContext();
    }

    private void initApplicationComponent() {
        mApplicationComponent =
                DaggerApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(this)).build();
    }

    @Override
    public void onTerminate() {
        ARouter.getInstance().destroy();
        super.onTerminate();

    }
}
