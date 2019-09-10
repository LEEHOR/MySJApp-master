package com.shenjing.mytextapp.common;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;

/**
 * author : Leehor
 * date   : 2019/8/129:45
 * version: 1.0
 * desc   :登录拦截回调
 */
public class LoginNavigationCallback implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {
        LogUtils.d("路由", postcard.getPath(), "路由onFound");
    }

    @Override
    public void onLost(Postcard postcard) {
        LogUtils.d("路由", postcard.getPath(), "路由onLost");
    }

    @Override
    public void onArrival(Postcard postcard) {
        LogUtils.d("路由", postcard.getPath(), "路由onArrival");
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        LogUtils.d("路由", postcard.getPath(), "路由onInterrupt");
        String path = postcard.getPath();
        Bundle bundle = postcard.getExtras();
        //拦截下来的页面
        //需要调转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
        ARouter.getInstance().build(ARouterUrl.LoginActivityUrl)
                .with(bundle)
                .withString(BaseParams.RouterPath, path)
                .navigation();
    }
}
