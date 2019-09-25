package com.shenjing.dengyuejinfu.net.services;



import com.shenjing.dengyuejinfu.entity.IncreaseQuotaInformationBean;
import com.shenjing.dengyuejinfu.entity.IncreaseQuotaBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/9/2416:27
 * version: 1.0
 * desc   :
 */
public interface IncreaseQuotaApi {
    /**
     * 获取首页课程秘籍
     * @return
     */
    @POST("/preview/title/type")
    Observable<IncreaseQuotaBean> getIncreaseData();

    /**
     * 获取资讯列表
     * @param userId
     * @param type
     * @param page
     * @return
     */
    @POST("/preview/title")
    @FormUrlEncoded
    Observable<IncreaseQuotaInformationBean> getIncreaseInformationData(@Field("userId") long userId, @Field("type") String type, @Field("page") int page);
}
