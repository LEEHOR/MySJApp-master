package com.shenjing.dengyuejinfu.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.security.rp.RPSDK;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import com.shenjing.dengyuejinfu.utils.YunDunHelper.YunDunHelper;
import com.shenjing.dengyuejinfu.utils.YunDunHelper.YunDunListener;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import java.io.File;
import java.util.List;

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
        implements CertificationActivityContract.View, YunDunListener {
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
    private PeopleCertificationBean pcb;
    private String verifyToken;
    private final static int MSG1 = 1;
    private final static int MSG2 = 2;
    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG1:
                    takePhotoIdFront.setVisibility(View.INVISIBLE);
                    GlideUtils.initImageByBytes(CertificationActivity.this, (byte[]) msg.obj, certificationIdCardFront);
                    break;
                case MSG2:
                    takePhotoIdBack.setVisibility(View.INVISIBLE);
                    GlideUtils.initImageByBytes(CertificationActivity.this, (byte[]) msg.obj, certificationIdCardBack);
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
        YunDunHelper.getInstance().setYunDunListener(this);
        certificationIdCardFront.setEnabled(false);
        certificationIdCardBack.setEnabled(false);
        takePhotoIdFront.setEnabled(false);
        takePhotoIdBack.setEnabled(false);
        mPresenter.getPeopleStatus(BaseParams.userId);
    }

    @OnClick({R.id.take_photo_id_back, R.id.take_photo_id_front, R.id.certification_submit, R.id.certification_idCard_front, R.id.certification_idCard_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.certification_idCard_front:
            case R.id.take_photo_id_front:
            case R.id.certification_idCard_back:
            case R.id.take_photo_id_back:
                mPresenter.verifyToken(BaseParams.userId);
                break;
            case R.id.certification_submit:
                if (isCanNext_s) {
                    ARouter.getInstance().build(ARouterUrl.BankCardCertificationActivityUrl).navigation(this, new LoginNavigationCallback());
                } else {
                    ToastUtils.showLong("请进行实名认证");
                }

                break;
        }
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
                GlideUtils.initImageWithFileCache(CertificationActivity.this, certificationStatus_s.getData().getFront(), certificationIdCardFront);

            }
            if (certificationStatus_s.getData().getBack() != null) {
                takePhotoIdBack.setVisibility(View.INVISIBLE);
                GlideUtils.initImageWithFileCache(CertificationActivity.this, certificationStatus_s.getData().getBack(), certificationIdCardBack);

            }
            certificationUserName.setText(certificationStatus_s.getData().getReal_name());
            certificationIdCardNo.setText(certificationStatus_s.getData().getId_no());
            certificationAddress.setText(certificationStatus_s.getData().getAddress() != null ? certificationStatus_s.getData().getAddress() : "");
        }
    }

    @Override
    public void getStatusFailure() {

    }

    @Override
    public void getVerifyTokenSuccess(String token) {
        this.verifyToken = token;
        getPermissions(token);
    }

    @Override
    public void getVerifyTokenFailure() {

    }

    @Override
    public void getOcrResultSuccess() {
        mPresenter.getPeopleStatus(BaseParams.userId);

    }

    @Override
    public void getOcrResultFailure() {

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
    public void isCanEditor(boolean isCanEditor) {
        certificationIdCardFront.setEnabled(isCanEditor);
        certificationIdCardBack.setEnabled(isCanEditor);
        takePhotoIdFront.setEnabled(isCanEditor);
        takePhotoIdBack.setEnabled(isCanEditor);
    }

    /**
     * 权限
     *
     * @param
     */
    private void getPermissions(String token) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA)) {
                startRPSDK(token);
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
                                startRPSDK(token);
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong(R.string.toast_14);
                            }
                        }).request();
            }
        } else {
            startRPSDK(token);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }

    /**
     * 获取身份证的显示的图片
     *
     * @param tag
     */
    public void getFileDisplay(File file, int tag) {
        if (FileUtils.isFileExists(file)) {
            Message message = Message.obtain();
            message.what = tag;
            message.obj = file;
            mhandler.sendMessage(message);
        }
    }

    /**
     * 获取身份证的显示的图片
     *
     * @param tag
     */
    public void getByteDisplay(byte[] bytes, int tag) {
        if (bytes != null) {
            Message message = Message.obtain();
            message.what = tag;
            message.obj = bytes;
            mhandler.sendMessage(message);
        }
    }

    private void startRPSDK(String token) {

        YunDunHelper.getInstance().startAliOcr(token, CertificationActivity.this);
    }

    @Override
    public void YunDunSuccess() {
        LogUtils.d("识别", "成功");
        mPresenter.sendOcrResult(BaseParams.userId, "1", verifyToken);

    }

    @Override
    public void YunDunFailure() {
        LogUtils.d("识别", "失败");
        mPresenter.sendOcrResult(BaseParams.userId, "2", verifyToken);
    }

    @Override
    public void YunDunNot() {
        LogUtils.d("识别", "不符合");
        mPresenter.sendOcrResult(BaseParams.userId, "-1", verifyToken);
        ToastUtils.showLong("认证失败重新认证");
    }
}
