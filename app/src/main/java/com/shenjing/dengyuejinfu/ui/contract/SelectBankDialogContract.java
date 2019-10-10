package com.shenjing.dengyuejinfu.ui.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterBank;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterBranchBank;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterCity;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterProvince;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class SelectBankDialogContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        RecyclerView getBankRecycler();

        RecyclerView getProvinceRecycler();

        RecyclerView getCityRecycler();

        RecyclerView getBranchBankRecycler();

        adapterBank getBankAdapter();

        adapterProvince getProvinceAdapter();

        adapterCity getCityAdapter();

        adapterBranchBank getBranchBankAdapter();

    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

            void getBankList();

            void getProvince();

            void getCity(String provinceId);

            void getBranchBank(String bankId,String cityId);

    }
}
