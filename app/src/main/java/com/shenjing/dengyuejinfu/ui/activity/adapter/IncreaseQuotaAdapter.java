package com.shenjing.dengyuejinfu.ui.activity.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.IncreaseQuotaBean;

/**
 * author : Leehor
 * date   : 2019/9/2414:48
 * version: 1.0
 * desc   :提额秘笈首页adapter
 */
public class IncreaseQuotaAdapter extends BaseQuickAdapter<IncreaseQuotaBean.DataBean, BaseViewHolder> {
    private Context mContext;

    public IncreaseQuotaAdapter(Context context) {
        super(R.layout.item_increase_quota);
        this.mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, IncreaseQuotaBean.DataBean item) {
        if (item != null) {
            helper.setText(R.id.item_increase_title, item.getClassName())
                    .setText(R.id.item_increase_describe, item.getClassRemark())
                    .addOnClickListener(R.id.item_increase_root);
        }

    }
}
