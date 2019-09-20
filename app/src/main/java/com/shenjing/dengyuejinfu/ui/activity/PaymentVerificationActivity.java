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
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.Constant;
import com.shenjing.dengyuejinfu.respondModule.PaymentModel;
import com.shenjing.dengyuejinfu.ui.contract.PaymentVerificationActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.PaymentVerificationActivityPresenter;
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
 * date   : 2019/8/1418:53
 * version: 1.0
 * desc   :安全支付认证
 */
@Route(path = ARouterUrl.PaymentVerificationActivityUrl)
public class PaymentVerificationActivity extends BaseActivity<PaymentVerificationActivityPresenter>
        implements PaymentVerificationActivityContract.View {
    @BindView(R.id.payment_verification_mStatusBar)
    View paymentVerificationMStatusBar;
    @BindView(R.id.payment_verification_titleBar)
    TitleBar paymentVerificationTitleBar;
    @BindView(R.id.take_face_pic)
    ImageView takeFacePic;
    @BindView(R.id.take_face_photo)
    ImageView takeFacePhoto;
    @BindView(R.id.take_submit)
    TextView takeSubmit;

    private final int TAKE_PHOTO_REQUEST_CODE_FACE = 1002;
    private boolean isFacePictureSuccess = false;

    /**
     * submit状态控制
     */
    private boolean isCanNext_s = false;
    private boolean isCanUpload_s = false;
    private File fileFace;
    private final static int MSG1 = 1;
    private final static int MSG2 = 2;
    private File fileFaceZip;
    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG1:
                    GlideUtils.initImageByBitMap(PaymentVerificationActivity.this, (Bitmap) msg.obj, takeFacePic);
                    break;
                case MSG2:
                    isFacePictureSuccess = (int) msg.obj == 1;
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_security_payment_verificatio;
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
        setStatusBar(paymentVerificationMStatusBar, R.color.blue1);
        setTittleBarBackground(paymentVerificationTitleBar, R.color.blue1);
        setPageTitle(paymentVerificationTitleBar, "安全支付认证", R.color.white);
        setPageLeftText(paymentVerificationTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        takeFacePhoto.setEnabled(false);
        takeFacePic.setEnabled(false);
        mPresenter.getTakeStatus(BaseParams.userId);

    }


    @Override
    public void getTakeStatusSuccess(PaymentModel paymentModel) {
        if (paymentModel.getData() != null) {
            if (paymentModel.getData().getTakeImg() != null) {
                takeFacePhoto.setEnabled(false);
                GlideUtils.initImageWithFileCache(this,paymentModel.getData().getTakeImg(),takeFacePic);
            }
        }
    }

    @Override
    public void getTakeStatusFailure() {

    }

    @Override
    public void upLoadSuccess() {

    }

    @Override
    public void upLoadFailure() {

    }


    @Override
    public void isCanNext(boolean isCanNext) {
        this.isCanNext_s=isCanNext;
    }

    @Override
    public void isCanUpLoad(boolean isCanUpLoad) {
        this.isCanUpload_s=isCanUpLoad;
    }

    @Override
    public void isCanEditor(boolean isCanEditor) {
        takeFacePhoto.setEnabled(isCanEditor);
        takeFacePic.setEnabled(isCanEditor);
    }

    @OnClick({R.id.take_face_pic, R.id.take_face_photo,R.id.take_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_face_pic:
            case R.id.take_face_photo:
                isFacePictureSuccess=false;
                getPermissions();
                break;
            case R.id.take_submit:
                if (isCanUpload_s){
                    if (isFacePictureSuccess){
                        if (!FileUtils.isFile(fileFaceZip)) {
                            ToastUtils.showLong("正在压缩图片，请稍后继续");
                            return;
                        }
                        Map map=new HashMap();
                        map.put("userId",BaseParams.userId);
                        map.put("takeImg",fileFaceZip.getAbsolutePath());
                        mPresenter.uploadTake(map);
                    } else {
                        ToastUtils.showLong("请拍摄照片");
                    }
                } else {
                    ToastUtils.showLong("无需上传");
                }
                break;
                default:

        }
    }

    /**
     * 拍照
     */
    private void takeFrontPhoto() {
        String picture_front = Constant.SAVE_DIR_TAKE_PHOTO;
        if (!FileUtils.createOrExistsDir(picture_front)) {
            ToastUtils.showLong("创建文件夹失败");
            return;
        }
        fileFace = new File(picture_front, "face_" + TimeUtils.millis2String(System.currentTimeMillis()) + ".jpeg");
        if (!FileUtils.createFileByDeleteOldFile(fileFace)) {
            ToastUtils.showLong("创建文件失败");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", fileFace);
            Intent captureIntent_front = IntentUtils.getCaptureIntent(photoUri);
            startActivityForResult(captureIntent_front, TAKE_PHOTO_REQUEST_CODE_FACE);
        } else {
            Uri imageUri = Uri.fromFile(fileFace);
            Intent captureIntent_front = IntentUtils.getCaptureIntent(imageUri);
            startActivityForResult(captureIntent_front, TAKE_PHOTO_REQUEST_CODE_FACE);
        }
    }

    /**
     * 权限
     */
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
                                ToastUtils.showLong("获取权限失败");
                            }
                        }).request();
            }
        } else {
            takeFrontPhoto();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==TAKE_PHOTO_REQUEST_CODE_FACE){
            // File file_front = new File(picture_front);
            if (FileUtils.isFile(fileFace)) {
                takeFacePhoto.setVisibility(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap face_image = ImageUtils.getBitmap(fileFace);
                        Bitmap bitmap_front_zip = ImageUtils.compressByQuality(face_image, 40, true);
                        Message message = new Message();
                        message.what = MSG1;
                        message.obj = bitmap_front_zip;
                        //然后将消息发送出去
                        mhandler.sendMessage(message);
                        // mhandler.sendEmptyMessage(MSG1);
                        if (!FileUtils.createOrExistsDir(Constant.SAVE_DIR_GLIDE_CACHE)) {
                            LogUtils.d("创建文件夹失败");
                            return;
                        }
                        fileFaceZip = new File(Constant.SAVE_DIR_GLIDE_CACHE, "image_face.jpeg");
                        Message message2 = new Message();
                        if (FileUtils.createFileByDeleteOldFile(fileFaceZip)) {
                            if (ImageUtils.save(bitmap_front_zip, fileFaceZip, Bitmap.CompressFormat.JPEG, false)) {
                                FileUtils.delete(fileFace);
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
                isFacePictureSuccess = false;
            }
        }
    }
}
