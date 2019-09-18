package com.shenjing.dengyuejinfu.net;

import com.blankj.utilcode.util.SPUtils;
import com.shenjing.dengyuejinfu.common.BaseParams;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : Leehor
 * date   : 2019/9/1611:04
 * version: 1.0
 * desc   : 添加请cookie
 */
public class ReadCookiesInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Set<String> stringSet = SPUtils.getInstance().getStringSet(BaseParams.COOKIE_KEY,new HashSet<>());
        for (String cookie : stringSet) {
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }
}
