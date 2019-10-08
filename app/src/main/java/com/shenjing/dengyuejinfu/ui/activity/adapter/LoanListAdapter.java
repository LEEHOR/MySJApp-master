package com.shenjing.dengyuejinfu.ui.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.BankListBean;
import com.shenjing.dengyuejinfu.entity.LoanListBean;
import com.shenjing.dengyuejinfu.utils.GlideUtils;

/**
 * author : Leehor
 * date   : 2019/9/3010:57
 * version: 1.0
 * desc   :
 */
public class LoanListAdapter extends BaseQuickAdapter<LoanListBean.DataBean.LoadListBean, BaseViewHolder> {
    private Context mContect;

    public LoanListAdapter(Context context) {
        super(R.layout.item_loan_list);
        this.mContect = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LoanListBean.DataBean.LoadListBean item) {
        if (item != null) {
            TextView textView_bankName = helper.getView(R.id.loanList_name);
            if (!StringUtils.isSpace(item.getLogo())){
                Drawable drawable = GlideUtils.getDrawable(mContext, item.getLogo());
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView_bankName.setCompoundDrawables(drawable, null, null, null);
            }
            textView_bankName.setText(item.getName());
            TextView tv_table = helper.getView(R.id.loanList_table);
            if (!StringUtils.isSpace(item.getLimitations())) {
                tv_table.setText(item.getLimitations());
            } else {
                tv_table.setText("undefined");
            }
            helper.setText(R.id.loanList_des,item.getIntroduction())
                    .addOnClickListener(R.id.loanList_submit);
        }


    }
}
