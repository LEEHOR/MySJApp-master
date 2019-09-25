package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.ChangePassBean;
import com.shenjing.dengyuejinfu.entity.LoginBean;
import com.shenjing.dengyuejinfu.entity.LoginOutBean;
import com.shenjing.dengyuejinfu.entity.LostPassBean;
import com.shenjing.dengyuejinfu.entity.RegisterBean;
import com.shenjing.dengyuejinfu.entity.SmsBean;
import com.shenjing.dengyuejinfu.entity.VersionBean;

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
    Observable<RegisterBean> register(@FieldMap Map<String,Object> map);

    /**
     * 账户密码登录
     * @param map
     * @return
     */
    @POST("/login/user/base/info")
    @FormUrlEncoded
    Observable<LoginBean> login_account(@FieldMap Map<String,Object> map);

    /**
     * 手机号验证码登录
     * @param map
     * @return
     */
    @POST("/login/phone/validata")
    @FormUrlEncoded
    Observable<LoginBean> login_phone(@FieldMap Map<String,Object> map);

    /**
     * 获取短信验证码
     * @param phoneNumber
     * @return
     */
    @POST("/sms")
    @FormUrlEncoded
    Observable<SmsBean> get_sms(@Field("phoneNumber") String phoneNumber);

    /**
     * 修改密码
     * @param map
     * @return
     */
    @POST("/changePwd")
    @FormUrlEncoded
    Observable<ChangePassBean> changePass(@FieldMap Map<String,Object> map);

    /**
     * 忘记密码
     * @param map
     * @return
     */
    @POST("/findPwd")
    @FormUrlEncoded
    Observable<LostPassBean> lostPass(@FieldMap Map<String,Object> map);

    /**
     * 获取短信验证码(忘记密码)
     * @param phoneNumber
     * @return
     */
    @POST("/sms")
    @FormUrlEncoded
    Observable<SmsBean> get_sms2(@Field("phoneNumber") String phoneNumber);

    /**
     * 退出登录
     * @param userid
     * @return
     */
    @POST("/logout")
    @FormUrlEncoded
    Observable<LoginOutBean> loginOut(@Field("userid") String userid);

    /**
     * 上传登录日志
     * @param map
     * @return
     */
    @POST("/saveUserDeviceLocation")
    @FormUrlEncoded
    Observable<BaseBean> uploadDeviceLocation(@FieldMap Map<String,Object> map);


    /**
     * 上传通讯录
     * @param userId
     * @param contacts
     * @return
     */
    @POST("/upLoadCallRecord")
    @FormUrlEncoded
    Observable<BaseBean> uploadCallRecord(@Field("userId") long userId, @Field("contacts") String contacts);

    /**
     * 获取版本更新
     * @return
     */
    @POST("/check/version")
    Observable<VersionBean> checkVersion();
}
