package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.respondModule.AddCreditCardModel;
import com.shenjing.dengyuejinfu.respondModule.BankInfoModel;
import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.PeopleCertificationStatus;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
     */
    @POST("/upLoadBankCardInfo")
    Observable<BaseModel> upLoadBankCardInfo(@Body RequestBody requestBody);


}
