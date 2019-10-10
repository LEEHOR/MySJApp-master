package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.entity.AddCreditCardBean;
import com.shenjing.dengyuejinfu.entity.BankBean;
import com.shenjing.dengyuejinfu.entity.CityBean;
import com.shenjing.dengyuejinfu.entity.ProvinceBean;
import com.shenjing.dengyuejinfu.entity.SubBranchBank;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/10/916:34
 * version: 1.0
 * desc   :
 */
public interface SelectBankApi {
    /**
     * 获取总行列表
     * @return
     */
    @POST("/get/bank")
    Observable<List<BankBean>> getBank();

    /**
     * 获取省份
     * @return
     */
    @POST("/get/province")
    Observable<List<ProvinceBean>> getProvince();


    /**
     * 获取城市列表
     * @param provinceCode
     * @return
     */
    @POST("/get/city")
    @FormUrlEncoded
    Observable<List<CityBean>> getCity(@Field("parentCode") String provinceCode);

    /**
     * 获取支行
     * @param bankId
     * @param cityId
     * @return
     */
    @POST("/get/bank/branch")
    @FormUrlEncoded
    Observable<List<SubBranchBank>> getBranchBank(@Field("bankId") String bankId, @Field("cityId") String cityId);

}
