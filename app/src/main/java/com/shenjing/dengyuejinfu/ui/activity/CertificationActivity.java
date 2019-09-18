package com.shenjing.dengyuejinfu.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
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
    private String picture_back;
    private String picture_front;
    private boolean isBackPictureSuccess=false;
    private boolean isFrontPictureSuccess=false;
    /**
     * submit状态控制
     */
    private boolean isCanNext_s=false;
    private boolean isCanEditor_s=false;
    private boolean isCanUpload_s=false;
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
                isFrontPictureSuccess=false;
                getPermissions(1);

                break;
            case R.id.certification_idCard_back:
            case R.id.take_photo_id_back:
                isBackPictureSuccess=false;
                getPermissions(2);
                break;
            case R.id.certification_submit:
                if (isCanUpload_s){
                    if (!isFrontPictureSuccess){
                        ToastUtils.showLong("请拍摄身份证头像面");
                        return;
                    }
                    if (!isBackPictureSuccess){
                        ToastUtils.showLong("请拍摄身份证国徽面");
                        return;
                    }

                    if (StringUtils.isSpace(certificationUserName.getText().toString().trim())){
                        ToastUtils.showLong("请填写姓名");
                        return;
                    }
                    if (StringUtils.isSpace(certificationIdCardNo.getText().toString().trim())){
                        ToastUtils.showLong("请填写银行卡号");
                        return;
                    }
                    Map map=new HashMap();
                    map.put("fornt",picture_front);
                    map.put("back",picture_back);
                    map.put("idNo",certificationIdCardNo.getText().toString().trim());
                    map.put("realName",certificationUserName.getText().toString().trim());
                    map.put("userId",Long.parseLong(BaseParams.userId));
                    mPresenter.uploadPeopleInfo(map);
                } else {
                    if (isCanNext_s){
                        ARouter.getInstance().build(ARouterUrl.BankCardCertificationActivityUrl).navigation(this,new LoginNavigationCallback());
                    }
                }
                break;
        }
    }

    /**
     * 拍摄背面
     */
    private void takeBackPhoto() {
        picture_back = Constant.SAVE_DIR_TAKE_PHOTO + "idCard_back_" + TimeUtils.getNowString() + ".png";
        File file_pre = new File(picture_back);
        if (file_pre.exists()) {
            file_pre.delete();
        }
        Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file_pre);
        Intent captureIntent_pre = IntentUtils.getCaptureIntent(photoUri);
        startActivityForResult(captureIntent_pre, TAKE_PHOTO_REQUEST_CODE_BACK);
    }

    /**
     * 拍摄正面图片
     */
    private void takeFrontPhoto(){
        picture_front = Constant.SAVE_DIR_TAKE_PHOTO + "idCard_front_" + TimeUtils.getNowString() + ".png";
        File file_front = new File(picture_front);
        if (file_front.exists()) {
            file_front.delete();
        }
        Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file_front);
        Intent captureIntent_front = IntentUtils.getCaptureIntent(photoUri);
        startActivityForResult(captureIntent_front, TAKE_PHOTO_REQUEST_CODE_FRONT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE_FRONT:
                File file_front = new File(picture_front);
                if (FileUtils.isFile(file_front)) {
                    takePhotoIdFront.setVisibility(View.INVISIBLE);
                    GlideUtils.initImageByFile(this,file_front,certificationIdCardFront);
                    isFrontPictureSuccess=true;
                } else {
                    isFrontPictureSuccess=false;
                }
                break;
            case TAKE_PHOTO_REQUEST_CODE_BACK:
                File file_back = new File(picture_back);
                if (FileUtils.isFile(file_back)) {
                    takePhotoIdBack.setVisibility(View.INVISIBLE);
                    GlideUtils.initImageByFile(this,file_back,certificationIdCardBack);
                    isBackPictureSuccess=true;
                } else {
                    isBackPictureSuccess=false;
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
    public void getStatusSuccess(PeopleCertificationStatus certificationStatus) {

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
        this.isCanNext_s=isCanNext;
    }

    @Override
    public void isCanUpLoad(boolean isCanUpLoad) {
        this.isCanUpload_s=isCanUpLoad;
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
     * @param i
     */
    private void getPermissions(int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)) {
                    if (i==1){
                        takeFrontPhoto();
                    } else {
                        takeBackPhoto();
                    }
            } else {
                PermissionUtils.permission(PermissionConstants.CAMERA)
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
                                if (i==1){
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
        }else {
            if (i==1){
                takeFrontPhoto();
            } else {
                takeBackPhoto();
            }
        }
    }
}
