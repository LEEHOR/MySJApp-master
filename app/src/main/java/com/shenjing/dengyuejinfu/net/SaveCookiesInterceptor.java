package com.shenjing.dengyuejinfu.net;

import com.blankj.utilcode.util.SPUtils;
import com.shenjing.dengyuejinfu.common.BaseParams;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * author : Leehor
 * date   : 2019/9/1611:08
 * version: 1.0
 * desc   : 保存cookie
 */
public class SaveCookiesInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SPUtils.getInstance().put(BaseParams.COOKIE_KEY,cookies,false);
        }

        return originalResponse;
    }
}
