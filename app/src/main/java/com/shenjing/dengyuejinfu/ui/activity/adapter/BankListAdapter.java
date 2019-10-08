package com.shenjing.dengyuejinfu.ui.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.App;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.BankListBean;
import com.shenjing.dengyuejinfu.utils.GlideUtils;

/**
 * author : Leehor
 * date   : 2019/9/3010:57
 * version: 1.0
 * desc   :
 */
public class BankListAdapter extends BaseQuickAdapter<BankListBean.DataBean.BankListBeans, BaseViewHolder> {
    private Context mContect;

    public BankListAdapter(Context context) {
        super(R.layout.item_bank_list);
        this.mContect = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BankListBean.DataBean.BankListBeans item) {
        if (item != null) {
            TextView textView_bankName = helper.getView(R.id.bankList_name);
            if (!StringUtils.isSpace(item.getLogo())){
                Drawable drawable = GlideUtils.getDrawable(mContext, item.getLogo());
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView_bankName.setCompoundDrawables(drawable, null, null, null);
            }
            textView_bankName.setText(item.getBank());
            View view = helper.getView(R.id.bankList_table);
            if (!StringUtils.isSpace(item.getTable1()) || !StringUtils.isSpace(item.getTable2())) {
                view.setVisibility(View.VISIBLE);
                TextView tv1 = helper.getView(R.id.bankList_table1);
                TextView tv2 = helper.getView(R.id.bankList_table2);
                if (!StringUtils.isSpace(item.getTable1())) {
                    tv1.setVisibility(View.VISIBLE);
                    tv1.setText(item.getTable1());
                } else {
                    tv1.setVisibility(View.INVISIBLE);
                }

                if (!StringUtils.isSpace(item.getTable2())) {
                    tv2.setVisibility(View.VISIBLE);
                    tv2.setText(item.getTable1());
                } else {
                    tv2.setVisibility(View.INVISIBLE);
                }
            } else {
                view.setVisibility(View.GONE);
            }
            helper.setText(R.id.bankList_des,item.getIntroduction())
                    .addOnClickListener(R.id.bankList_root);
        }


    }
}
