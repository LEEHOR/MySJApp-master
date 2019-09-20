package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.respondModule.QRModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/9/2015:08
 * version: 1.0
 * desc   :
 */
public interface ShareApi {
    /**
     * 获取二维码
     * @param userId
     * @return
     */
    @POST("/share/qr/code")
    @FormUrlEncoded
    Observable<QRModel> getCode(@Field("userId") long userId);

}
