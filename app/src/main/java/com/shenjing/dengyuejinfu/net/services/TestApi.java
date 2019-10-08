package com.shenjing.dengyuejinfu.net.services;


import com.shenjing.dengyuejinfu.entity.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/9/299:06
 * version: 1.0
 * desc   :
 */
public interface TestApi {

    @POST("/test/head")
    Observable<BaseBean> testNet();
}
