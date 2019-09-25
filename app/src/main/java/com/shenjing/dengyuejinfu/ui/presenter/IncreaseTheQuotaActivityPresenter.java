package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.IncreaseQuotaApi;
import com.shenjing.dengyuejinfu.net.services.IndexFragmentApi;
import com.shenjing.dengyuejinfu.entity.BannerBean;
import com.shenjing.dengyuejinfu.entity.IncreaseQuotaBean;
import com.shenjing.dengyuejinfu.ui.contract.IncreaseTheQuotaActivityContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class IncreaseTheQuotaActivityPresenter extends BasePresenter<IncreaseTheQuotaActivityContract.View>
        implements IncreaseTheQuotaActivityContract.Presenter {
    private Context mContext;

    @Inject
    public IncreaseTheQuotaActivityPresenter(Context context) {
        this.mContext = context;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getIncreaseBanner(String actionScope) {

        mView.showLoading();
        // mView.Refresh(false);
        RetrofitManager.create(IndexFragmentApi.class).getBanner(actionScope)
                .compose(mView.<BannerBean>bindToLife())
                .compose(RxSchedulers.<BannerBean>applySchedulers())
                .subscribe(new Consumer<BannerBean>() {
                    @Override
                    public void accept(BannerBean bannerBean) {
                        mView.hideLoading();
                        if (bannerBean.getCode() != null && bannerBean.getCode().equals("0000")) {
                            mView.showSuccess(bannerBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getBannerSuccess(bannerBean);

                        } else {
                            mView.showFail(bannerBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getBannerFailure();

                        }
                    }
                }, this::getBannerError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getIncreaseData() {
        mView.showLoading();
        // mView.Refresh(false);
        RetrofitManager.create(IncreaseQuotaApi.class).getIncreaseData()
                .compose(mView.<IncreaseQuotaBean>bindToLife())
                .compose(RxSchedulers.<IncreaseQuotaBean>applySchedulers())
                .subscribe(new Consumer<IncreaseQuotaBean>() {
                    @Override
                    public void accept(IncreaseQuotaBean increaseQuotaBean) {
                        mView.hideLoading();
                        if (increaseQuotaBean.getCode() != null && increaseQuotaBean.getCode().equals("0000")) {
                            mView.showSuccess(increaseQuotaBean.getMsg());
                            mView.isCanRefresh(false);
                            if (increaseQuotaBean.getData() != null && increaseQuotaBean.getData().size()>0) {
                                mView.getAdapter().setNewData(increaseQuotaBean.getData());
                            } else {
                                mView.getAdapter().setEmptyView(R.layout.view_empty,mView.getRecycler());
                            }

                        } else {
                            mView.showFail(increaseQuotaBean.getMsg());
                            mView.isCanRefresh(false);
                            mView.getAdapter().setEmptyView(R.layout.view_error,mView.getRecycler());
                        }
                    }
                }, this::getIncreaseError);
    }

    @Override
    public void Refresh() {
        getIncreaseData();
    }

    private void getBannerError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
        mView.isCanRefresh(false);
    }

    private void getIncreaseError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误");
        mView.isCanRefresh(false);
        mView.getAdapter().setEmptyView(R.layout.view_error,mView.getRecycler());
    }

}
