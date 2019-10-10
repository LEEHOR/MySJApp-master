package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.common.Constant;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.CertificationApi;
import com.shenjing.dengyuejinfu.entity.BankInfoBean;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.ui.contract.BankCardCertificationActivityContract;

import java.io.File;
import java.util.Map;
import java.util.Objects;

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
                .compose(mView.<BankInfoBean>bindToLife())
                .compose(RxSchedulers.<BankInfoBean>applySchedulers())
                .subscribe(new Consumer<BankInfoBean>() {
                    @Override
                    public void accept(BankInfoBean bankInfoBean) {
                        mView.hideLoading();
                        if (bankInfoBean.getCode() != null && bankInfoBean.getCode().equals("0000")) {
                            mView.showSuccess(bankInfoBean.getMsg());
                            if (bankInfoBean.getData().getState().equals("9001")) {
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                                mView.submit().setText("完成");
                            } else if (bankInfoBean.getData().getState().equals("9002")) {
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                                mView.submit().setText("上传");
                            } else if (bankInfoBean.getData().getState().equals("9003")) {
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                                mView.submit().setText("完成");
                            } else if (bankInfoBean.getData().getState().equals("9004")) {
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                                mView.submit().setText("上传");
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(false);
                                mView.isCanUpLoad(true);
                                mView.submit().setText("上传");
                            }
                            mView.getBankInfoSuccess(bankInfoBean);
                        } else {
                            mView.showFail(bankInfoBean.getMsg());
                            mView.getBankInfoFailure();
                            mView.isCanEditor(false);
                            mView.isCanNext(false);
                            mView.isCanUpLoad(false);
                            mView.submit().setText("返回");
                        }
                    }
                }, this::loadStatusError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void upLoadBankCardInfo(Map<String, String> map) {
        mView.showLoading("正在上传..");
        File bankCardFile = new File(Objects.requireNonNull(map.get("bankCardImg")));
        RequestBody requestBody_bankCardFile = RequestBody.create(bankCardFile, MediaType.parse("multipart/form-data"));
        RequestBody body = new MultipartBody.Builder()
                .addFormDataPart("phoneNumber", Objects.requireNonNull(map.get("phoneNumber")))
                .addFormDataPart("bank", Objects.requireNonNull(map.get("bankName")))
                .addFormDataPart("bankCardNo", Objects.requireNonNull(map.get("bankCardNo")))
                .addFormDataPart("userId", Objects.requireNonNull(map.get("userId")))
                .addFormDataPart("bankBranchName", Objects.requireNonNull(map.get("bankBranchName")))
                .addFormDataPart("address", Objects.requireNonNull(map.get("address")))
                .addFormDataPart("bankCardImgFile", bankCardFile.getName(), requestBody_bankCardFile)
                .build();

        RetrofitManager.create(CertificationApi.class).upLoadBankCardInfo(body)
                .compose(mView.<BaseBean>bindToLife())
                .compose(RxSchedulers.<BaseBean>applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) {
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
                }, this::loadUploadError);
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
        mView.isCanUpLoad(false);
        mView.submit().setText("返回");
    }

}
