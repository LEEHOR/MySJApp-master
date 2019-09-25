package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.PeopleCertificationStatusBean;
import com.shenjing.dengyuejinfu.ui.contract.CertificationActivityContract;

import java.io.File;
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
        mView.showLoading("正在上传..");
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
                .compose(mView.<BaseBean>bindToLife())
                .compose(RxSchedulers.<BaseBean>applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean)  {
                        mView.hideLoading();
                        if (baseBean.getCode() != null && baseBean.getCode().equals("0000")) {
                            mView.showSuccess(baseBean.getMsg());
                            mView.upLoadSuccess();
                            mView.isCanNext(true);
                            mView.isCanUpLoad(false);
                            mView.isCanEditor(false);
                            LogUtils.d(baseBean.getCode());
                        } else {
                            LogUtils.d(baseBean.getCode());
                            mView.showFail(baseBean.getMsg());
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
                .compose(mView.<PeopleCertificationStatusBean>bindToLife())
                .compose(RxSchedulers.<PeopleCertificationStatusBean>applySchedulers())
                .subscribe(new Consumer<PeopleCertificationStatusBean>() {
                    @Override
                    public void accept(PeopleCertificationStatusBean peopleCertificationStatusBean)  {
                        mView.hideLoading();
                        if (peopleCertificationStatusBean.getCode() != null && peopleCertificationStatusBean.getCode().equals("0000")) {
                            mView.showSuccess(peopleCertificationStatusBean.getMsg());
                            if (peopleCertificationStatusBean.getData().getState().equals("9001")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (peopleCertificationStatusBean.getData().getState().equals("9002")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else if (peopleCertificationStatusBean.getData().getState().equals("9003")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            }else if (peopleCertificationStatusBean.getData().getState().equals("9004")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(false);
                                mView.isCanUpLoad(true);
                            }
                            mView.getStatusSuccess(peopleCertificationStatusBean);
                        } else {
                            mView.showFail(peopleCertificationStatusBean.getMsg());
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
