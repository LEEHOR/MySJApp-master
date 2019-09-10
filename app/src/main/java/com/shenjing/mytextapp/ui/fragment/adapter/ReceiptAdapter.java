package com.shenjing.mytextapp.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.entity.ReceiptBean;
import com.shenjing.mytextapp.widgte.GalleryLayoutManager.RecyclerViewHolder;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/8/1315:42
 * version: 1.0
 * desc   :
 */
public class ReceiptAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Integer[] mImgs = {R.drawable.icon_front_desk_pay, R.drawable.icon_union_pay, R.drawable.icon_code_pay};
    private int layoutResIds;
    private List<ReceiptBean> datas;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public ReceiptAdapter(Context context,int layoutResId, @Nullable List<ReceiptBean> data) {
        this.layoutResIds = layoutResId;
        this.datas = data;
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutResIds, null);
        // 自定义view的宽度,控制一屏显示的个数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT);
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height=mContext.getResources().getDisplayMetrics().heightPixels;
        params.width = width / 2;   //这里每屏显示3个<将屏幕平均分为3份
      //  params.height=height;
        view.setLayoutParams(params);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (datas != null ) {
            ImageView imageView = holder.itemView.findViewById(R.id.item_receipt_type_iv);
            TextView textView = holder.itemView.findViewById(R.id.item_receipt_tv);

            textView.setText(datas.get(position % datas.size()).getTitle());
            imageView.setImageResource( datas.get(position % datas.size()).getType() == 1 ? mImgs[0]
                    : datas.get(position % datas.size()).getType() == 2 ? mImgs[1]
                    : datas.get(position % datas.size()).getType() == 3 ? mImgs[2] : mImgs[0]);
            holder.itemView.setTag(position);
            int i = position % datas.size();
            LogUtils.d("positions",i);
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        //注意这里使用getTag方法获取数据
                        mOnItemClickListener.onItemClick(view, view.getTag().toString());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
    public void setmOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //定义一个接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }
}
