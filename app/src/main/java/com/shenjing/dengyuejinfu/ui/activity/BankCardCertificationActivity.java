package com.shenjing.dengyuejinfu.ui.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
    @BindView(R.id.bank_class)
    TextView bankClass;
    @BindView(R.id.bank_submit)
    TextView bankSubmit;
    private final int TAKE_PHOTO_REQUEST_CODE_FRONT = 1002;
    private boolean isBankPictureSuccess = false;

    /**
     * submit状态控制
     */
    private boolean isCanNext_s = false;
    private boolean isCanUpload_s = false;
    private File fileFront;
    private final static int MSG1 = 1;
    private final static int MSG2 = 2;
    private File fileFrontZip;
    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG1:
                    GlideUtils.initImageByBitMap(BankCardCertificationActivity.this, (Bitmap) msg.obj, bankImage);
                    break;
                case MSG2:
                    isBankPictureSuccess = (int) msg.obj == 1;
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
        bankIdNo.setEnabled(false);
        bankPhone.setEnabled(false);
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
            bankClass.setText(model.getData().getBank());
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
    }

    @OnClick({R.id.bank_image, R.id.bank_take_photo, R.id.bank_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank_image:
            case R.id.bank_take_photo:
                isBankPictureSuccess = false;
                getPermissions();
                break;
            case R.id.bank_submit:
                if (isCanUpload_s) {
                    if (isBankPictureSuccess) {
                        if (StringUtils.isSpace(bankIdNo.getText().toString().trim())) {
                            ToastUtils.showLong(R.string.toast_4);
                            return;
                        }
                        if (StringUtils.isSpace(bankPhone.getText().toString().trim())) {
                            ToastUtils.showLong(R.string.toast_5);
                            return;
                        }
                        if (!RegexUtils.isMobileSimple(bankPhone.getText().toString().trim())) {
                            ToastUtils.showLong(R.string.toast_6);
                            return;
                        }
                        if (!FileUtils.isFile(fileFrontZip)) {
                            ToastUtils.showLong(R.string.toast_9);
                            return;
                        }
                        Map map = new HashMap();
                        map.put("bankCardImg", fileFrontZip.getAbsolutePath());
                        map.put("phoneNumber", bankPhone.getText().toString().trim());
                        map.put("bank", "");
                        map.put("bankCardNo", bankIdNo.getText().toString().trim());
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

    /**
     * 拍照
     */
    private void takeFrontPhoto() {
        String picture_front = Constant.SAVE_DIR_TAKE_PHOTO;
        if (!FileUtils.createOrExistsDir(picture_front)) {
            ToastUtils.showLong(R.string.toast_12);
            return;
        }
        fileFront = new File(picture_front, "bank_" + TimeUtils.millis2String(System.currentTimeMillis()) + ".jpeg");
        if (!FileUtils.createFileByDeleteOldFile(fileFront)) {
            ToastUtils.showLong(R.string.toast_13);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", fileFront);
            Intent captureIntent_front = IntentUtils.getCaptureIntent(photoUri);
            startActivityForResult(captureIntent_front, TAKE_PHOTO_REQUEST_CODE_FRONT);
        } else {
            Uri imageUri = Uri.fromFile(fileFront);
            Intent captureIntent_front = IntentUtils.getCaptureIntent(imageUri);
            startActivityForResult(captureIntent_front, TAKE_PHOTO_REQUEST_CODE_FRONT);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_REQUEST_CODE_FRONT) {
            // File file_front = new File(picture_front);
            if (FileUtils.isFile(fileFront)) {
                bankTakePhoto.setVisibility(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap front_image = ImageUtils.getBitmap(fileFront);
                        Bitmap bitmap_front_zip = ImageUtils.compressByQuality(front_image, 40, true);
                        Message message = new Message();
                        message.what = MSG1;
                        message.obj = bitmap_front_zip;
                        //然后将消息发送出去
                        mhandler.sendMessage(message);
                        // mhandler.sendEmptyMessage(MSG1);
                        if (!FileUtils.createOrExistsDir(Constant.SAVE_DIR_GLIDE_CACHE)) {
                            LogUtils.d(R.string.toast_12);
                            return;
                        }
                        fileFrontZip = new File(Constant.SAVE_DIR_GLIDE_CACHE, "image_bank.jpeg");
                        Message message2 = new Message();
                        if (FileUtils.createFileByDeleteOldFile(fileFrontZip)) {
                            if (ImageUtils.save(bitmap_front_zip, fileFrontZip, Bitmap.CompressFormat.JPEG, false)) {
                                FileUtils.delete(fileFront);
                                message2.what = MSG2;
                                message2.obj = 1;
                                //然后将消息发送出去
                                mhandler.sendMessage(message2);
                            } else {
                                message2.what = MSG2;
                                message2.obj = 2;
                                //然后将消息发送出去
                                mhandler.sendMessage(message2);
                            }
                        } else {
                            message2.what = MSG2;
                            message2.obj = 1;
                            //然后将消息发送出去
                            mhandler.sendMessage(message2);
                        }
                    }
                }).start();
            } else {
                isBankPictureSuccess = false;
            }
        }
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA)) {
                takeFrontPhoto();
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
                                takeFrontPhoto();
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong(R.string.toast_14);
                            }
                        }).request();
            }
        } else {
            takeFrontPhoto();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }
}
