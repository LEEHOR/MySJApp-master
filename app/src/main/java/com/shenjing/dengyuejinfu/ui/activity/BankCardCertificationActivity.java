package com.shenjing.dengyuejinfu.ui.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.Constant;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.entity.BankInfoBean;
import com.shenjing.dengyuejinfu.ui.contract.BankCardCertificationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.BankCardCertificationActivityPresenter;
import com.shenjing.dengyuejinfu.utils.GlideUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;
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
        implements BankCardCertificationActivityContract.View {
    @BindView(R.id.card_certification_mStatusBar)
    View cardCertificationMStatusBar;
    @BindView(R.id.card_certification_titleBar)
    TitleBar cardCertificationTitleBar;
    @BindView(R.id.bank_image)
    ImageView bankImage;
    @BindView(R.id.bank_take_photo)
    ImageView bankTakePhoto;
    @BindView(R.id.bank_idNo)
    EditText bankIdNo;
    @BindView(R.id.bank_phone)
    EditText bankPhone;
    @BindView(R.id.bank_name)
    EditText bankName;
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
    private File fileBank;
    private File fileBankZip;
    private static final int TAKE_PHOTO_REQUEST_CODE_BANK = 1;
    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG1:
                    GlideUtils.initImageByBitMap(BankCardCertificationActivity.this, (Bitmap) msg.obj, bankImage);
                    getSaveImagePath((Bitmap) msg.obj, MSG2);
                    break;
                case MSG2:
                    isBankOrcSuccess = true;
                    break;
                case MSG3:

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
        mPresenter.getBankInfo(BaseParams.userId);
        bankImage.setEnabled(false);
        bankTakePhoto.setEnabled(false);
        bankName.setEnabled(false);
        bankIdNo.setEnabled(false);
        bankPhone.setEnabled(false);
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
        bankName.setEnabled(isCanEditor);
    }

    @Override
    public TextView  submit() {
        return  bankSubmit;
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
                        if (!FileUtils.isFileExists(fileBankZip)){
                            ToastUtils.showLong("图片未就绪，请稍后再试");
                            return;
                        }

                        if (StringUtils.isSpace(bankIdNo.getText().toString())) {
                            ToastUtils.showLong("填写银行卡号");
                            return;
                        }
                        if (bankIdNo.getText().toString().trim().length()!=18) {
                            ToastUtils.showLong("填写正确的银行卡号");
                            return;
                        }
                        if (StringUtils.isSpace(bankName.getText().toString())) {
                            ToastUtils.showLong("填写银行名称");
                            return;
                        }
                        if (StringUtils.isSpace(bankPhone.getText().toString())) {
                            ToastUtils.showLong("填写手机号");
                            return;
                        }
                        if (!RegexUtils.isMobileSimple(bankPhone.getText().toString())) {
                            ToastUtils.showLong("填写正确的手机号");
                            return;
                        }
                        Map map = new HashMap();
                        map.put("userId", BaseParams.userId);
                        map.put("phoneNumber",bankPhone.getText().toString().trim());
                        map.put("bank",bankName.getText().toString().trim());
                        map.put("bankCardNo",bankIdNo.getText().toString().trim());
                        map.put("bankCardImg",fileBankZip.getAbsolutePath());
                        mPresenter.upLoadBankCardInfo(map);
                    } else {
                        ToastUtils.showLong(R.string.toast_10);
                    }
                } else {

//                    if (isCanNext_s) {
//                        ARouter.getInstance().build(ARouterUrl.PaymentVerificationActivityUrl)
//                                .navigation(this, new LoginNavigationCallback());
//                    }
                }
                break;
        }
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA)) {
                takeBankPhoto();
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
                                takeBankPhoto();
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong(R.string.toast_14);
                            }
                        }).request();
            }
        } else {
            takeBankPhoto();
        }
    }


    /**
     * 获取银行卡压缩图
     *
     * @param tag
     */
    public void getZipBitmap(File fileBank,int tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bank_zip_image = ImageUtils.getBitmap(fileBank);
                Bitmap bitmaps = ImageUtils.compressByQuality(bank_zip_image, 60);
                if (bitmaps != null) {
                    Message message = Message.obtain();
                    message.what = tag;
                    message.obj = bitmaps;
                    mhandler.sendMessage(message);
                } else {
                    isBankOrcSuccess=false;
                }
            }
        }).start();
    }

    /**
     * 保存压缩过的图片
     *
     * @param zipBitmap
     * @param tag
     */
    public void getSaveImagePath(Bitmap zipBitmap, int tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!FileUtils.createOrExistsDir(Constant.SAVE_DIR_YOUDUN)) {
                    return;
                }
                fileBankZip = new File(Constant.SAVE_DIR_YOUDUN, "image_bank_zip" + TimeUtils.millis2String(System.currentTimeMillis()) + ".jpeg");
                if (FileUtils.createFileByDeleteOldFile(fileBankZip)) {
                    if (FileUtils.createFileByDeleteOldFile(fileBankZip)) {
                        if (ImageUtils.save(zipBitmap, fileBankZip, Bitmap.CompressFormat.JPEG, false)) {
                            Message message = Message.obtain();
                            message.what = tag;
                            message.obj = fileBankZip;
                            mhandler.sendMessage(message);
                        }
                    }
                }
            }
        }).start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }

    /**
     * 拍摄图片
     */
    private void takeBankPhoto() {
        String picture_bank = Constant.SAVE_DIR_TAKE_PHOTO;
        if (!FileUtils.createOrExistsDir(picture_bank)) {
            ToastUtils.showLong(R.string.toast_12);
            return;
        }
        fileBank = new File(picture_bank, "image_bank_" + TimeUtils.millis2String(System.currentTimeMillis()) + ".jpeg");
        if (!FileUtils.createFileByDeleteOldFile(fileBank)) {
            ToastUtils.showLong(R.string.toast_13);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", fileBank);
            Intent captureIntent_pre = IntentUtils.getCaptureIntent(photoUri);
            startActivityForResult(captureIntent_pre, TAKE_PHOTO_REQUEST_CODE_BANK);
        } else {
            Uri imageUri = Uri.fromFile(fileBank);
            Intent captureIntent_pre = IntentUtils.getCaptureIntent(imageUri);
            startActivityForResult(captureIntent_pre, TAKE_PHOTO_REQUEST_CODE_BANK);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_REQUEST_CODE_BANK && resultCode == Activity.RESULT_OK) {
            if (FileUtils.isFile(fileBank)) {
                bankTakePhoto.setVisibility(View.INVISIBLE);
                getZipBitmap(fileBank, MSG1);
            } else {
                isBankOrcSuccess = false;
            }
        }
    }
}
