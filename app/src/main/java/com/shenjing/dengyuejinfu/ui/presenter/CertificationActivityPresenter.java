package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;
import android.os.Environment;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.net.services.UserApi;
import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.LoginModel;
import com.shenjing.dengyuejinfu.respondModule.PeopleCertificationStatus;
import com.shenjing.dengyuejinfu.ui.contract.CertificationActivityContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    @SuppressLint("CheckResult")
    @Override
    public void uploadPeopleInfo(Map<String, Object> map) {
        mView.showLoading();
        File back=new File(map.get("back").toString());
        RequestBody requestBody_back  =RequestBody.create(back,MediaType.parse("multipart/form-data"));
        File fornt=new File(map.get("fornt").toString());
        RequestBody requestBody_fornt  =RequestBody.create(fornt,MediaType.parse("multipart/form-data"));
     //   MultipartBody.Part part_back = MultipartBody.Part.createFormData("back", back.getName(), requestBody_back);
        RequestBody body=new MultipartBody.Builder()
                .addFormDataPart("realName",map.get("realName").toString())
                .addFormDataPart("idNo",map.get("idNo").toString())
                .addFormDataPart("userIds",map.get("userId").toString())
                .addFormDataPart("fornt",fornt.getName(),requestBody_fornt)
                .addFormDataPart("back",back.getName(),requestBody_back)
                .build();

        RetrofitManager.create(CertificationApi.class).uploadCreditPeople(body)
                .compose(mView.<BaseModel>bindToLife())
                .compose(RxSchedulers.<BaseModel>applySchedulers())
                .subscribe(new Consumer<BaseModel>() {
                    @Override
                    public void accept(BaseModel baseModel)  {
                        mView.hideLoading();
                        if (baseModel.getCode() != null && baseModel.getCode().equals("0000")) {
                            mView.showSuccess(baseModel.getMsg());
                            mView.upLoadSuccess();
                            mView.isCanNext(true);
                            mView.isCanUpLoad(false);
                            mView.isCanEditor(false);
                        } else {
                            mView.showFail(baseModel.getMsg());
                            mView.upLoadFailure();
                            mView.isCanNext(false);
                            mView.isCanUpLoad(true);
                            mView.isCanEditor(true);
                        }
                    }
                },this::loadUploadError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPeopleStatus(String userId) {
        mView.showLoading();
        RetrofitManager.create(CertificationApi.class).getCreditPeopleStatus(Long.parseLong(userId))
                .compose(mView.<PeopleCertificationStatus>bindToLife())
                .compose(RxSchedulers.<PeopleCertificationStatus>applySchedulers())
                .subscribe(new Consumer<PeopleCertificationStatus>() {
                    @Override
                    public void accept(PeopleCertificationStatus peopleCertificationStatus)  {
                        mView.hideLoading();
                        if (peopleCertificationStatus.getCode() != null && peopleCertificationStatus.getCode().equals("0000")) {
                            mView.showSuccess(peopleCertificationStatus.getMsg());
                            if (peopleCertificationStatus.getData().getState().equals("9001")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (peopleCertificationStatus.getData().getState().equals("9002")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else if (peopleCertificationStatus.getData().getState().equals("9003")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            }else if (peopleCertificationStatus.getData().getState().equals("9004")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(false);
                                mView.isCanUpLoad(true);
                            }
                            mView.getStatusSuccess(peopleCertificationStatus);
                        } else {
                            mView.showFail(peopleCertificationStatus.getMsg());
                            mView.getStatusFailure();
                            mView.isCanEditor(false);
                            mView.isCanNext(false);
                        }
                    }
                },this::loadStatusError);
    }

    private void loadUploadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.isCanUpLoad(true);
    }

    private void loadStatusError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.isCanUpLoad(true);
    }

}
