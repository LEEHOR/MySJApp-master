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
                    public void accept(BankInfoBean bankInfoBean)  {
                        mView.hideLoading();
                        if (bankInfoBean.getCode() != null && bankInfoBean.getCode().equals("0000")) {
                            mView.showSuccess(bankInfoBean.getMsg());
                            if (bankInfoBean.getData().getState().equals("9001")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            } else if (bankInfoBean.getData().getState().equals("9002")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else if (bankInfoBean.getData().getState().equals("9003")){
                                mView.isCanUpLoad(false);
                                mView.isCanNext(true);
                                mView.isCanEditor(false);
                            }else if (bankInfoBean.getData().getState().equals("9004")){
                                mView.isCanUpLoad(true);
                                mView.isCanNext(false);
                                mView.isCanEditor(true);
                            } else {
                                mView.isCanEditor(true);
                                mView.isCanNext(false);
                                mView.isCanUpLoad(true);
                            }
                            mView.getBankInfoSuccess(bankInfoBean);
                        } else {
                            mView.showFail(bankInfoBean.getMsg());
                            mView.getBankInfoFailure();
                            mView.isCanEditor(false);
                            mView.isCanNext(false);
                            mView.isCanUpLoad(false);
                        }
                    }
                },this::loadStatusError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void upLoadBankCardInfo(Map<String, Object> map) {
        mView.showLoading("正在上传..");
        File bankCardFile=new File(map.get("bankCardImg").toString());
        RequestBody requestBody_bankCardFile  =RequestBody.create(bankCardFile, MediaType.parse("multipart/form-data"));
        RequestBody body=new MultipartBody.Builder()
                .addFormDataPart("phoneNumber",map.get("phoneNumber").toString())
                .addFormDataPart("bank","天地银行")
                .addFormDataPart("bankCardNo",map.get("bankCardNo").toString())
                .addFormDataPart("userId",map.get("userId").toString())
                .addFormDataPart("bankCardImg",bankCardFile.getName(),requestBody_bankCardFile)
                .build();

        RetrofitManager.create(CertificationApi.class).upLoadBankCardInfo(body)
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

    @SuppressLint("CheckResult")
    @Override
    public void DownLoadImg(String url, String fileName) {
        if (!FileUtils.createOrExistsDir(Constant.SAVE_DIR_YOUDUN)) {
            LogUtils.d(R.string.toast_12);
            return;
        }
        File file = new File(Constant.SAVE_DIR_YOUDUN, TimeUtils.millis2String(System.currentTimeMillis())+"_"+fileName);
        if (FileUtils.createFileByDeleteOldFile(file)) {
            RetrofitManager.DownLoadFile(url)
                    .compose(mView.<ResponseBody>bindToLife())
                    .compose(RxSchedulers.<ResponseBody>applySchedulers())
                    .subscribe(new Consumer<ResponseBody>() {
                        @Override
                        public void accept(ResponseBody o) throws Exception {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    boolean b = com.shenjing.dengyuejinfu.utils.FileUtils.writeResponseBodyToDisk(o, file);
                                    if (b) {
                                        mView.downLoadImgSuccess(file.getAbsolutePath(), file);
                                    } else {
                                        mView.downLoadImgFailure();
                                    }
                                }
                            }).start();

                        }
                    },this::downLoadError);
        }
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
    private void downLoadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.downLoadImgFailure();
    }
}
