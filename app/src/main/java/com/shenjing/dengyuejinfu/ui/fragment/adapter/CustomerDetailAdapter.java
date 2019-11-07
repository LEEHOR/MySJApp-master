package com.shenjing.dengyuejinfu.ui.fragment.adapter;

import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.CustomerDetail;


/**
 * author : Leehor
 * date   : 2019/11/714:34
 * version: 1.0
 * desc   :
 */
public class CustomerDetailAdapter extends BaseQuickAdapter<CustomerDetail.DataBean, BaseViewHolder> {
    public CustomerDetailAdapter() {
        super(R.layout.item_customer_detail);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CustomerDetail.DataBean item) {
        if (item != null) {
            helper.setText(R.id.customerDetailName,item.getRealName())
                    .setText(R.id.customerDetailPhone,item.getPhoneNumber())
                    .setText(R.id.customerDetailStatus,
                            item.getAutonymState().equals("1")?"已认证":
                            item.getAutonymState().equals("2")?"未认证":
                            item.getAutonymState().equals("3")?"认证失败":
                            item.getAutonymState().equals("4")?"认证成功":"未认证");
        }
    }
}
