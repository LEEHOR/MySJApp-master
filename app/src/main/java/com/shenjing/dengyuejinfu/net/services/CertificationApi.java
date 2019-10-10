package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.entity.AddCreditCardBean;
import com.shenjing.dengyuejinfu.entity.AliyunNotificationBean;
import com.shenjing.dengyuejinfu.entity.AliyunToken;
import com.shenjing.dengyuejinfu.entity.BankInfoBean;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.PaymentBean;
import com.shenjing.dengyuejinfu.entity.PeopleCertificationStatusBean;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

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
    Observable<AddCreditCardBean> uploadCreditCardInfo(@FieldMap Map<String,Object> map);

    /**
     * 获取认证状态（身份证认证）状态
     */
    @POST("/authentication/identity")
    @FormUrlEncoded
    Observable<PeopleCertificationStatusBean> getCreditPeopleStatus(@Field("userId") long userId);


    /**
     * 获取阿里云认证token
     * @param userId
     * @return
     */
    @POST("/upload/identity/token")
    @FormUrlEncoded
    Observable<AliyunToken> getAliToken(@Field("userId") long userId);


    /**
     * 阿里云Ocr结果通知
     * @param userId
     * @param result
     * @return
     */
    @POST("/upload/identity/notification")
    @FormUrlEncoded
    Observable<AliyunNotificationBean> sendAliyunNotification(@Field("userId") long userId, @Field("result") String result,@Field("token")String token);


    /**
     * 获取银行卡信息
     * @param userId
     * @return
     */
    @POST("/getBankCardInfo")
    @FormUrlEncoded
    Observable<BankInfoBean> getBankCardInfo(@Field("userId") long userId);

    /**
     * 银行卡认证
     * @param requestBody
     * @return
     * getTakeImg
     */
   @POST("/upload/BankCardInfo")
    Observable<BaseBean> upLoadBankCardInfo(@Body RequestBody requestBody);

    /**
     * 获取手持身份证
     * @param userId
     * @return
     */
    @POST("/getTakeImg")
    @FormUrlEncoded
    Observable<PaymentBean> getTakeImg(@Field("userId") long userId);


    /**
     * 上传手持身份证
     * @param requestBody
     * @return
     */
    @POST("/upLoadTakeImg")
    Observable<BaseBean> upLoadTakeImg(@Body RequestBody requestBody);


}
