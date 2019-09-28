package com.shenjing.dengyuejinfu.ui.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.leehor.simple.youdun.YouDunHelper;
import com.leehor.simple.youdun.YouDunListener;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.entity.PeopleCertificationBean;
import com.shenjing.dengyuejinfu.entity.PeopleCertificationStatusBean;
import com.shenjing.dengyuejinfu.ui.contract.CertificationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.CertificationActivityPresenter;
import com.shenjing.dengyuejinfu.utils.GlideUtils;
import com.shenjing.dengyuejinfu.utils.YouDunOrderIdUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * author : Leehor
 * date   : 2019/8/1418:42
 * version: 1.0
 * desc   :身份认证
 */
@Route(path = ARouterUrl.CertificationActivityUrl)
public class CertificationActivity extends BaseActivity<CertificationActivityPresenter>
        implements CertificationActivityContract.View, YouDunListener {
    @BindView(R.id.certification_mStatusBar)
    View certificationMStatusBar;
    @BindView(R.id.certification_titleBar)
    TitleBar certificationTitleBar;
    @BindView(R.id.certification_idCard_front)
    ImageView certificationIdCardFront;
    @BindView(R.id.take_photo_id_front)
    ImageView takePhotoIdFront;
    @BindView(R.id.certification_idCard_back)
    ImageView certificationIdCardBack;
    @BindView(R.id.take_photo_id_back)
    ImageView takePhotoIdBack;
    @BindView(R.id.certification_userName)
    TextView certificationUserName;
    @BindView(R.id.certification_idCardNo)
    TextView certificationIdCardNo;
    @BindView(R.id.certification_status)
    TextView certificationStatus;
    @BindView(R.id.certification_submit)
    TextView certificationSubmit;
    @BindView(R.id.certification_address)
    TextView certificationAddress;
    /**
     * submit状态控制
     */
    private boolean isCanNext_s = false;
    private boolean isCanEditor_s = false;
    private boolean isCanUpload_s = false;
    private PeopleCertificationBean pcb;
    private boolean isIdOcrSuccess; //身份证识别是否通过
    private boolean isLivingRecSuccess;  //活体检测是否通过
    private boolean isRealNameVerifyRecSuccess; //实人认证是否通过
    private boolean isFaceRecSuccess;    //人脸识别是否通过
    private final static int MSG1 = 1;
    private final static int MSG2 = 2;
    private final static int MSG3 = 3;
    private final static int MSG4 = 4;
    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG1:
                    takePhotoIdFront.setVisibility(View.INVISIBLE);
                    GlideUtils.initImageByBitMap(CertificationActivity.this, (Bitmap) msg.obj, certificationIdCardFront);
                    break;
                case MSG2:
                    takePhotoIdBack.setVisibility(View.INVISIBLE);
                    GlideUtils.initImageByBitMap(CertificationActivity.this, (Bitmap) msg.obj, certificationIdCardBack);
                    break;
                case MSG3:  // 开始下载图片
                    Bundle data = msg.getData();
                    if (data != null) {
                        String url = data.getString("url");
                        String fileName = data.getString("fileName");
                        int type = data.getInt("type");
                        mPresenter.downLoadImg(url, fileName, type);
                    }
                    break;
                case MSG4:  // 获取下载的图片地址
                    Bundle dataPath = msg.getData();
                    if (dataPath != null) {
                        String filePath = dataPath.getString("filePath");
                        int type = dataPath.getInt("type");
                        if (type == 1) {
                            if (filePath != null) {
                                pcb.setIdcard_front_photo(filePath);
                                isIdOcrSuccess = true;
                            } else {
                                isIdOcrSuccess = false;
                            }

                        } else if (type == 2) {
                            if (filePath != null) {
                                pcb.setIdcard_back_photo(filePath);
                                isIdOcrSuccess = true;
                            } else {
                                isIdOcrSuccess = false;
                            }

                        } else if (type == 3) {
                            if (filePath != null) {
                                pcb.setIdcard_portrait_photo(filePath);
                                isIdOcrSuccess = true;
                            } else {
                                isIdOcrSuccess = false;
                            }

                        } else if (type == 4) {
                            if (filePath != null) {
                                pcb.setLiving_photo(filePath);
                                isLivingRecSuccess = true;
                            } else {
                                isLivingRecSuccess = false;
                            }
                        } else {
                            isIdOcrSuccess = false;
                            isLivingRecSuccess = false;
                        }
                    }
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        initActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        setStatusBar(certificationMStatusBar, R.color.blue1);
        setTittleBarBackground(certificationTitleBar, R.color.blue1);
        setPageTitle(certificationTitleBar, "身份认证", R.color.white);
        setPageLeftText(certificationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        pcb = new PeopleCertificationBean();
        certificationIdCardFront.setEnabled(false);
        certificationIdCardBack.setEnabled(false);
        takePhotoIdFront.setEnabled(false);
        takePhotoIdBack.setEnabled(false);
        certificationUserName.setEnabled(false);
        certificationIdCardNo.setEnabled(false);
        mPresenter.getPeopleStatus(BaseParams.userId);
    }

    @OnClick({R.id.take_photo_id_back, R.id.take_photo_id_front, R.id.certification_submit, R.id.certification_idCard_front, R.id.certification_idCard_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.certification_idCard_front:
            case R.id.take_photo_id_front:
            case R.id.certification_idCard_back:
            case R.id.take_photo_id_back:
                isIdOcrSuccess = false;
                isLivingRecSuccess = false;
                isFaceRecSuccess = false;
                isRealNameVerifyRecSuccess = false;
                getPermissions();
                break;
            case R.id.certification_submit:
                if (isCanUpload_s) {
                    if (!isIdOcrSuccess) {
                        ToastUtils.showLong(R.string.toast_15);
                        return;
                    }
                    if (!isLivingRecSuccess) {
                        ToastUtils.showLong(R.string.toast_16);
                        return;
                    }
                    if (!isRealNameVerifyRecSuccess) {
                        ToastUtils.showLong(R.string.toast_30);
                        return;
                    }
                    if (!isFaceRecSuccess) {
                        ToastUtils.showLong(R.string.toast_31);
                        return;
                    }
                    Map map = new HashMap();
                    map.put("address", pcb.getAddress());
                    map.put("age", pcb.getAge());
                    map.put("birthday", pcb.getBirthday());
                    map.put("id_name", pcb.getId_name());
                    map.put("id_number", pcb.getId_number());
                    map.put("gender", pcb.getGender());
                    map.put("nation", pcb.getNation());
                    map.put("idcard_back_photo", pcb.getIdcard_back_photo());
                    map.put("idcard_front_photo", pcb.getIdcard_front_photo());
                    map.put("idcard_portrait_photo", pcb.getIdcard_portrait_photo());
                    map.put("issuing_authority", pcb.getIssuing_authority());
                    map.put("validity_period", pcb.getValidity_period());
                    map.put("validity_period_expired", pcb.getValidity_period_expired());
                    map.put("classify", pcb.getClassify());
                    map.put("score", pcb.getScore());
                    map.put("living_photo", pcb.getLiving_photo());
                    map.put("userId", BaseParams.userId);
                    mPresenter.uploadPeopleInfo(map);
                } else {
                    if (isCanNext_s) {
                        ARouter.getInstance().build(ARouterUrl.BankCardCertificationActivityUrl).navigation(this, new LoginNavigationCallback());
                    }
                }
                break;
        }
    }

    @Override
    public void upLoadSuccess() {

    }

    @Override
    public void upLoadFailure() {

    }

    @Override
    public void getStatusSuccess(PeopleCertificationStatusBean certificationStatus_s) {
        if (certificationStatus_s.getData() != null) {
            String state = certificationStatus_s.getData().getState();
            certificationStatus.setText(state.equals("9001") ? getResources().getString(R.string.card_27)
                    : state.equals("9002") ? getResources().getString(R.string.card_28)
                    : state.equals("9003") ? getResources().getString(R.string.card_29)
                    : state.equals("9004") ? getResources().getString(R.string.card_30)
                    : getResources().getString(R.string.card_28));
            if (certificationStatus_s.getData().getFront() != null) {
                takePhotoIdFront.setVisibility(View.INVISIBLE);
                GlideUtils.initImageNoCache(this, certificationStatus_s.getData().getFront(), certificationIdCardFront);
            }
            if (certificationStatus_s.getData().getBack() != null) {
                takePhotoIdBack.setVisibility(View.INVISIBLE);
                GlideUtils.initImageNoCache(this, certificationStatus_s.getData().getFront(), certificationIdCardBack);
            }
            certificationUserName.setText(certificationStatus_s.getData().getReal_name());
            certificationIdCardNo.setText(certificationStatus_s.getData().getId_no());
            certificationAddress.setText(certificationStatus_s.getData().getAddress());
        }
    }

    @Override
    public void getStatusFailure() {

    }

    @Override
    public TextView submitText() {
        return submitText();
    }

    @Override
    public void downLoadImgSuccess(String filePath, File file, int type) {
        getImagePath(filePath, file, type, MSG4);
    }

    @Override
    public void downLoadImgFailure(int type) {
        if (type == 1 || type == 2 || type == 3) {
            isIdOcrSuccess = false;
        } else {
            isFaceRecSuccess = false;
        }
    }

    @Override
    public void isCanNext(boolean isCanNext) {
        this.isCanNext_s = isCanNext;
    }

    @Override
    public void isCanUpLoad(boolean isCanUpLoad) {
        this.isCanUpload_s = isCanUpLoad;
    }

    @Override
    public void isCanEditor(boolean isCanEditor) {
        certificationIdCardFront.setEnabled(true);
        certificationIdCardBack.setEnabled(true);
        takePhotoIdFront.setEnabled(true);
        takePhotoIdBack.setEnabled(true);
    }

    /**
     * 权限
     *
     * @param
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA)) {
                YouDunHelper.getInstance().AllOcr(CertificationActivity.this, YouDunOrderIdUtils.getYDOrderId());
            } else {
                PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                        .rationale(new PermissionUtils.OnRationaleListener() {
                            @Override
                            public void rationale(ShouldRequest shouldRequest) {
                                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .callback(new PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {
                                YouDunHelper.getInstance().AllOcr(CertificationActivity.this, YouDunOrderIdUtils.getYDOrderId());
//
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong(R.string.toast_14);
                            }
                        }).request();
            }
        } else {
            YouDunHelper.getInstance().AllOcr(CertificationActivity.this, YouDunOrderIdUtils.getYDOrderId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }

    //********************************友盾回调监听*********************************
    @Override
    public void optionBankOcrSuccess(JSONObject jsonObject) {

    }

    @Override
    public void optionIdOcrSuccess(JSONObject jsonObject) {  //  1
//        {
//            "address":"浙江省杭州市滨江区越达巷",
//                "age":"27",
//                "birthday":"1990.04.12",
//                "gender":"男",
//                "id_name":"周伯通",
//                "id_number":"320421199011121234",
//                "idcard_back_photo":"https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....",
//                "idcard_front_photo":"https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....",
//                "idcard_portrait_photo":"https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....",
//                "issuing_authority":"滨江公安局",
//                "nation":"汉",
//                "partner_order_id":"andr_1496712433370",
//                "validity_period":"2017.02.03-2037.02.03",
//                "validity_period_expired":"0",
//                "sdk_idcard_front_photo":"Bitmap",
//                "sdk_idcard_portrait_photo":"Bitmap",
//                "sdk_idcard_back_photo":"Bitmap",
//                "classify":"2",
//                "score":"0.9104828"
//            "message":"操作成功",
//                "session_id":"193852619297587200",
//                "success":"true"
//        }
        if (jsonObject != null) {
            try {
                //显示
                Bitmap sdk_idcard_front_photo = (Bitmap) jsonObject.opt("sdk_idcard_front_photo");
                getUrlBitmap(sdk_idcard_front_photo, MSG1);
                Bitmap sdk_idcard_back_photo = (Bitmap) jsonObject.opt("sdk_idcard_back_photo");
                getUrlBitmap(sdk_idcard_back_photo, MSG2);
                if (jsonObject.has("success") &&
                        jsonObject.getString("success").equals("true")) {
                    String classify = jsonObject.getString("classify");
                    if (StringUtils.equals(classify, "2")) {
//                        Bitmap sdk_idcard_front_photo = (Bitmap) jsonObject.opt("sdk_idcard_front_photo");
//                        getUrlBitmap(sdk_idcard_front_photo, MSG1);
//                        Bitmap sdk_idcard_back_photo = (Bitmap) jsonObject.opt("sdk_idcard_back_photo");
//                        getUrlBitmap(sdk_idcard_back_photo, MSG2);
                        // Bitmap sdk_idcard_portrait_photo = (Bitmap) jsonObject.opt("sdk_idcard_portrait_photo");
                        //getUrlBitmap(sdk_idcard_portrait_photo,MSG3);
                        //下载
                        getDownLoadUrl(jsonObject.getString("idcard_front_photo"), "idCardFront.jpeg", 1, MSG3);
                        getDownLoadUrl(jsonObject.getString("idcard_back_photo"), "idCardBack.jpeg", 2, MSG3);
                        getDownLoadUrl(jsonObject.getString("idcard_portrait_photo"), "idCardPortrait.jpeg", 3, MSG3);
                        //身份证号码
                        pcb.setId_number(jsonObject.getString("id_number"));
                        //身份证姓名
                        pcb.setId_name(jsonObject.getString("id_name"));
                        //添加:年龄、性别、民族、签发机关、身份证有效期
                        pcb.setAge(jsonObject.getString("age"));
                        pcb.setGender(jsonObject.getString("gender"));
                        pcb.setNation(jsonObject.getString("nation"));
                        pcb.setAddress(jsonObject.getString("address"));
                        pcb.setIssuing_authority(jsonObject.getString("issuing_authority"));
                        pcb.setValidity_period(jsonObject.getString("validity_period"));
                        pcb.setValidity_period_expired(jsonObject.getString("validity_period_expired"));
                        pcb.setClassify(classify);
                        pcb.setScore(jsonObject.getString("score"));
                        certificationUserName.setText(jsonObject.getString("id_name"));
                        certificationIdCardNo.setText(jsonObject.getString("id_number"));
                        certificationAddress.setText(jsonObject.getString("address"));
                    } else {
                        isIdOcrSuccess = false;
                    }
                } else {
                    isIdOcrSuccess = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                isIdOcrSuccess = false;
            }
        }
    }

    @Override
    public void optionLivelinessSuccess(JSONObject jsonObject) {  //2
//        {
//            "partner_order_id":"andr_1496711670963",
//                "risk_tag":{
//            "living_attack":"1"
//        },
//            "living_photo":"https://idsafe-auth.udcredit.com/front/4.0/api/file_download/....",
//                "session_id":"193849591345643520",
//                "success":"true",
//                "message":"操作成功"
//        }
        try {
            if (jsonObject.has("success") &&
                    jsonObject.getString("success").equals("true")) {
                //风险标签
                JSONObject risk = jsonObject.optJSONObject("risk_tag");
                // 0-未检测到活体攻击;  1-存在活体攻击风险

                String living_attack = risk.getString("living_attack");
                //请求是否成功
                boolean isLivingSuccess = jsonObject.getBoolean("success");
                if ("0".equals(living_attack) && isLivingSuccess) {
                    //活体检测图片
                    getDownLoadUrl(jsonObject.getString("living_photo"), "livingFace.jpeg", 4, MSG3);
                } else {
                    isLivingRecSuccess = false;
                }
            } else {
                isLivingRecSuccess = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isLivingRecSuccess = false;
        }

    }

    @Override
    public void optionCompareFace(JSONObject jsonObject) {  //4
//        {
//            "partner_order_id":"andr_1496711670963",
//                "thresholds":{
//            "1e-5":"0.9",
//                    "1e-4":"0.8",
//                    "1e-3":"0.7"
//        },
//            "similarity":"0.9668",
//                "session_id":"193849591345643520",
//                "success":"1",
//                "suggest_result":"F",
//                "message":"操作成功"
//        }
        try {
            if (jsonObject.has("success") &&
                    (jsonObject.getString("success").equals("1") || jsonObject.getString("success").equals("true"))) {
                String suggest_result = jsonObject.getString("suggest_result");
                if (StringUtils.equals(suggest_result, "T")) {  //认证是否通过
                    String similarity = jsonObject.getString("similarity");
                    double simil = Double.valueOf(similarity);
                    //相似度
                    if (simil > 0.7) {
                        isFaceRecSuccess = true;
                    } else {
                        isFaceRecSuccess = false;
                    }
                } else {
                    isFaceRecSuccess = false;
                }
            } else {
                isFaceRecSuccess = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isFaceRecSuccess = false;
        }

    }

    @Override
    public void optionVerifyCompare(JSONObject jsonObject) {  //3
        //验证状态
        try {
            if (jsonObject.has("success") &&
                    jsonObject.getString("success").equals("true")) {
                String verifyStatus = jsonObject.optString("verify_status");
                // 结果状态
                String resultStatus = jsonObject.optString("result_status");
                switch (verifyStatus) {
                    //姓名与号码一致，取得网格照
                    case "1":
                        if (resultStatus.equals("01")) {
                            isRealNameVerifyRecSuccess = true;
                        } else {
                            isRealNameVerifyRecSuccess = false;
                        }
                        break;
                    //姓名与号码不一致
                    case "2":
                        isRealNameVerifyRecSuccess = false;
                        //查询无结果
                    case "3":
                        isRealNameVerifyRecSuccess = false;
                        break;
                }
            } else {
                isRealNameVerifyRecSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isRealNameVerifyRecSuccess = false;
        }

    }

    @Override
    public void optionError(JSONObject jsonObject) {
        isIdOcrSuccess = false;
        isLivingRecSuccess = false;
        isFaceRecSuccess = false;
        isRealNameVerifyRecSuccess = false;
    }

    @Override
    public void JSONExceptionError(JSONException e) {
        isIdOcrSuccess = false;
        isLivingRecSuccess = false;
        isFaceRecSuccess = false;
        isRealNameVerifyRecSuccess = false;
    }


    /**
     * 获取身份证的显示的图片
     *
     * @param tag
     */
    public void getUrlBitmap(Bitmap bitmap, int tag) {
        if (bitmap != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmaps = ImageUtils.compressByQuality(bitmap, 60);
                    if (bitmaps != null) {
                        Message message = Message.obtain();
                        message.arg1 = tag;
                        message.obj = bitmaps;
                        mhandler.sendMessage(message);
                    }
                }
            }).start();
        } else {
            isIdOcrSuccess = false;
        }


    }

    /**
     * 下载指令
     *
     * @param url
     * @param fileName
     * @param type     1 身份证前
     *                 2身份证背
     *                 3 身份证头
     *                 4 活体
     * @param tag
     */
    public void getDownLoadUrl(String url, String fileName, int type, int tag) {
        if (url != null) {
            Message message = Message.obtain();
            message.arg1 = tag;
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("fileName", fileName);
            bundle.putInt("type", type);
            message.setData(bundle);
            mhandler.sendMessage(message);
        } else {
            switch (type) {
                case 1:
                case 2:
                case 3:
                    isIdOcrSuccess = false;
                    break;
                case 4:
                    isLivingRecSuccess = false;
                    break;
            }
        }
    }

    /**
     * 下载完成获取地址
     *
     * @param filePath
     * @param type
     * @param tag
     */
    public void getImagePath(String filePath, File file, int type, int tag) {
        Message message = Message.obtain();
        message.arg1 = tag;
        message.obj = file;
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putInt("type", type);
        message.setData(bundle);
        mhandler.sendMessage(message);
    }

}
