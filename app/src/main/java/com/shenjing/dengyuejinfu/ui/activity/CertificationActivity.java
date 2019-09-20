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
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.App;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.Constant;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.respondModule.PeopleCertificationStatus;
import com.shenjing.dengyuejinfu.ui.contract.CertificationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.CertificationActivityPresenter;
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
 * date   : 2019/8/1418:42
 * version: 1.0
 * desc   :身份认证
 */
@Route(path = ARouterUrl.CertificationActivityUrl)
public class CertificationActivity extends BaseActivity<CertificationActivityPresenter>
        implements CertificationActivityContract.View {
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
    EditText certificationUserName;
    @BindView(R.id.certification_idCardNo)
    EditText certificationIdCardNo;
    @BindView(R.id.certification_status)
    TextView certificationStatus;
    @BindView(R.id.certification_submit)
    TextView certificationSubmit;
    private final int TAKE_PHOTO_REQUEST_CODE_BACK = 1001;
    private final int TAKE_PHOTO_REQUEST_CODE_FRONT = 1002;
    private boolean isBackPictureSuccess = false;
    private boolean isFrontPictureSuccess = false;
    /**
     * submit状态控制
     */
    private boolean isCanNext_s = false;
    private boolean isCanEditor_s = false;
    private boolean isCanUpload_s = false;
    private File fileFront;
    private File fileBack;
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
                    GlideUtils.initImageByBitMap(CertificationActivity.this, (Bitmap) msg.obj, certificationIdCardFront);
                    break;
                case MSG2:
                    GlideUtils.initImageByBitMap(CertificationActivity.this, (Bitmap) msg.obj, certificationIdCardBack);
                    break;
                case MSG3:
                    isFrontPictureSuccess = (int) msg.obj == 1;
                    LogUtils.d("消息发送3--"+isFrontPictureSuccess);
                    break;
                case MSG4:
                    isBackPictureSuccess = (int) msg.obj == 1;
                    LogUtils.d("消息发送4--"+isBackPictureSuccess);
                    break;
            }
        }
    };
    private File fileFrontZip;
    private File fileBackZip;

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
                isFrontPictureSuccess = false;
                getPermissions(1);

                break;
            case R.id.certification_idCard_back:
            case R.id.take_photo_id_back:
                isBackPictureSuccess = false;
                getPermissions(2);
                break;
            case R.id.certification_submit:
                if (isCanUpload_s) {
                    if (!isFrontPictureSuccess) {
                        ToastUtils.showLong("请拍摄身份证头像面");
                        return;
                    }
                    if (!isBackPictureSuccess) {
                        ToastUtils.showLong("请拍摄身份证国徽面");
                        return;
                    }

                    if (StringUtils.isSpace(certificationUserName.getText().toString().trim())) {
                        ToastUtils.showLong("请填写姓名");
                        return;
                    }
                    if (StringUtils.isSpace(certificationIdCardNo.getText().toString().trim())) {
                        ToastUtils.showLong("请填写银行卡号");
                        return;
                    }
                    if (!FileUtils.isFile(fileFrontZip)) {
                        ToastUtils.showLong("正在压缩图片，请稍后继续");
                        return;
                    }
                    if (!FileUtils.isFile(fileBackZip)) {
                        ToastUtils.showLong("正在压缩图片，请稍后继续");
                        return;
                    }
                    Map map = new HashMap();
                    map.put("fornt", fileFrontZip.getAbsolutePath());
                    map.put("back", fileBackZip.getAbsolutePath());
                    map.put("idNo", certificationIdCardNo.getText().toString().trim());
                    map.put("realName", certificationUserName.getText().toString().trim());
                    map.put("userId", Long.parseLong(BaseParams.userId));
                    mPresenter.uploadPeopleInfo(map);
                } else {
                    if (isCanNext_s) {
                        ARouter.getInstance().build(ARouterUrl.BankCardCertificationActivityUrl).navigation(this, new LoginNavigationCallback());
                    }
                }
                break;
        }
    }

    /**
     * 拍摄背面
     */
    private void takeBackPhoto() {
        String picture_back = Constant.SAVE_DIR_TAKE_PHOTO;
        if (!FileUtils.createOrExistsDir(picture_back)) {
            ToastUtils.showLong("创建文件夹失败");
            return;
        }
        fileBack = new File(picture_back, "idCard_back_" + TimeUtils.millis2String(System.currentTimeMillis()) + ".jpeg");
        if (!FileUtils.createFileByDeleteOldFile(fileBack)) {
            ToastUtils.showLong("创建文件失败");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", fileBack);
            Intent captureIntent_pre = IntentUtils.getCaptureIntent(photoUri);
            startActivityForResult(captureIntent_pre, TAKE_PHOTO_REQUEST_CODE_BACK);
        } else {
            Uri imageUri = Uri.fromFile(fileBack);
            Intent captureIntent_pre = IntentUtils.getCaptureIntent(imageUri);
            startActivityForResult(captureIntent_pre, TAKE_PHOTO_REQUEST_CODE_BACK);
        }
    }

    /**
     * 拍摄正面图片
     */
    private void takeFrontPhoto() {
        String picture_front = Constant.SAVE_DIR_TAKE_PHOTO;
        if (!FileUtils.createOrExistsDir(picture_front)) {
            ToastUtils.showLong("创建文件夹失败");
            return;
        }
        fileFront = new File(picture_front, "idCard_front_" + TimeUtils.millis2String(System.currentTimeMillis()) + ".jpeg");
        if (!FileUtils.createFileByDeleteOldFile(fileFront)) {
            ToastUtils.showLong("创建文件失败");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoUri = FileProvider.getUriForFile(App.getAppContext(), getPackageName() + ".fileProvider", fileFront);
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
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE_FRONT:
                if (FileUtils.isFile(fileFront)) {
                    takePhotoIdFront.setVisibility(View.INVISIBLE);
                    //mhandler.sendEmptyMessage(MSG1);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap front_image = ImageUtils.getBitmap(fileFront);
                            Bitmap bitmap_front_zip = ImageUtils.compressByQuality(front_image, 40, true);
                            Message message = new Message();
                            message.what = MSG1;
                            message.obj = bitmap_front_zip;
                            //消息发送
                            mhandler.sendMessage(message);
                            if (!FileUtils.createOrExistsDir(Constant.SAVE_DIR_GLIDE_CACHE)) {
                                LogUtils.d("创建文件夹失败");
                                return;
                            }
                            fileFrontZip = new File(Constant.SAVE_DIR_GLIDE_CACHE, "image_front.jpeg");
                            Message message2 = new Message();
                            if (FileUtils.createFileByDeleteOldFile(fileFrontZip)) {
                                if (ImageUtils.save(bitmap_front_zip, fileFrontZip, Bitmap.CompressFormat.JPEG, false)) {
                                    FileUtils.delete(fileFront);
                                    message2.what = MSG3;
                                    message2.obj = 1;
                                    //消息发送
                                    LogUtils.d("消息发送3——1");
                                    mhandler.sendMessage(message2);

                                } else {
                                    message2.what = MSG3;
                                    message2.obj = 2;
                                    //消息发送
                                    LogUtils.d("消息发送3——2");
                                    mhandler.sendMessage(message2);
                                }
                            } else {
                                message2.what = MSG3;
                                message2.obj = 2;
                                //消息发送
                                LogUtils.d("消息发送3——3");
                                mhandler.sendMessage(message2);
                            }
                        }
                    }).start();
                    // GlideUtils.initImageByFile(this, fileFront, certificationIdCardFront);
                } else {
                    LogUtils.d("消息发送3——4");
                    isFrontPictureSuccess = false;
                }
                break;
            case TAKE_PHOTO_REQUEST_CODE_BACK:
                //  File file_back = new File(picture_back);
                if (FileUtils.isFile(fileBack)) {
                    takePhotoIdBack.setVisibility(View.INVISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap back_image = ImageUtils.getBitmap(fileBack);
                            Bitmap bitmap_back_zip = ImageUtils.compressByQuality(back_image, 40, true);
                            Message message = new Message();
                            message.what = MSG2;
                            message.obj = bitmap_back_zip;
                            //消息发送
                            mhandler.sendMessage(message);
                            if (!FileUtils.createOrExistsDir(Constant.SAVE_DIR_GLIDE_CACHE)) {
                                LogUtils.d("创建文件夹失败");
                                return;
                            }
                            fileBackZip = new File(Constant.SAVE_DIR_GLIDE_CACHE, "image_back.jpeg");
                            Message message2 = new Message();
                            if (FileUtils.createFileByDeleteOldFile(fileBackZip)) {
                                if (ImageUtils.save(bitmap_back_zip, fileBackZip, Bitmap.CompressFormat.JPEG, false)) {
                                    FileUtils.delete(fileBack);
                                    message2.what = MSG4;
                                    message2.obj = 1;
                                    //消息发送
                                    LogUtils.d("消息发送4——1");
                                    mhandler.sendMessage(message2);
                                } else {
                                    message2.what = MSG4;
                                    message2.obj = 2;
                                    //消息发送
                                    LogUtils.d("消息发送4——2");
                                    mhandler.sendMessage(message2);
                                }
                            } else {
                                message2.what = MSG4;
                                message2.obj = 2;
                                //消息发送
                                LogUtils.d("消息发送4——3");
                                mhandler.sendMessage(message2);
                            }
                        }
                    }).start();
                    // mhandler.sendEmptyMessage(MSG2);
                    //  GlideUtils.initImageByFile(this, fileBack, certificationIdCardBack);
                } else {
                    LogUtils.d("消息发送4——4");
                    isBackPictureSuccess = false;
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    @Override
    public void upLoadSuccess() {

    }

    @Override
    public void upLoadFailure() {

    }

    @Override
    public void getStatusSuccess(PeopleCertificationStatus certificationStatus_s) {
        if (certificationStatus_s.getData() != null) {
            String state = certificationStatus_s.getData().getState();
            certificationStatus.setText(state.equals("9001")?"审核完成":state.equals("9002")?"未认证"
                    :state.equals("9003")?"审核中":state.equals("9004")?"审核失败":"未认证");
            if (certificationStatus_s.getData().getFront() != null) {
                takePhotoIdFront.setVisibility(View.INVISIBLE);
                GlideUtils.initImageNoCache(this,certificationStatus_s.getData().getFront(),certificationIdCardFront);
            }
            if (certificationStatus_s.getData().getBack() !=null){
                takePhotoIdBack.setVisibility(View.INVISIBLE);
                GlideUtils.initImageNoCache(this,certificationStatus_s.getData().getFront(),certificationIdCardBack);
            }
            certificationUserName.setText(certificationStatus_s.getData().getReal_name());
            certificationIdCardNo.setText(certificationStatus_s.getData().getId_no());
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
    public void isCanNext(boolean isCanNext) {
        this.isCanNext_s = isCanNext;
    }

    @Override
    public void isCanUpLoad(boolean isCanUpLoad) {
        this.isCanUpload_s = isCanUpLoad;
    }

    @Override
    public void isCanEditor(boolean isCanEditor) {
        certificationIdCardFront.setEnabled(isCanEditor);
        certificationIdCardBack.setEnabled(isCanEditor);
        takePhotoIdFront.setEnabled(isCanEditor);
        takePhotoIdBack.setEnabled(isCanEditor);
        certificationUserName.setEnabled(isCanEditor);
        certificationIdCardNo.setEnabled(isCanEditor);
    }

    /**
     * 权限
     *
     * @param i
     */
    private void getPermissions(int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA)) {
                if (i == 1) {
                    takeFrontPhoto();
                } else {
                    takeBackPhoto();
                }
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
                                if (i == 1) {
                                    takeFrontPhoto();
                                } else {
                                    takeBackPhoto();
                                }
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong("获取权限失败");
                            }
                        }).request();
            }
        } else {
            if (i == 1) {
                takeFrontPhoto();
            } else {
                takeBackPhoto();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }
}
