package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.CreditCardListBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/9/209:53
 * version: 1.0
 * desc   :信用卡
 */
public interface CreditCardApi {

    /**
     * 获取信用卡列表
     * setCreditCardStatus
     * @param userId
     * @return
     */
    @POST("/getCreditCard")
    @FormUrlEncoded
    Observable<CreditCardListBean> getCreditCardList(@Field("userId") long userId);

    /**
     * 设置信用卡状态
     * @param userId
     * @return
     */
    @POST("/setCreditCardStatus")
    @FormUrlEncoded
    Observable<BaseBean> setCreditCardStatus(@Field("userId") long userId, @Field("creditCardNo") String creditCardNo);
}
