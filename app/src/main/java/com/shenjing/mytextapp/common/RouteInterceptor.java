package com.shenjing.mytextapp.common;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;

/**
 * author : Leehor
 * date   : 2019/8/129:32
 * version: 1.0
 * desc   : 登录拦截器
 */
@Interceptor(priority = 1, name = "login")
public class RouteInterceptor implements IInterceptor {
    private Context context;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        SPUtils spUtils = SPUtils.getInstance();
        String user_name = spUtils.getString(BaseParams.USER_NAME_KEY, null);
        String user_token = spUtils.getString(BaseParams.USER_TOKEN_KEY, null);
        if (!StringUtils.isSpace(user_name) && !StringUtils.isSpace(user_token)) {  //已经登录
            callback.onContinue(postcard);
        } else {
            switch (path) {
                case ARouterUrl.LoginActivityUrl:  // 不需要登录的直接进入这个页面
                case ARouterUrl.MainActivityUrl:
                    callback.onContinue(postcard);
                    break;
                default:
                    callback.onInterrupt(null);//需要登陆的直接拦截
                    break;
            }
        }
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
