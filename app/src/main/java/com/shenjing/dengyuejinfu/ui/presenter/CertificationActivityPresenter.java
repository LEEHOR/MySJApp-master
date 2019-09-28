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
import okhttp3.ResponseBody;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public  class CertificationActivityPresenter extends BasePresenter<CertificationActivityContract.View>
        implements CertificationActivityContract.Presenter {

    @Inject
    public CertificationActivityPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadPeopleInfo(Map<String, Object> map) {
       /* map.put("address",pcb.getAddress());
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
        map.put("living_photo",pcb.getLiving_photo());*/
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

    @SuppressLint("CheckResult")
    @Override
    public void downLoadImg(String url, String fileName, int type) {
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
                                        mView.downLoadImgSuccess(file.getAbsolutePath(), file, type);
                                    } else {
                                        mView.downLoadImgFailure(type);
                                    }
                                }
                            }).start();

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            mView.downLoadImgFailure(type);
                        }
                    });
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

}
