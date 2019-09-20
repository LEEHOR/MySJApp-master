package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.respondModule.AddCreditCardModel;
import com.shenjing.dengyuejinfu.respondModule.BankInfoModel;
import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.PaymentModel;
import com.shenjing.dengyuejinfu.respondModule.PeopleCertificationStatus;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/9/1814:37
 * version: 1.0
 * desc   :信用卡认证、实名认证 Api
 */
public interface CertificationApi {
    /**
     * 提交信用卡信息（信用卡认证）
     * @param map
     * @return
     */
    @POST("/uploadCreditCard")
    @FormUrlEncoded
    Observable<AddCreditCardModel> uploadCreditCardInfo(@FieldMap Map<String,Object> map);

    /**
     * 实名认证（身份证认证）
     */
    @POST("/act/upload/identity/card")
    Observable<BaseModel> uploadCreditPeople(@Body RequestBody requestBody);

    /**
     * getBankCardInfo
     * 实名认证（身份证认证）状态
     */
    @POST("/act/authentication/identity")
    @FormUrlEncoded
    Observable<PeopleCertificationStatus> getCreditPeopleStatus(@Field("userId") long userId);


    /**
     * 获取银行卡信息
     * @param userId
     * @return
     */
    @POST("/getBankCardInfo")
    @FormUrlEncoded
    Observable<BankInfoModel> getBankCardInfo(@Field("userId") long userId);

    /**
     * 银行卡认证
     * @param requestBody
     * @return
     * getTakeImg
     */
    @POST("/upLoadBankCardInfo")
    Observable<BaseModel> upLoadBankCardInfo(@Body RequestBody requestBody);


    /**
     * 获取手持身份证
     * @param userId
     * @return
     */
    @POST("/getTakeImg")
    @FormUrlEncoded
    Observable<PaymentModel> getTakeImg(@Field("userId") long userId);


    /**
     * 上传手持身份证
     * @param requestBody
     * @return
     */
    @POST("/upLoadTakeImg")
    Observable<BaseModel> upLoadTakeImag(@Body RequestBody requestBody);

}
