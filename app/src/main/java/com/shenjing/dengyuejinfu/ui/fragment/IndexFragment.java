package com.shenjing.dengyuejinfu.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.donkingliang.banner.CustomBanner;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseFragment;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.respondModule.BannerModel;
import com.shenjing.dengyuejinfu.ui.contract.IndexFragmentContract;
import com.shenjing.dengyuejinfu.ui.presenter.IndexFragmentPresenter;
import com.shenjing.dengyuejinfu.utils.SetCustomBannerUtils;
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
    private List<String> bannerList=new ArrayList<>();
    private List<String> marqueeList=new ArrayList<>();

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
        mPresenter.Banner();
        indexSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.Banner();
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

    @OnClick({R.id.index_cardEvaluation, R.id.index_creditInquiry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_cardEvaluation:
                ARouter.getInstance().build(ARouterUrl.CardEvaluationActivityUrl).navigation();
                break;
            case R.id.index_creditInquiry:
                ARouter.getInstance().build(ARouterUrl.CreditInquiryActivityUrl).navigation();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void getBannerSuccess(BannerModel bannerModel) {
        if (bannerModel.getData() != null) {
            List<BannerModel.DataBean.BannerBean> banner = bannerModel.getData().getBanner();
            if (banner != null) {
                bannerList.clear();
                for (BannerModel.DataBean.BannerBean b: banner
                ) {
                    bannerList.add(b.getPath());
                }
                SetCustomBannerUtils.setCustomBanner(indexBanner,bannerList, ImageView.ScaleType.FIT_CENTER);
                setBannerLister(banner,indexBanner);
            }
            List<String> marquee = bannerModel.getData().getMarquee();
            if (marquee != null) {
                marqueeList.clear();
                marqueeList.addAll(marquee);
            }
            setTextBanner(indexBannerView,marqueeList);

        }
    }

    @Override
    public void getBannerFailure() {

    }


    @Override
    public void Refresh(boolean Refresh) {
        indexSwipe.setRefreshing(Refresh);
    }

    /**
     * banner轮播点击监听
     * @param bannerBean
     * @param customBanner
     */
    private void setBannerLister(List<BannerModel.DataBean.BannerBean> bannerBean, CustomBanner customBanner){
        customBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int i, Object o) {
                ARouter.getInstance().build(ARouterUrl.WebViewActivityUrl)
                        .withString(BaseParams.webViewTitle,bannerBean.get(i).getTitle())
                        .withString(BaseParams.webViewUrl,bannerBean.get(i).getUrl())
                        .navigation(getActivity(),new LoginNavigationCallback());
            }
        });
    }

    /**
     * 轮播公告
     * @param textBanner
     * @param _marquee
     */
    private void setTextBanner(TextBannerView textBanner,List<String> _marquee){
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
}
