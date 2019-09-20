package com.shenjing.dengyuejinfu.ui.activity.adapter;
import android.content.Context;

import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.respondModule.CreditCardListModel;

/**
 * author : Leehor
 * date   : 2019/9/1918:43
 * version: 1.0
 * desc   :  信用卡列表
 */
public class CreditficationCardListAdapter extends BaseQuickAdapter<CreditCardListModel.DataBean.CreditCardBean, BaseViewHolder> {
        private Context mContext;
    public CreditficationCardListAdapter(Context context) {
        super(R.layout.item_credit_card_certification);
        this.mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CreditCardListModel.DataBean.CreditCardBean item) {
        if (item != null) {
        helper.setText(R.id.bank_name,item.getBank())
                .setText(R.id.bank_No,String.format(mContext.getResources().getString(R.string.credit_card_1), item.getIdNo() != null ? item.getIdNo().substring(item.getIdNo().length() - 4) : "","储蓄卡"));
        helper.addOnClickListener(R.id.item_root_creditCard);
        helper.setVisible(R.id.item_creditCard_status,item.getStatus()!=null && item.getStatus().equals("1"));
        }
    }
}
