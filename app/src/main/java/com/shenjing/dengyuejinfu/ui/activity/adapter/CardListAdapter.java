package com.shenjing.dengyuejinfu.ui.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.entity.BankListBean;
import com.shenjing.dengyuejinfu.entity.CardListBean;
import com.shenjing.dengyuejinfu.utils.GlideUtils;

/**
 * author : Leehor
 * date   : 2019/9/3010:57
 * version: 1.0
 * desc   :
 */
public class CardListAdapter extends BaseQuickAdapter<CardListBean.DataBean.CardList, BaseViewHolder> {
    private Context mContect;

    public CardListAdapter(Context context) {
        super(R.layout.item_card_list);
        this.mContect = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CardListBean.DataBean.CardList item) {
        if (item != null) {
            helper.setText(R.id.cardList_name, item.getCardName())
                    .setText(R.id.cardList_des, item.getIntroduction())
                    .addOnClickListener(R.id.cardList_submit);
            ImageView iv = helper.getView(R.id.cardList_iv);
            if (!StringUtils.isSpace(item.getCardImg())) {
                GlideUtils.initImageWithFileCache(mContext, item.getCardImg(), iv);
            }
        }
    }
}
