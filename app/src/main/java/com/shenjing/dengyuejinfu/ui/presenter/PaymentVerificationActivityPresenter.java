package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.PaymentBean;
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
                .compose(mView.<PaymentBean>bindToLife())
                .compose(RxSchedulers.<PaymentBean>applySchedulers())
                .subscribe(new Consumer<PaymentBean>() {
                    @Override
                    public void accept(PaymentBean paymentBean)  {
                        mView.hideLoading();
                        if (paymentBean.getCode() != null && paymentBean.getCode().equals("0000")) {
                            mView.showSuccess(paymentBean.getMsg());
                            if (!StringUtils.isSpace(paymentBean.getData().getTakeImg())){
                                if (paymentBean.getData().getState().equals("9001")){
                                    mView.isCanUpLoad(false);
                                    mView.isCanNext(true);
                                    mView.isCanEditor(false);
                                } else if (paymentBean.getData().getState().equals("9002")){
                                    mView.isCanUpLoad(true);
                                    mView.isCanNext(false);
                                    mView.isCanEditor(true);
                                } else if (paymentBean.getData().getState().equals("9003")){
                                    mView.isCanUpLoad(false);
                                    mView.isCanNext(true);
                                    mView.isCanEditor(false);
                                }else if (paymentBean.getData().getState().equals("9004")){
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
                            mView.getTakeStatusSuccess(paymentBean);
                        } else {
                            mView.showFail(paymentBean.getMsg());
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
     //   RequestBody requestBody_faceFile  =RequestBody.create(faceImgFile, MediaType.parse("multipart/form-data"));
        RequestBody body=new MultipartBody.Builder()
                .addFormDataPart("userId",map.get("userId").toString())
           //     .addFormDataPart("takeImg",faceImgFile.getName(),requestBody_faceFile)
                .build();

        RetrofitManager.create(CertificationApi.class).upLoadTakeImag(body)
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
                        } else {
                            mView.showFail(baseBean.getMsg());
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
