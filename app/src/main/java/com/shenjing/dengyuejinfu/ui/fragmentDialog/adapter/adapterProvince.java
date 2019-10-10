package com.shenjing.dengyuejinfu.ui.fragmentDialog.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.ProvinceBean;

/**
 * author : Leehor
 * date   : 2019/10/915:20
 * version: 1.0
 * desc   :省份
*/
public class adapterProvince extends BaseQuickAdapter<ProvinceBean, BaseViewHolder> {
    private int select_position=-1;
    public void setSelectPosition(int selectPosition){
        this.select_position=selectPosition;
        notifyDataSetChanged();
    }
    public void refreshPosition(){
        this.select_position=-1;
    }
    public adapterProvince() {
        super(R.layout.item_selectbank);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProvinceBean item) {
        if (item != null) {
            TextView tv = helper.getView(R.id.dialog_select_tv);
            if (select_position==helper.getAdapterPosition()){
                tv.setSelected(true);
            } else {
                tv.setSelected(false);
            }
            tv.setText(item.getName());
            helper.addOnClickListener(R.id.dialog_selectbank_root);
        }
    }
}
