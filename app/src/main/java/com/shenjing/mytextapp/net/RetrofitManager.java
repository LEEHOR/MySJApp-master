package com.shenjing.mytextapp.net;


import androidx.annotation.NonNull;

import com.shenjing.mytextapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 13:32
 */
public class RetrofitManager {
    //连接超时
    private static long CONNECT_TIMEOUT = 60L;
    //阅读超时
    private static long READ_TIMEOUT = 30L;
    //写入超时
    private static long WRITE_TIMEOUT = 30L;

    private static volatile OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    //DEBUG模式下配Log参数拦截拦截器
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
                    builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
                    builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
                    builder.addInterceptor(new CommonParametersInterceptor());
                  //  builder.addInterceptor(new ReadCookiesInterceptor());
                  //  builder.addInterceptor(new SaveCookiesInterceptor());
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogging());
                        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(loggingInterceptor);
                    }
                    mOkHttpClient = builder.build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 获取android的service
     * 可以创建不同的Service对应不同的模块
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

}
