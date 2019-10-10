package com.shenjing.dengyuejinfu.ui.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.entity.BankBean;
import com.shenjing.dengyuejinfu.entity.CityBean;
import com.shenjing.dengyuejinfu.entity.ProvinceBean;
import com.shenjing.dengyuejinfu.entity.SubBranchBank;
import com.shenjing.dengyuejinfu.net.RetrofitManager;
import com.shenjing.dengyuejinfu.net.RxSchedulers;
import com.shenjing.dengyuejinfu.net.services.SelectBankApi;
import com.shenjing.dengyuejinfu.ui.contract.SelectBankDialogContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class SelectBankDialogPresenter extends BasePresenter<SelectBankDialogContract.View>
        implements SelectBankDialogContract.Presenter {


    @Inject
    public SelectBankDialogPresenter() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void getBankList() {
        RetrofitManager.create(SelectBankApi.class).getBank()
                .compose(mView.<List<BankBean>>bindToLife())
                .compose(RxSchedulers.<List<BankBean>>applySchedulers())
                .subscribe(new Consumer<List<BankBean>>() {
                    @Override
                    public void accept(List<BankBean> bankBeanList) throws Exception {
                        mView.getBankAdapter().refreshPosition();
                        mView.getBankAdapter().setNewData(bankBeanList);
                    }
                }, this::loadBankError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getProvince() {
        RetrofitManager.create(SelectBankApi.class).getProvince()
                .compose(mView.<List<ProvinceBean>>bindToLife())
                .compose(RxSchedulers.<List<ProvinceBean>>applySchedulers())
                .subscribe(new Consumer<List<ProvinceBean>>() {
                    @Override
                    public void accept(List<ProvinceBean> provinceBeanList) throws Exception {
                        mView.getProvinceAdapter().refreshPosition();
                        mView.getProvinceAdapter().setNewData(provinceBeanList);
                    }
                }, this::loadProvinceError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCity(String provinceId) {
        RetrofitManager.create(SelectBankApi.class).getCity(provinceId)
                .compose(mView.<List<CityBean>>bindToLife())
                .compose(RxSchedulers.<List<CityBean>>applySchedulers())
                .subscribe(new Consumer<List<CityBean>>() {
                    @Override
                    public void accept(List<CityBean> cityBeanList) throws Exception {
                        mView.getCityAdapter().refreshPosition();
                        mView.getCityAdapter().setNewData(cityBeanList);
                    }
                }, this::loadCityError);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getBranchBank(String bankId, String cityId) {
        RetrofitManager.create(SelectBankApi.class).getBranchBank(bankId, cityId)
                .compose(mView.<List<SubBranchBank>>bindToLife())
                .compose(RxSchedulers.<List<SubBranchBank>>applySchedulers())
                .subscribe(new Consumer<List<SubBranchBank>>() {
                    @Override
                    public void accept(List<SubBranchBank> subBranchBankList) throws Exception {
                        mView.getBranchBankAdapter().refreshPosition();
                        mView.getBranchBankAdapter().setNewData(subBranchBankList);

                    }
                }, this::loadBranchBankError);
    }

    private void loadBankError(Throwable throwable) {
        throwable.printStackTrace();
        ToastUtils.showShort("加载错误,请重试");
        mView.getBankAdapter().setEmptyView(R.layout.view_error, mView.getBankRecycler());
    }

    private void loadProvinceError(Throwable throwable) {
        throwable.printStackTrace();
        ToastUtils.showShort("加载错误,请重试");
        mView.getProvinceAdapter().setEmptyView(R.layout.view_error, mView.getProvinceRecycler());
    }

    private void loadCityError(Throwable throwable) {
        throwable.printStackTrace();
        ToastUtils.showShort("加载错误,请重试");
        mView.getCityAdapter().setEmptyView(R.layout.view_error, mView.getCityRecycler());
    }

    private void loadBranchBankError(Throwable throwable) {
        throwable.printStackTrace();
        ToastUtils.showShort("加载错误,请重试");
        mView.getBranchBankAdapter().setEmptyView(R.layout.view_error, mView.getBranchBankRecycler());
    }
}
