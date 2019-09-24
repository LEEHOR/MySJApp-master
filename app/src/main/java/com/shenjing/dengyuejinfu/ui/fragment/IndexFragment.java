package com.shenjing.dengyuejinfu.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.donkingliang.banner.CustomBanner;
import com.shenjing.dengyuejinfu.App;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseFragment;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.respondModule.BannerModel;
import com.shenjing.dengyuejinfu.respondModule.VersionModel;
import com.shenjing.dengyuejinfu.ui.contract.IndexFragmentContract;
import com.shenjing.dengyuejinfu.ui.presenter.IndexFragmentPresenter;
import com.shenjing.dengyuejinfu.utils.CompareVersion;
import com.shenjing.dengyuejinfu.utils.SetCustomBannerUtils;
import com.shenjing.dengyuejinfu.utils.VersionDownLoad;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/8/1216:29
 * version: 1.0
 * desc   :首页
 */
public class IndexFragment extends BaseFragment<IndexFragmentPresenter> implements IndexFragmentContract.View {
    @BindView(R.id.index_mStatusBar)
    View mStatusBar;
    @BindView(R.id.index_titleBar)
    TitleBar fragmentCouponTitleBar;
    @BindView(R.id.index_swipe)
    SwipeRefreshLayout indexSwipe;
    @BindView(R.id.index_cardEvaluation)
    CardView indexCardEvaluation;
    @BindView(R.id.index_creditInquiry)
    CardView indexCreditInquiry;
    @BindView(R.id.index_banner)
    CustomBanner<String> indexBanner;
    @BindView(R.id.index_bannerView)
    TextBannerView indexBannerView;
    @BindView(R.id.index_IncreaseTheQuota)
    LinearLayout indexIncreaseTheQuota;
    private List<String> bannerList = new ArrayList<>();
    private List<String> marqueeList = new ArrayList<>();
    private static final int INSTALL_PERMISSION_SETTING = 1;
    private String apkDownLoadUrl;
    private String versionId;

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        initFragmentComponent().inject(this);

    }

    @Override
    protected void initView() {
        setStatusBar(mStatusBar, R.color.white);
        setPageTitle(fragmentCouponTitleBar, "腾云金服", R.color.textColor);
        setTittleBarBackgraound(fragmentCouponTitleBar, R.color.white);
        setTitleRightImage(fragmentCouponTitleBar, R.drawable.icon_message, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                ARouter.getInstance().build(ARouterUrl.AnnouncementActivityUrl).navigation();
            }
        });
        indexSwipe.setColorSchemeResources(R.color.blue4);
    }

    @Override
    protected void initFunc() {
        getPermissions();
        mPresenter.Banner("1");

        indexSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.Banner("1");
                //mPresenter.getVersion();
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LogUtils.d("showFragment--index");
        }
    }

    @OnClick({R.id.index_cardEvaluation, R.id.index_creditInquiry,R.id.index_IncreaseTheQuota})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_cardEvaluation:
                ARouter.getInstance().build(ARouterUrl.CardEvaluationActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
            case R.id.index_creditInquiry:
                ARouter.getInstance().build(ARouterUrl.CreditInquiryActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
            case R.id.index_IncreaseTheQuota:
                ARouter.getInstance().build(ARouterUrl.IncreaseQuotaActivityUrl).navigation(getActivity(),new LoginNavigationCallback());
                break;
        }
    }

    @Override
    public void getBannerSuccess(BannerModel bannerModel) {
        if (bannerModel.getData() != null) {
            List<BannerModel.DataBean.BannerBean> banner = bannerModel.getData().getBanner();
            if (banner != null) {
                bannerList.clear();
                for (BannerModel.DataBean.BannerBean b : banner
                ) {
                    bannerList.add(b.getPath());
                }
                SetCustomBannerUtils.setCustomBanner(indexBanner, bannerList, ImageView.ScaleType.FIT_CENTER);
                setBannerLister(banner, indexBanner);
            }
            List<BannerModel.DataBean.MarqueeBean> marquee = bannerModel.getData().getMarquee();
            if (marquee != null) {
                marqueeList.clear();
                for (BannerModel.DataBean.MarqueeBean marqueeBean : marquee
                ) {
                    marqueeList.add(marqueeBean.getContent());
                }
            }
            setTextBanner(indexBannerView, marqueeList);

        }
    }

    @Override
    public void getBannerFailure() {

    }


    @Override
    public void Refresh(boolean Refresh) {
        indexSwipe.setRefreshing(Refresh);
    }

    @Override
    public void getVersionSuccess(VersionModel versionModel) {
        if (versionModel != null) {
            if (versionModel.getData() != null) {
                versionId = versionModel.getData().getVersionId();
                apkDownLoadUrl = versionModel.getData().getPath();
                String appVersionName = AppUtils.getAppVersionName();
                int i = CompareVersion.compareVersionNumber(appVersionName, versionId);
                LogUtils.d("版本", i);
                if (i == 1) {

                    showVisionDialog(versionModel.getData().getDescription(), versionId
                            , versionModel.getData().getForceUpdate()
                            , apkDownLoadUrl);
                }
            }
        }
    }

    @Override
    public void getVersionFailure() {

    }

    /**
     * banner轮播点击监听
     *
     * @param bannerBean
     * @param customBanner
     */
    private void setBannerLister(List<BannerModel.DataBean.BannerBean> bannerBean, CustomBanner customBanner) {
        customBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int i, Object o) {
                ARouter.getInstance().build(ARouterUrl.WebViewActivityUrl)
                        .withString(BaseParams.webViewTitle, bannerBean.get(i).getTitle())
                        .withString(BaseParams.webViewUrl, bannerBean.get(i).getUrl())
                        .navigation(getActivity(), new LoginNavigationCallback());
            }
        });
    }

    /**
     * 轮播公告
     *
     * @param textBanner
     * @param _marquee
     */
    private void setTextBanner(TextBannerView textBanner, List<String> _marquee) {
        textBanner.setDatas(_marquee);
        textBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        indexBannerView.stopViewAnimator();
    }

    @Override
    public void onResume() {
        super.onResume();
        indexBannerView.startViewAnimator();
    }


    /**
     * 获取手机联系人
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)) {
                mPresenter.getVersion();
            } else {
                PermissionUtils.permission(PermissionConstants.STORAGE)
                        .rationale(new PermissionUtils.OnRationaleListener() {
                            @Override
                            public void rationale(ShouldRequest shouldRequest) {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .callback(new PermissionUtils.FullCallback() {
                            @Override
                            public void onGranted(List<String> permissionsGranted) {
                                mPresenter.getVersion();
                            }

                            @Override
                            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                ToastUtils.showLong("获取权限失败");
                            }
                        }).request();
            }
        } else {
            mPresenter.getVersion();
        }
    }

    /**
     * 更新的弹窗
     */
    private void showVisionDialog(String describe, String visionId, String forceUpdate, String apkDownLoadUrl) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity()).build();
        materialDialog.setTitle("发现更新，版本" + visionId);
        materialDialog.setContent(describe);
        if (forceUpdate.equals("1")) {
            materialDialog.setCancelable(false);
            materialDialog.setCanceledOnTouchOutside(false);
            materialDialog.setActionButton(DialogAction.NEUTRAL, "去更新");
        } else {
            materialDialog.setCancelable(true);
            materialDialog.setCanceledOnTouchOutside(true);
            materialDialog.setActionButton(DialogAction.POSITIVE, "去更新");
            materialDialog.setActionButton(DialogAction.NEGATIVE, "下次再说");
        }
        materialDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasInstallPermission(apkDownLoadUrl);
                materialDialog.dismiss();
            }
        });
        materialDialog.getActionButton(DialogAction.NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasInstallPermission(apkDownLoadUrl);
                materialDialog.dismiss();
            }
        });
        materialDialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    /**
     * 检查android8.0有无安装权限
     */
    private void hasInstallPermission(String apkDownLoadUrl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPermission = getActivity().getPackageManager().canRequestPackageInstalls();
            if (!hasInstallPermission) {
                //开启权限页面
                startInstallPermissionSettingActivity();
            } else {
                VersionDownLoad.startDownLoad(App.getAppContext(), apkDownLoadUrl);
            }
        }
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivityForResult(intent, INSTALL_PERMISSION_SETTING);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INSTALL_PERMISSION_SETTING && resultCode == Activity.RESULT_OK) {
            VersionDownLoad.startDownLoad(App.getAppContext(), apkDownLoadUrl);
        }
    }

}
