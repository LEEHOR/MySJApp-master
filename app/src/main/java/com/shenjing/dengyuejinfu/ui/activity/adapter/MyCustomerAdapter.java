package com.shenjing.dengyuejinfu.ui.activity.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.App;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.CustomerBean;
import com.shenjing.dengyuejinfu.utils.GlideUtils;

/**
 * author : Leehor
 * date   : 2019/11/710:54
 * version: 1.0
 * desc   :
 */
public class MyCustomerAdapter extends BaseQuickAdapter<CustomerBean, BaseViewHolder> {

    public MyCustomerAdapter() {
        super(R.layout.item_mycustomer);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CustomerBean item) {
        if (item != null) {
            int direct=item.getDirect()!=null?item.getDirect().size():0;
            int inDirect= item.getInDirect()!=null?item.getInDirect().size():0;
            helper.setText(R.id.customer_title,item.getLevelName())
                    .setText(R.id.customer_count,"直接用户"+direct+"\t间接用户"+inDirect)
                    .addOnClickListener(R.id.customer_root);
                    GlideUtils.initCircleImage(App.getAppContext(),item.getLogo(),helper.getView(R.id.customer_logo));
        }
    }
}
