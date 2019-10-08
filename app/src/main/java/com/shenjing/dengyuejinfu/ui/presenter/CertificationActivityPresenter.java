package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.common.Constant;
import com.shenjing.dengyuejinfu.entity.AliyunNotificationBean;
import com.shenjing.dengyuejinfu.entity.AliyunToken;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.PeopleCertificationStatusBean;
import com.shenjing.dengyuejinfu.ui.contract.CertificationActivityContract;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class CertificationActivityPresenter extends BasePresenter<CertificationActivityContract.View>
        implements CertificationActivityContract.Presenter {

    @Inject
    public CertificationActivityPresenter() {

    }

/*    @SuppressLint("CheckResult")
    @Override
    public void uploadPeopleInfo(Map<String, Object> map) {
       *//* map.put("address",pcb.getAddress());
        map.put("age",pcb.getAge());
        map.put("birthday",pcb.getBirthday());
        map.put("id_name",pcb.getId_name());
        map.put("id_number",pcb.getId_number());
        map.put("gender",pcb.getGender());
        map.put("nation",pcb.getNation());
        map.put("idcard_back_photo",pcb.getIdcard_back_photo());
        map.put("idcard_front_photo",pcb.getIdcard_front_photo());
        map.put("idcard_portrait_photo",pcb.getIdcard_portrait_photo());
        map.put("issuing_authority",pcb.getIssuing_authority());
        map.put("validity_period",pcb.getValidity_period());
        map.put("validity_period_expired",pcb.getValidity_period_expired());
        map.put("classify",pcb.getClassify());
        map.put("score",pcb.getScore());
        map.put("living_photo",pcb.getLiving_photo());*//*
        mView.showLoading("正在上传..");
        //身份证国徽面
        File file_back=new File(map.get("idcard_back_photo").toString());
        RequestBody requestBody_back  =RequestBody.create(file_back,MediaType.parse("multipart/form-data"));
        //身份证人像面
        File file_front=new File(map.get("idcard_front_photo").toString());
        RequestBody requestBody_front  =RequestBody.create(file_front,MediaType.parse("multipart/form-data"));
        //身份证头像
        File file_portrait=new File(map.get("idcard_portrait_photo").toString());
        RequestBody requestBody_portrait  =RequestBody.create(file_portrait,MediaType.parse("multipart/form-data"));
        //活体照片
        File file_living=new File(map.get("living_photo").toString());
        RequestBody requestBody_living  =RequestBody.create(file_living,MediaType.parse("multipart/form-data"));

        RequestBody body=new MultipartBody.Builder()
                .addFormDataPart("realName",map.get("id_name").toString())
                .addFormDataPart("idNo",map.get("id_number").toString())
                .addFormDataPart("idAddress",map.get("address").toString())
                .addFormDataPart("age",map.get("age").toString())
                .addFormDataPart("gender",map.get("gender").toString())
                .addFormDataPart("nation",map.get("nation").toString())
                .addFormDataPart("birthday",map.get("birthday").toString())
                .addFormDataPart("issuingAuthority",map.get("issuing_authority").toString())
                .addFormDataPart("validityPeriod",map.get("validity_period").toString())
                .addFormDataPart("validityPeriodExpired",map.get("validity_period_expired").toString())
                //.addFormDataPart("classify",map.get("classify").toString())
                .addFormDataPart("score",map.get("score").toString())
                .addFormDataPart("fornt",file_front.getName(),requestBody_front)
                .addFormDataPart("back",file_back.getName(),requestBody_back)
                .addFormDataPart("head",file_portrait.getName(),requestBody_portrait)
                .addFormDataPart("takeImage",file_living.getName(),requestBody_living)
                .addFormDataPart("userIds",map.get("userId").toString())
                .build();

        RetrofitManager.create(CertificationApi.class).uploadCreditPeople(body)
                .compose(mView.<BaseBean>bindToLife())
                .compose(RxSchedulers.<BaseBean>applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean)  {
                        mView.hideLoading();
                        if (baseBean.getCode() != null && baseBean.getCode().equals("0000")) {
                            mView.showSuccess(baseBean.getMsg());
                            mView.isCanNext(true);
                            mView.isCanUpLoad(false);
                            mView.isCanEditor(false);
                            LogUtils.d(baseBean.getCode());
                        } else {
                            LogUtils.d(baseBean.getCode());
                            mView.showFail(baseBean.getMsg());
                            mView.isCanNext(false);
                            mView.isCanUpLoad(true);
                            mView.isCanEditor(true);
                        }
                    }
                },this::loadUploadError);
    }*/

    @SuppressLint("CheckResult")
    @Override
    public void verifyToken(String userId) {
        RetrofitManager.create(CertificationApi.class).getAliToken(Long.parseLong(userId))
                .compose(mView.bindToLife())
                .compose(RxSchedulers.applySchedulers())
                .subscribe(new Consumer<AliyunToken>() {
                    @Override
                    public void accept(AliyunToken aliyunToken) throws Exception {
                        if (aliyunToken.getCode() != null && aliyunToken.getCode().equals("0000")) {
                            if (aliyunToken.getData() != null && aliyunToken.getData().getToken() != null) {
                                mView.getVerifyTokenSuccess(aliyunToken.getData().getToken());
                            } else {
                                mView.getVerifyTokenFailure();
                            }

                        } else {
                            mView.getVerifyTokenFailure();
                        }
                    }
                }, this::tokenError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void sendOcrResult(String userId, String result, String token) {
        mView.showLoading();
        RetrofitManager.create(CertificationApi.class).sendAliyunNotification(Long.parseLong(userId), result, token)
                .compose(mView.<AliyunNotificationBean>bindToLife())
                .compose(RxSchedulers.<AliyunNotificationBean>applySchedulers())
                .subscribe(new Consumer<AliyunNotificationBean>() {
                    @Override
                    public void accept(AliyunNotificationBean notificationBean) {
                        mView.hideLoading();
                        if (notificationBean.getCode() != null && notificationBean.getCode().equals("0000")) {
                            mView.showSuccess(notificationBean.getMsg());
                            if (notificationBean.getData().getState().equals("9001")) {
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (notificationBean.getData().getState().equals("9002")) {
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else if (notificationBean.getData().getState().equals("9003")) {
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (notificationBean.getData().getState().equals("9004")) {
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(false);
                            }
                            mView.getOcrResultSuccess();
                        } else {
                            mView.showFail(notificationBean.getMsg());
                            mView.getOcrResultFailure();
                            mView.isCanEditor(false);
                            mView.isCanNext(false);
                        }
                    }
                }, this::loadStatusError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPeopleStatus(String userId) {
        mView.showLoading();
        RetrofitManager.create(CertificationApi.class).getCreditPeopleStatus(Long.parseLong(userId))
                .compose(mView.<PeopleCertificationStatusBean>bindToLife())
                .compose(RxSchedulers.<PeopleCertificationStatusBean>applySchedulers())
                .subscribe(new Consumer<PeopleCertificationStatusBean>() {
                    @Override
                    public void accept(PeopleCertificationStatusBean peopleCertificationStatusBean) {
                        mView.hideLoading();
                        if (peopleCertificationStatusBean.getCode() != null && peopleCertificationStatusBean.getCode().equals("0000")) {
                            mView.showSuccess(peopleCertificationStatusBean.getMsg());
                            if (peopleCertificationStatusBean.getData().getState().equals("9001")) {
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (peopleCertificationStatusBean.getData().getState().equals("9002")) {
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else if (peopleCertificationStatusBean.getData().getState().equals("9003")) {
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (peopleCertificationStatusBean.getData().getState().equals("9004")) {
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(false);
                            }
                            mView.getStatusSuccess(peopleCertificationStatusBean);
                        } else {
                            mView.showFail(peopleCertificationStatusBean.getMsg());
                            mView.getStatusFailure();
                            mView.isCanEditor(false);
                            mView.isCanNext(false);
                        }
                    }
                }, this::loadStatusError);
    }


    private void loadUploadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
    }

    private void loadStatusError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.getStatusFailure();
    }

    private void tokenError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.getVerifyTokenFailure();
    }

}
