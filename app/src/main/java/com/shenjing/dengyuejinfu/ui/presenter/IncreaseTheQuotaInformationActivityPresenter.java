package com.shenjing.dengyuejinfu.ui.presenter;


import android.annotation.SuppressLint;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.IncreaseQuotaApi;
import com.shenjing.dengyuejinfu.respondModule.BannerModel;
import com.shenjing.dengyuejinfu.respondModule.IncreaseQuotaInformationModel;
import com.shenjing.dengyuejinfu.ui.contract.IncreaseTheQuotaInformationActivityContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class IncreaseTheQuotaInformationActivityPresenter extends BasePresenter<IncreaseTheQuotaInformationActivityContract.View>
        implements IncreaseTheQuotaInformationActivityContract.Presenter {
    private Context mContext;
    private int nextPage=1;
    private int PAGE_SIZE = 10;
    private String userId;
    private String type;

    @Inject
    public IncreaseTheQuotaInformationActivityPresenter(Context context) {
        this.mContext = context;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getInformation(String userId, String type, int page) {
        this.userId = userId;
        this.type = type;
        mView.showLoading();
        mView.getAdapter().setEnableLoadMore(false);
        // mView.Refresh(false);
        RetrofitManager.create(IncreaseQuotaApi.class).getIncreaseInformationData(Long.parseLong(userId), type, page)
                .compose(mView.<IncreaseQuotaInformationModel>bindToLife())
                .compose(RxSchedulers.<IncreaseQuotaInformationModel>applySchedulers())
                .subscribe(new Consumer<IncreaseQuotaInformationModel>() {
                    @Override
                    public void accept(IncreaseQuotaInformationModel increaseQuotaInformationModel) {
                        mView.hideLoading();
                        if (increaseQuotaInformationModel.getCode() != null && increaseQuotaInformationModel.getCode().equals("0000")) {
                            mView.showSuccess(increaseQuotaInformationModel.getMsg());
                            mView.isCanRefresh(false);
                            if (increaseQuotaInformationModel.getData() != null) {
                                mView.getAdapter().setNewData(increaseQuotaInformationModel.getData());

                            } else {
                                mView.getAdapter().setEmptyView(R.layout.view_empty, mView.getRecycler());
                            }

                        } else {
                            mView.showFail(increaseQuotaInformationModel.getMsg());
                            mView.isCanRefresh(false);
                            mView.getAdapter().setEmptyView(R.layout.view_empty, mView.getRecycler());
                        }
                        mView.getAdapter().setEnableLoadMore(true);
                    }
                }, this::getInformationError);
    }

    private void getInformationError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showLong("加载错误");
        mView.isCanRefresh(false);
        mView.getAdapter().setEmptyView(R.layout.view_empty, mView.getRecycler());
        mView.getAdapter().setEnableLoadMore(true);
        mView.getAdapter().loadMoreFail();
    }

    @Override
    public void refresh() {
        this.nextPage = 1;
        mView.getAdapter().setEnableLoadMore(false);
        startLoadMoreOrRefresh(1);
    }

    @Override
    public void loadMore() {
        mView.getAdapter().setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                startLoadMoreOrRefresh(2);
            }
        }, mView.getRecycler());

    }

    /**
     * 开始加载更多
     *
     */
    /**
     * @param t 1 :加载更多
     *          2 ：刷新
     */
    @SuppressLint("CheckResult")
    private void startLoadMoreOrRefresh(int t) {
        if (t == 1) {
            mView.showLoading();
        }
        RetrofitManager.create(IncreaseQuotaApi.class).getIncreaseInformationData(Long.parseLong(userId), type,t==1?0:nextPage)
                .compose(mView.<IncreaseQuotaInformationModel>bindToLife())
                .compose(RxSchedulers.<IncreaseQuotaInformationModel>applySchedulers())
                .subscribe(new Consumer<IncreaseQuotaInformationModel>() {
                    @Override
                    public void accept(IncreaseQuotaInformationModel increaseQuotaInformationModel) {
                        if (t == 1) {
                            mView.isCanRefresh(false);
                            mView.hideLoading();
                            mView.getAdapter().setEnableLoadMore(true);
                        }
                        if (increaseQuotaInformationModel.getCode() != null && increaseQuotaInformationModel.getCode().equals("0000")) {
                            mView.showSuccess(increaseQuotaInformationModel.getMsg());
                            boolean isLoadMoreEnd = nextPage == 1;
                            setData(isLoadMoreEnd, increaseQuotaInformationModel.getData());

                        } else {
                            mView.showFail(increaseQuotaInformationModel.getMsg());
                            if (t == 2) {
                                mView.getAdapter().loadMoreFail();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadMoreError(throwable, t);
                    }
                });
    }

    private void loadMoreError(Throwable throwable, int t) {
        throwable.printStackTrace();
        ToastUtils.showLong("加载错误");
        if (t == 2) {
            mView.getAdapter().loadMoreFail();
        } else {
            mView.isCanRefresh(false);
            mView.hideLoading();
            mView.getAdapter().setEnableLoadMore(true);
            mView.getAdapter().setEmptyView(R.layout.view_empty, mView.getRecycler());
        }

    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param dataBean
     */
    private void setData(boolean isRefresh, List<IncreaseQuotaInformationModel.DataBean> dataBean) {
        final int size = dataBean == null ? 0 : dataBean.size();
        if (isRefresh) {
            mView.getAdapter().setNewData(dataBean);
        } else {
            nextPage++;
            if (size > 0) {
                mView.getAdapter().addData(dataBean);
            }
            if (size < PAGE_SIZE) {
                //第一页如果不够一页就不显示没有更多数据布局
                mView.getAdapter().loadMoreEnd(isRefresh);
            } else {
                mView.getAdapter().loadMoreComplete();
            }
        }
    }
}
