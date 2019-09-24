package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.ChangePassModel;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.respondModule.LoginOutModel;
import com.shenjing.dengyuejinfu.respondModule.LostPassModel;
import com.shenjing.dengyuejinfu.respondModule.RegisterModel;
import com.shenjing.dengyuejinfu.respondModule.SmsModel;
import com.shenjing.dengyuejinfu.respondModule.VersionModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/9/129:08
 * version: 1.0
 * desc   :个人用户api 登录，注册，个人中心
 */
public interface UserApi {

    /**
     * 注册
     * @param map
     * @return
     */
    @POST("/regist/password/username")
    @FormUrlEncoded
    Observable<RegisterModel> register(@FieldMap Map<String,Object> map);

    /**
     * 账户密码登录
     * @param map
     * @return
     */
    @POST("/login/user/base/info")
    @FormUrlEncoded
    Observable<LoginModel> login_account(@FieldMap Map<String,Object> map);

    /**
     * 手机号验证码登录
     * @param map
     * @return
     */
    @POST("/login/phone/validata")
    @FormUrlEncoded
    Observable<LoginModel> login_phone(@FieldMap Map<String,Object> map);

    /**
     * 获取短信验证码
     * @param phoneNumber
     * @return
     */
    @POST("/sms")
    @FormUrlEncoded
    Observable<SmsModel> get_sms(@Field("phoneNumber") String phoneNumber);

    /**
     * 修改密码
     * @param map
     * @return
     */
    @POST("/changePwd")
    @FormUrlEncoded
    Observable<ChangePassModel> changePass(@FieldMap Map<String,Object> map);

    /**
     * 忘记密码
     * @param map
     * @return
     */
    @POST("/findPwd")
    @FormUrlEncoded
    Observable<LostPassModel> lostPass(@FieldMap Map<String,Object> map);

    /**
     * 获取短信验证码(忘记密码)
     * @param phoneNumber
     * @return
     */
    @POST("/sms")
    @FormUrlEncoded
    Observable<SmsModel> get_sms2(@Field("phoneNumber") String phoneNumber);

    /**
     * 退出登录
     * @param userid
     * @return
     */
    @POST("/logout")
    @FormUrlEncoded
    Observable<LoginOutModel> loginOut(@Field("userid") String userid);

    /**
     * 上传登录日志
     * @param map
     * @return
     */
    @POST("/saveUserDeviceLocation")
    @FormUrlEncoded
    Observable<BaseModel> uploadDeviceLocation(@FieldMap Map<String,Object> map);


    /**
     * 上传通讯录
     * @param userId
     * @param contacts
     * @return
     */
    @POST("/upLoadCallRecord")
    @FormUrlEncoded
    Observable<BaseModel> uploadCallRecord(@Field("userId") long userId,@Field("contacts") String contacts);

    /**
     * 获取版本更新
     * @return
     */
    @POST("/check/version")
    Observable<VersionModel> checkVersion();
}
