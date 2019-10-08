package com.shenjing.dengyuejinfu.net;


import com.shenjing.dengyuejinfu.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
    private static long CONNECT_TIMEOUT = 30L;
    //阅读超时
    private static long READ_TIMEOUT = 30L;
    //写入超时
    private static long WRITE_TIMEOUT = 30L;

    private static volatile OkHttpClient mOkHttpClient;
    private static volatile OkHttpClient DOkHttpClient;
    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
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
     * 接口请求的 Retrofit
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

    /**
     * 测试
     * @param clazz
     * @param baseUrl
     * @param <T>
     * @return
     */
    public static <T> T createTest(Class<T> clazz,String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);

    }
    /**
     * 下载的
     * @return
     */
    public static OkHttpClient getDownLoadOkHttpClient(){
        if (DOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (DOkHttpClient == null) {
                    //DEBUG模式下配Log参数拦截拦截器
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
                    builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
                    builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogging());
                        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(loggingInterceptor);
                    }
                    DOkHttpClient = builder.build();
                }
            }
        }
        return DOkHttpClient;
    }

    /**
     * 下载的请求
     * @param url
     *          文件地址
     * @return
     */
    public static Observable <ResponseBody> DownLoadFile(String url){
     return Observable.create(new ObservableOnSubscribe<ResponseBody>() {
            @Override
            public void subscribe(ObservableEmitter<ResponseBody> emitter) {
                Request request=new Request.Builder().url(url).get().build();
                OkHttpClient okHttpClient=getDownLoadOkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response){
                        emitter.onNext(response.body());
                    }
                });
            }
        }) .observeOn(AndroidSchedulers.mainThread())// 指定 最后拿到数据操，解析，显示发生在主线程
             .subscribeOn(Schedulers.io());// 指定 网络请求耗时操作发生在子线程;
    }



}
