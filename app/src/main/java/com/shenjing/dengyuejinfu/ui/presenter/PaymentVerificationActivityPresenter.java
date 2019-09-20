package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.PaymentModel;
import com.shenjing.dengyuejinfu.ui.contract.PaymentVerificationActivityContract;

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
public class PaymentVerificationActivityPresenter extends BasePresenter<PaymentVerificationActivityContract.View>
        implements PaymentVerificationActivityContract.Presenter {



    @Inject
    public PaymentVerificationActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getTakeStatus(String userId) {
        mView.showLoading();
        RetrofitManager.create(CertificationApi.class).getTakeImg(Long.parseLong(userId))
                .compose(mView.<PaymentModel>bindToLife())
                .compose(RxSchedulers.<PaymentModel>applySchedulers())
                .subscribe(new Consumer<PaymentModel>() {
                    @Override
                    public void accept(PaymentModel paymentModel)  {
                        mView.hideLoading();
                        if (paymentModel.getCode() != null && paymentModel.getCode().equals("0000")) {
                            mView.showSuccess(paymentModel.getMsg());
                            if (!StringUtils.isSpace(paymentModel.getData().getTakeImg())){
                                if (paymentModel.getData().getState().equals("9001")){
                                    mView.isCanUpLoad(false);
                                    mView.isCanNext(true);
                                    mView.isCanEditor(false);
                                } else if (paymentModel.getData().getState().equals("9002")){
                                    mView.isCanUpLoad(true);
                                    mView.isCanNext(false);
                                    mView.isCanEditor(true);
                                } else if (paymentModel.getData().getState().equals("9003")){
                                    mView.isCanUpLoad(false);
                                    mView.isCanNext(true);
                                    mView.isCanEditor(false);
                                }else if (paymentModel.getData().getState().equals("9004")){
                                    mView.isCanUpLoad(true);
                                    mView.isCanNext(false);
                                    mView.isCanEditor(true);
                                } else {
                                    mView.isCanEditor(true);
                                    mView.isCanNext(false);
                                    mView.isCanUpLoad(true);
                                }
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(true);
                                mView.isCanUpLoad(true);
                            }
                            mView.getTakeStatusSuccess(paymentModel);
                        } else {
                            mView.showFail(paymentModel.getMsg());
                            mView.getTakeStatusFailure();
                            mView.isCanEditor(false);
                            mView.isCanNext(false);
                            mView.isCanUpLoad(false);
                        }
                    }
                },this::loadStatusError);
    }

    private void loadStatusError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.isCanUpLoad(true);
    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadTake(Map<String, Object> map) {
        mView.showLoading("正在上传..");
        File faceImgFile=new File(map.get("takeImg").toString());
        RequestBody requestBody_faceFile  =RequestBody.create(faceImgFile, MediaType.parse("multipart/form-data"));
        RequestBody body=new MultipartBody.Builder()
                .addFormDataPart("userId",map.get("userId").toString())
                .addFormDataPart("takeImg",faceImgFile.getName(),requestBody_faceFile)
                .build();

        RetrofitManager.create(CertificationApi.class).upLoadTakeImag(body)
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

    private void loadUploadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.isCanUpLoad(true);
    }
}
