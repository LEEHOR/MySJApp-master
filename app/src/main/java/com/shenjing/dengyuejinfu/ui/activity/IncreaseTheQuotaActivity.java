package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.donkingliang.banner.CustomBanner;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.common.LoginNavigationCallback;
import com.shenjing.dengyuejinfu.decoration.SpacesItemDecoration;
import com.shenjing.dengyuejinfu.entity.BannerBean;
import com.shenjing.dengyuejinfu.entity.IncreaseQuotaBean;
import com.shenjing.dengyuejinfu.ui.activity.adapter.IncreaseQuotaAdapter;
import com.shenjing.dengyuejinfu.ui.contract.IncreaseTheQuotaActivityContract;
import com.shenjing.dengyuejinfu.ui.presenter.IncreaseTheQuotaActivityPresenter;
import com.shenjing.dengyuejinfu.utils.SetCustomBannerUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/9/2414:00
 * version: 1.0
 * desc   :提高额度页面
 */
@Route(path = ARouterUrl.IncreaseQuotaActivityUrl)
public class IncreaseTheQuotaActivity extends BaseActivity<IncreaseTheQuotaActivityPresenter>
        implements IncreaseTheQuotaActivityContract.View {
    @BindView(R.id.increase_quota_mStatusBar)
    View increaseQuotaMStatusBar;
    @BindView(R.id.increase_quota_titleBar)
    TitleBar increaseQuotaTitleBar;
    @BindView(R.id.increase_quota_banner)
    CustomBanner increaseQuotaBanner;
    @BindView(R.id.increase_quota_bannerText)
    TextBannerView increaseQuotaBannerText;
    @BindView(R.id.increase_quota_recycler)
    RecyclerView increaseQuotaRecycler;
    @BindView(R.id.increase_quota_swipe)
    SwipeRefreshLayout increaseQuotaSwipe;
    private GridLayoutManager gridLayoutManager;
    private IncreaseQuotaAdapter increaseQuotaAdapter;
    private List<String> bannerList = new ArrayList<>();
    private List<String> marqueeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_increase_quota;
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
        setStatusBar(increaseQuotaMStatusBar, R.color.blue1);
        setPageTitle(increaseQuotaTitleBar, "提额秘籍", R.color.white);
        setTittleBarBackground(increaseQuotaTitleBar, R.color.blue1);
        setPageLeftText(increaseQuotaTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
        increaseQuotaSwipe.setColorSchemeResources(R.color.blue4);
    }

    @Override
    protected void initFunc() {
        increaseQuotaSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getIncreaseBanner("2");
                mPresenter.Refresh();
            }
        });

        increaseQuotaAdapter = new IncreaseQuotaAdapter(IncreaseTheQuotaActivity.this);
        gridLayoutManager = new GridLayoutManager(IncreaseTheQuotaActivity.this, 2, RecyclerView.VERTICAL, false);
        increaseQuotaRecycler.setLayoutManager(gridLayoutManager);
        increaseQuotaRecycler.setAdapter(increaseQuotaAdapter);
        increaseQuotaRecycler.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8f)
                , ConvertUtils.dp2px(8f)));

        mPresenter.getIncreaseBanner("2");
        mPresenter.getIncreaseData();

        increaseQuotaAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                IncreaseQuotaBean.DataBean dataBean = (IncreaseQuotaBean.DataBean) adapter.getData().get(position);
                ARouter.getInstance().build(ARouterUrl.IncreaseQuotaInformationActivityUrl)
                        .withInt(BaseParams.IncreaseQuotaType, dataBean.getId())
                        .navigation(IncreaseTheQuotaActivity.this, new LoginNavigationCallback());
            }
        });
    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFailure() {

    }

    @Override
    public IncreaseQuotaAdapter getAdapter() {
        return increaseQuotaAdapter;
    }

    @Override
    public RecyclerView getRecycler() {
        return increaseQuotaRecycler;
    }

    @Override
    public void isCanRefresh(boolean refresh) {
        increaseQuotaSwipe.setRefreshing(refresh);
    }

    @Override
    public void getBannerSuccess(BannerBean bannerBean) {
        if (bannerBean.getData() != null) {
            List<BannerBean.DataBean.Banner> banner = bannerBean.getData().getBanner();
            if (banner != null) {
                bannerList.clear();
                for (BannerBean.DataBean.Banner b : banner
                ) {
                    bannerList.add(b.getPath());
                }
                SetCustomBannerUtils.setCustomBanner(increaseQuotaBanner, bannerList, ImageView.ScaleType.FIT_CENTER);
                setBannerLister(banner, increaseQuotaBanner);
            }
            List<BannerBean.DataBean.MarqueeBean> marquee = bannerBean.getData().getMarquee();
            if (marquee != null) {
                marqueeList.clear();
                for (BannerBean.DataBean.MarqueeBean marqueeBean : marquee
                ) {
                    marqueeList.add(marqueeBean.getContent());
                }
            }
            setTextBanner(increaseQuotaBannerText, marqueeList);

        }
    }

    @Override
    public void getBannerFailure() {

    }

    /**
     * banner轮播点击监听
     *
     * @param bannerBean
     * @param customBanner
     */
    private void setBannerLister(List<BannerBean.DataBean.Banner> bannerBean, CustomBanner customBanner) {
        customBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int i, Object o) {
//                ARouter.getInstance().build(ARouterUrl.WebViewActivityUrl)
//                        .withString(BaseParams.webViewTitle, bannerBean.get(i).getTitle())
//                        .withString(BaseParams.webViewUrl, bannerBean.get(i).getUrl())
//                        .navigation(IncreaseTheQuotaActivity.this, new LoginNavigationCallback());
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
        increaseQuotaBannerText.stopViewAnimator();
    }

    @Override
    public void onResume() {
        super.onResume();
        increaseQuotaBannerText.startViewAnimator();
    }
}
