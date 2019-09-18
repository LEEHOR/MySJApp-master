package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.respondModule.BankInfoModel;
import com.shenjing.dengyuejinfu.respondModule.BaseModel;
import com.shenjing.dengyuejinfu.respondModule.PeopleCertificationStatus;
import com.shenjing.dengyuejinfu.ui.contract.BankCardCertificationActivityContract;

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
public class BankCardCertificationActivityPresenter extends BasePresenter<BankCardCertificationActivityContract.View>
        implements BankCardCertificationActivityContract.Presenter {
    @Inject
    public BankCardCertificationActivityPresenter() {
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBankInfo(String userId) {
        mView.showLoading();
        RetrofitManager.create(CertificationApi.class).getBankCardInfo(Long.parseLong(userId))
                .compose(mView.<BankInfoModel>bindToLife())
                .compose(RxSchedulers.<BankInfoModel>applySchedulers())
                .subscribe(new Consumer<BankInfoModel>() {
                    @Override
                    public void accept(BankInfoModel bankInfoModel)  {
                        mView.hideLoading();
                        if (bankInfoModel.getCode() != null && bankInfoModel.getCode().equals("0000")) {
                            mView.showSuccess(bankInfoModel.getMsg());
                            if (bankInfoModel.getFeasibility().equals("9001")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (bankInfoModel.getFeasibility().equals("9002")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else if (bankInfoModel.getFeasibility().equals("9003")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            }else if (bankInfoModel.getFeasibility().equals("9004")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(false);
                                mView.isCanUpLoad(true);
                            }
                            mView.getBankInfoSuccess(bankInfoModel);
                        } else {
                            mView.showFail(bankInfoModel.getMsg());
                            mView.getBankInfoFailure();
                            mView.isCanEditor(false);
                            mView.isCanNext(false);
                        }
                    }
                },this::loadStatusError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void upLoadBankCardInfo(Map<String, Object> map) {
        mView.showLoading();
        File bankCardFile=new File(map.get("bankCardImg").toString());
        RequestBody requestBody_bankCardFile  =RequestBody.create(bankCardFile, MediaType.parse("multipart/form-data"));
        RequestBody body=new MultipartBody.Builder()
                .addFormDataPart("phoneNumber",map.get("phoneNumber").toString())
                .addFormDataPart("bank",map.get("bank").toString())
                .addFormDataPart("bank",map.get("bank").toString())
                .addFormDataPart("userId",map.get("userId").toString())
                .addFormDataPart("bankCardImg",bankCardFile.getName(),requestBody_bankCardFile)
                .build();

        RetrofitManager.create(CertificationApi.class).upLoadBankCardInfo(body)
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

    private void loadStatusError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.isCanUpLoad(true);
    }
}
