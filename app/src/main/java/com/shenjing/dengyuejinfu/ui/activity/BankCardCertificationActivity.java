package com.shenjing.dengyuejinfu.ui.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.leehor.simple.youdun.YouDunHelper;
import com.leehor.simple.youdun.YouDunListener;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.Constant;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.entity.BankInfoBean;
import com.shenjing.dengyuejinfu.entity.BankOcrInfoBean;
import com.shenjing.dengyuejinfu.ui.contract.BankCardCertificationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.BankCardCertificationActivityPresenter;
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
 * date   : 2019/8/1418:47
 * version: 1.0
 * desc   :银行卡认证
 */
@Route(path = ARouterUrl.BankCardCertificationActivityUrl)
public class BankCardCertificationActivity extends BaseActivity<BankCardCertificationActivityPresenter>
        implements BankCardCertificationActivityContract.View, YouDunListener {
    @BindView(R.id.card_certification_mStatusBar)
    View cardCertificationMStatusBar;
    @BindView(R.id.card_certification_titleBar)
    TitleBar cardCertificationTitleBar;
    @BindView(R.id.bank_image)
    ImageView bankImage;
    @BindView(R.id.bank_take_photo)
    ImageView bankTakePhoto;
    @BindView(R.id.bank_idNo)
    TextView bankIdNo;
    @BindView(R.id.bank_phone)
    EditText bankPhone;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.bank_submit)
    TextView bankSubmit;
    /**
     * submit状态控制
     */
    private boolean isCanNext_s = false;
    private boolean isCanUpload_s = false;
    private final static int MSG1 = 1;
    private final static int MSG2 = 2;
    private final static int MSG3 = 3;
    private boolean isBankOrcSuccess;
    private BankOcrInfoBean bankOcrInfoBean;
    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG1:
                    GlideUtils.initImageByBitMap(BankCardCertificationActivity.this, (Bitmap) msg.obj, bankImage);
                    break;
                case MSG2:  //下载
                    Bundle data = msg.getData();
                    String url = data.getString("url");
                    String fileName = data.getString("fileName");
                    mPresenter.DownLoadImg(url, fileName);
                    break;
                case MSG3:  //获取下载的图片地址
                    Bundle dataPath = msg.getData();
                    if (dataPath != null) {
                        String filePath = dataPath.getString("filePath");
                        bankOcrInfoBean.setBank_card_photo(filePath);
                        isBankOrcSuccess = true;
                    } else {
                        isBankOrcSuccess = false;
                    }
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_card_certification;
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
        setStatusBar(cardCertificationMStatusBar, R.color.blue1);
        setTittleBarBackground(cardCertificationTitleBar, R.color.blue1);
        setPageTitle(cardCertificationTitleBar, "银行卡认证", R.color.white);
        setPageLeftText(cardCertificationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        bankOcrInfoBean = new BankOcrInfoBean();
        mPresenter.getBankInfo(BaseParams.userId);
        bankImage.setEnabled(false);
        bankTakePhoto.setEnabled(false);
    }

    @Override
    public void getBankInfoSuccess(BankInfoBean model) {
        if (model.getData() != null) {
            if (model.getData().getBank_card_img() != null) {
                bankTakePhoto.setVisibility(View.INVISIBLE);
                GlideUtils.initImageWithFileCache(this, model.getData().getBank_card_img(), bankImage);
            }
            bankPhone.setText(model.getData().getPhone_number());
            bankName.setText(model.getData().getBank());
            bankIdNo.setText(model.getData().getBank_card_no());
        }
    }

    @Override
    public void getBankInfoFailure() {

    }

    @Override
    public void upLoadSuccess() {

    }

    @Override
    public void upLoadFailure() {

    }

    @Override
    public void downLoadImgSuccess(String filePath, File file) {
        getImagePath(filePath, file, MSG3);
    }

    @Override
    public void downLoadImgFailure() {
        isBankOrcSuccess = false;
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
        bankImage.setEnabled(isCanEditor);
        bankIdNo.setEnabled(isCanEditor);
        bankPhone.setEnabled(isCanEditor);
        bankTakePhoto.setEnabled(isCanEditor);
    }

    @OnClick({R.id.bank_image, R.id.bank_take_photo, R.id.bank_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank_image:
            case R.id.bank_take_photo:
                isBankOrcSuccess = false;
                getPermissions();
                break;
            case R.id.bank_submit:
                if (isCanUpload_s) {
                    if (isBankOrcSuccess) {
                        if (StringUtils.isSpace(bankPhone.getText().toString())) {
                            ToastUtils.showLong("填写手机号");
                            return;
                        }
                        if (!RegexUtils.isMobileSimple(bankPhone.getText().toString())) {
                            ToastUtils.showLong("填写正确的手机号");
                            return;
                        }
                        Map map = new HashMap();
                        map.put("bank_card_photo", bankOcrInfoBean.getBank_card_photo());
                        map.put("bank_name", bankOcrInfoBean.getBank_name());
                        map.put("bank_card_no", bankOcrInfoBean.getBank_card_no());
                        map.put("card_type", bankOcrInfoBean.getCard_type());
                        map.put("org_code", bankOcrInfoBean.getOrg_code());
                        map.put("phoneNumber", bankPhone.getText().toString().trim());
                        map.put("userId", BaseParams.userId);
                        mPresenter.upLoadBankCardInfo(map);
                    } else {
                        ToastUtils.showLong(R.string.toast_10);
                    }
                } else {
                    if (isCanNext_s) {
                        ARouter.getInstance().build(ARouterUrl.PaymentVerificationActivityUrl)
                                .navigation(this, new LoginNavigationCallback());
                    }
                }
                break;
        }
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA)) {
                YouDunHelper.getInstance().optionBankOCR(BankCardCertificationActivity.this, YouDunOrderIdUtils.getYDOrderId());
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
                                YouDunHelper.getInstance().optionBankOCR(BankCardCertificationActivity.this, YouDunOrderIdUtils.getYDOrderId());
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong(R.string.toast_14);
                            }
                        }).request();
            }
        } else {
            YouDunHelper.getInstance().optionBankOCR(BankCardCertificationActivity.this, YouDunOrderIdUtils.getYDOrderId());
        }
    }


    /**
     * 获取银行卡显示的图片
     *
     * @param tag
     */
    public void getUrlBitmap(Bitmap bitmap, final int tag) {
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
            isBankOrcSuccess = false;
        }


    }

    /**
     * 下载图片指令
     *
     * @param url
     * @param tag
     */
    public void getDownLoadUrl(String url, String fileName, int tag) {
        if (url != null) {
            Message message = Message.obtain();
            message.arg1 = tag;
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("fileName", fileName);
            message.setData(bundle);
            mhandler.sendMessage(message);
        } else {
            isBankOrcSuccess = false;
        }

    }

    /**
     * 下载完成获取地址
     *
     * @param filePath
     * @param tag
     */
    public void getImagePath(String filePath, File file, int tag) {
        Message message = Message.obtain();
        message.arg1 = tag;
        message.obj = file;
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        message.setData(bundle);
        mhandler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void optionBankOcrSuccess(JSONObject jsonObject) {

//        {
//            "partner_order_id": "andr_1545967493371",
//                "bank_card_photo": "http://10.1.30.51:8080/idsafe-front/front/1.0/......",
//                "bank_name": "杭州商业银行",
//                "bank_card_no": "1234567890122222",
//                "card_type": "借记卡",
//                "org_code": "1234568",
//                "sdk_bank_card_photo":"Bitmap",
//                "success": "true",
//                "message": "操作成功"
//        }
        try {
            //显示银行卡图片
            Bitmap sdk_bank_card_photo = (Bitmap) jsonObject.opt("sdk_bank_card_photo");
            getUrlBitmap(sdk_bank_card_photo, MSG1);
            if (jsonObject.has("success") &&
                    (jsonObject.getString("success").equals("1") || jsonObject.getString("success").equals("true"))) {
                //下载
                getDownLoadUrl(jsonObject.getString("bank_card_photo"), "image_bank.jpeg", MSG2);
                String bank_name = jsonObject.getString("bank_name");
                String bank_card_no = jsonObject.getString("bank_card_no");
                String card_type = jsonObject.getString("card_type");
                String org_code = jsonObject.getString("org_code");
                bankIdNo.setText(bank_card_no);
                bankName.setText(bank_name);
                bankOcrInfoBean.setBank_name(bank_name);
                bankOcrInfoBean.setBank_card_no(bank_card_no);
                bankOcrInfoBean.setCard_type(card_type);
                bankOcrInfoBean.setOrg_code(org_code);
            } else {
                isBankOrcSuccess = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isBankOrcSuccess = false;
        }
    }

    @Override
    public void optionIdOcrSuccess(JSONObject jsonObject) {

    }

    @Override
    public void optionLivelinessSuccess(JSONObject jsonObject) {

    }

    @Override
    public void optionCompareFace(JSONObject jsonObject) {

    }

    @Override
    public void optionVerifyCompare(JSONObject jsonObject) {

    }

    @Override
    public void optionError(JSONObject jsonObject) {
        isBankOrcSuccess = false;
    }

    @Override
    public void JSONExceptionError(JSONException e) {
        isBankOrcSuccess = false;
    }
}
