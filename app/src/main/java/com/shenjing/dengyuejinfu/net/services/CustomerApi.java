package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.entity.CustomerDetail;
import com.shenjing.dengyuejinfu.entity.MyCustomerBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/11/710:49
 * version: 1.0
 * desc   :
 */
public interface CustomerApi {

    /**
     * 获取用户客户数据
     * @param userId
     * @return
     */

    @POST("/get/lay/total")
    @FormUrlEncoded
    Observable<MyCustomerBean> getCustomer(@Field("userId") long userId);

    /**
     * 获取用户客户数据
     * @param idString
     * @param status
     * @return
     */

    @POST("/get/lay/status")
    @FormUrlEncoded
    Observable<CustomerDetail> getCustomerDetail(@Field("idStrings") String idString, @Field("status") String  status);
}
