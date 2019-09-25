package com.shenjing.dengyuejinfu.ui.activity.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.IncreaseQuotaInformationBean;

/**
 * author : Leehor
 * date   : 2019/9/2414:48
 * version: 1.0
 * desc   :提额秘笈首页adapter
 */
public class IncreaseQuotaInformationAdapter extends BaseQuickAdapter<IncreaseQuotaInformationBean.DataBean, BaseViewHolder> {
    public IncreaseQuotaInformationAdapter() {
        super(R.layout.item_increase_quota_information);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, IncreaseQuotaInformationBean.DataBean item) {
        if (item != null) {
            helper.setText(R.id.item_increase_info_title,item.getTitle())
                    .setText(R.id.item_increase_info_time,item.getCreateTime())
                    .setText(R.id.item_increase_info_count,String.valueOf(item.getPageView()))
                    .addOnClickListener(R.id.item_increase_info_root);
        }

    }
}
