package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.ui.contract.CertificationFaceActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.CertificationFaceActivityPresenter;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/9/2711:44
 * version: 1.0
 * desc   :人脸识别
 */
@Route(path = ARouterUrl.CertificationFaceActivityUrl)
public class CertificationFaceActivity extends BaseActivity<CertificationFaceActivityPresenter>
        implements CertificationFaceActivityContract.View {

    @BindView(R.id.face_mStatusBar)
    View faceMStatusBar;
    @BindView(R.id.face_titleBar)
    TitleBar faceTitleBar;
    @BindView(R.id.face_img)
    ImageView faceImg;
    @BindView(R.id.face_submit)
    TextView faceSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification_face;
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
        setStatusBar(faceMStatusBar, R.color.blue1);
        setTittleBarBackground(faceTitleBar, R.color.blue1);
        setPageTitle(faceTitleBar, "人脸识别", R.color.white);
        setPageLeftText(faceTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {

    }

    @Override
    public void isCanNext(boolean isCanNext) {

    }

    @Override
    public void isCanUpLoad(boolean isCanUpLoad) {

    }

    @Override
    public void isCanEditor(boolean isCanEditor) {

    }

    @OnClick(R.id.face_submit)
    public void onClick() {

    }
}
