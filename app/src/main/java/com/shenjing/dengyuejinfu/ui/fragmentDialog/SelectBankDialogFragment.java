package com.shenjing.dengyuejinfu.ui.fragmentDialog;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseDialogFragment;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.decoration.SpacesItemDecoration;
import com.shenjing.dengyuejinfu.entity.BankBean;
import com.shenjing.dengyuejinfu.entity.CityBean;
import com.shenjing.dengyuejinfu.entity.ProvinceBean;
import com.shenjing.dengyuejinfu.entity.SubBranchBank;
import com.shenjing.dengyuejinfu.ui.contract.SelectBankDialogContract;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterBank;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterBranchBank;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterCity;
import com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterProvince;
import com.shenjing.dengyuejinfu.ui.presenter.SelectBankDialogPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/10/914:57
 * version: 1.0
 * desc   :选择所属银行
 */
@Route(path = ARouterUrl.SelectBankDialogFragmentUrl)
public class SelectBankDialogFragment extends BaseDialogFragment<SelectBankDialogPresenter>
        implements SelectBankDialogContract.View {
    @BindView(R.id.dialog_complete)
    TextView dialogComplete;
    @BindView(R.id.dialog_dismiss)
    TextView dialogDismiss;
    @BindView(R.id.dialog_bank_name)
    TextView dialogBankName;
    @BindView(R.id.dialog_select_province)
    TextView dialogSelectProvince;
    @BindView(R.id.dialog_select_city)
    TextView dialogSelectCity;
    @BindView(R.id.dialog_select_bank_branch)
    AppCompatTextView dialogSelectBankBranch;
    @BindView(R.id.dialog_recycler_bank)
    RecyclerView dialogRecyclerBank;
    @BindView(R.id.dialog_recycler_province)
    RecyclerView dialogRecyclerProvince;
    @BindView(R.id.dialog_recycler_city)
    RecyclerView dialogRecyclerCity;
    @BindView(R.id.dialog_recycler_bank_branch)
    RecyclerView dialogRecyclerBankBranch;
    private adapterBank adapterBank;
    private adapterProvince adapterProvince;
    private adapterCity adapterCity;
    private adapterBranchBank adapterBranchBank;
    private selectBankListener selectBankListener_s;
    private String bankId;
    private String subBranchId;
    private String provinceCode;
    private String cityCode;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_province_city_area;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        initDialogComponent().inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager bankManager = new LinearLayoutManager(getActivity());
        adapterBank = new adapterBank();
        dialogRecyclerBank.setLayoutManager(bankManager);
        dialogRecyclerBank.setAdapter(adapterBank);
        adapterBank.setEmptyView(R.layout.view_empty, dialogRecyclerBank);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialogRecyclerBank.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1, null)));
        } else {
            dialogRecyclerBank.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1)));
        }

        adapterProvince = new adapterProvince();
        LinearLayoutManager provinceManager = new LinearLayoutManager(getActivity());
        dialogRecyclerProvince.setLayoutManager(provinceManager);
        dialogRecyclerProvince.setAdapter(adapterProvince);
        adapterProvince.setEmptyView(R.layout.view_empty, dialogRecyclerProvince);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialogRecyclerProvince.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1, null)));
        } else {
            dialogRecyclerProvince.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1)));
        }

        adapterCity = new adapterCity();
        LinearLayoutManager cityManager = new LinearLayoutManager(getActivity());
        dialogRecyclerCity.setLayoutManager(cityManager);
        dialogRecyclerCity.setAdapter(adapterCity);
        adapterCity.setEmptyView(R.layout.view_empty, dialogRecyclerCity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialogRecyclerCity.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1, null)));
        } else {
            dialogRecyclerCity.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1)));
        }

        adapterBranchBank = new adapterBranchBank();
        LinearLayoutManager branchBankManager = new LinearLayoutManager(getActivity());
        dialogRecyclerBankBranch.setLayoutManager(branchBankManager);
        dialogRecyclerBankBranch.setAdapter(adapterBranchBank);
        adapterBranchBank.setEmptyView(R.layout.view_empty, dialogRecyclerBankBranch);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialogRecyclerBankBranch.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1, null)));
        } else {
            dialogRecyclerBankBranch.addItemDecoration(new SpacesItemDecoration(0,
                    ConvertUtils.dp2px(1f), getResources().getColor(R.color.linaColor1)));
        }
    }

    @Override
    public void iniWidow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(lp.MATCH_PARENT, lp.MATCH_PARENT);
            window.setWindowAnimations(R.style.bottom_in_out_animation);
        }
    }

    @Override
    protected void initFunc() {
        mPresenter.getBankList();
        adapterBank.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BankBean bank = (BankBean) adapter.getData().get(position);
                adapterBank.setSelectPosition(position);
                bankId = bank.getBankId();
                dialogBankName.setText(bank.getBankName());
                mPresenter.getProvince();
                dialogSelectBankBranch.setText("");
                adapterBranchBank.refreshPosition();

            }
        });

        adapterProvince.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ProvinceBean province = (ProvinceBean) adapter.getData().get(position);
                adapterProvince.setSelectPosition(position);
                provinceCode = province.getCode();
               String provinceName = province.getName();
                dialogSelectProvince.setText(provinceName);
                mPresenter.getCity(provinceCode);
                dialogSelectBankBranch.setText("");
                dialogSelectCity.setText("");
                adapterBranchBank.refreshPosition();

            }
        });

        adapterCity.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CityBean city = (CityBean) adapter.getData().get(position);
                adapterCity.setSelectPosition(position);
                cityCode = city.getCode();
               String cityName = city.getName();
                dialogSelectCity.setText(cityName);
                mPresenter.getBranchBank(bankId, cityCode);
                dialogSelectBankBranch.setText("");

            }
        });
        adapterBranchBank.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (StringUtils.isSpace(dialogSelectCity.getText().toString().trim())){
                    ToastUtils.showLong("请先选择城市(区)");
                    return;
                }
                SubBranchBank branchBank = (SubBranchBank) adapter.getData().get(position);
                adapterBranchBank.setSelectPosition(position);
                subBranchId = branchBank.getSubBranchId();
                String subBranchName = branchBank.getSubBranchName();
                dialogSelectBankBranch.setText(subBranchName);
            }
        });
    }

    @OnClick({R.id.dialog_complete, R.id.dialog_dismiss})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_complete:
                if (StringUtils.isSpace(dialogBankName.getText().toString().trim())) {
                    ToastUtils.showLong("请选择开户总行");
                    return;
                }
                if (StringUtils.isSpace(dialogSelectBankBranch.getText().toString().trim())) {
                    ToastUtils.showLong("请选择开户支行");
                    return;
                }
                if (StringUtils.isSpace(dialogSelectProvince.getText().toString().trim())) {
                    ToastUtils.showLong("请选择所在省份");
                    return;
                }
                if (StringUtils.isSpace(dialogSelectCity.getText().toString().trim())) {
                    ToastUtils.showLong("请选择所在城市");
                    return;
                }
                if (selectBankListener_s != null) {
                    selectBankListener_s.selectSuccess(dialogBankName.getText().toString().trim(),bankId,
                            dialogSelectBankBranch.getText().toString().trim(),subBranchId,
                            dialogSelectProvince.getText().toString().trim(),provinceCode,
                            dialogSelectCity.getText().toString().trim(),cityCode);
                }
                dismiss();
                break;
            case R.id.dialog_dismiss:
                dismiss();
                break;

        }
    }

    @Override
    public RecyclerView getBankRecycler() {
        return dialogRecyclerBank;
    }

    @Override
    public RecyclerView getProvinceRecycler() {
        return dialogRecyclerProvince;
    }

    @Override
    public RecyclerView getCityRecycler() {
        return dialogRecyclerCity;
    }

    @Override
    public RecyclerView getBranchBankRecycler() {
        return dialogRecyclerBankBranch;
    }

    @Override
    public com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterBank getBankAdapter() {
        return adapterBank;
    }

    @Override
    public com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterProvince getProvinceAdapter() {
        return adapterProvince;
    }

    @Override
    public com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterCity getCityAdapter() {
        return adapterCity;
    }

    @Override
    public com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter.adapterBranchBank getBranchBankAdapter() {
        return adapterBranchBank;
    }

    public void setDialogSelectBankListener(selectBankListener selectBankListener) {
        this.selectBankListener_s = selectBankListener;
    }
}
