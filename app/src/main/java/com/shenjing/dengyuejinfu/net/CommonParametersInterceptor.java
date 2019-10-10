package com.shenjing.dengyuejinfu.net;

import com.shenjing.dengyuejinfu.common.BaseParams;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : Leehor
 * date   : 2019/9/1715:58
 * version: 1.0
 * desc   :添加公共的请求头
 */
public class CommonParametersInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .addHeader("Accept-Charset","utf-8")
                .addHeader("Transfer-Encoding","chunked")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("Token", BaseParams.userToken)
                .addHeader("user-agent","Android")
                .build();
        return chain.proceed(request);
    }
}
