package com.shenjing.mytextapp.ui.fragment;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.base.BaseFragment;
import com.shenjing.mytextapp.entity.ReceiptBean;
import com.shenjing.mytextapp.ui.contract.ReceiptFragmentContract;
import com.shenjing.mytextapp.ui.fragment.adapter.ReceiptAdapter;
import com.shenjing.mytextapp.ui.presenter.ReceiptFragmentPresenter;
import com.shenjing.mytextapp.widgte.FullScreenNumberKeyboardView;
import com.shenjing.mytextapp.widgte.GalleryLayoutManager.GalleryLayoutManager;
import com.shenjing.mytextapp.widgte.GalleryLayoutManager.RecyclerViewHolder;
import com.shenjing.mytextapp.widgte.GalleryLayoutManager.Transformer;
import com.shenjing.mytextapp.widgte.TitleBar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/8/1216:29
 * version: 1.0
 * desc   :收款
 */
public class ReceiptFragment extends BaseFragment<ReceiptFragmentPresenter> implements ReceiptFragmentContract.View, GalleryLayoutManager.OnItemSelectedListener {


    @BindView(R.id.receipt_mStatusBar)
    View mStatusBar;
    @BindView(R.id.receipt_titleBar)
    TitleBar fragmentCouponTitleBar;
    @BindView(R.id.receipt_money_count)
    TextView receiptMoneyCount;
    @BindView(R.id.receipt_number_key)
    FullScreenNumberKeyboardView receiptNumberKey;
    @BindView(R.id.receipt_recyclerView)
    RecyclerView receiptRecyclerView;
    private ReceiptAdapter receiptAdapter;

    public static ReceiptFragment newInstance() {
        ReceiptFragment fragment = new ReceiptFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_receipt;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        setStatusBar(mStatusBar, R.color.blue1);
        setPageTitle(fragmentCouponTitleBar, "收款", R.color.white);
        setTittleBarBackgraound(fragmentCouponTitleBar, R.color.blue1);
    }

    @Override
    protected void initFunc() {


        receiptNumberKey.setOnNumberClickListener(new FullScreenNumberKeyboardView.OnNumberClickListener() {
            StringBuilder builder1 = new StringBuilder();

            @Override
            public void onNumberReturn(String number) {
                //第一位不能为"."
                if (builder1.length() == 0 && number.equals(".")) {
                    return;
                }
                //只能有一个 "."
                if (builder1.length()>=0){
                    if ((builder1.length()-builder1.toString().replace(".", "").length()>=1)){
                        return;
                    }
                }
                builder1.append(number);
                BigDecimal bigDecimal = new BigDecimal(builder1.toString());
                bigDecimal.setScale(3, BigDecimal.ROUND_HALF_EVEN);
                NumberFormat nf = new DecimalFormat("###,##0.00");
                String format = nf.format(bigDecimal);
                receiptMoneyCount.setText(format);
            }

            @Override
            public void onNumberDelete() {
                if (builder1.length() >= 1) {
                    builder1.deleteCharAt(builder1.length() - 1);
                    BigDecimal bigDecimal = new BigDecimal(EmptyUtils.isNotEmpty(builder1.toString()) ? builder1.toString() : "0.00");
                    bigDecimal.setScale(3, BigDecimal.ROUND_HALF_EVEN);
                    NumberFormat nf = new DecimalFormat("###,##0.00");
                    String format = nf.format(bigDecimal);
                    receiptMoneyCount.setText(format);
                }

            }
        });
        setRecyclerView();
    }

    private void setRecyclerView() {
        GalleryLayoutManager manager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        manager.attach(receiptRecyclerView, 1000000);
        // 设置滑动缩放效果
        manager.setItemTransformer(new Transformer());
        manager.setCallbackInFling(true);

        List<ReceiptBean> beanList = new ArrayList<>();
        beanList.clear();
        ReceiptBean receiptBean1 = new ReceiptBean(1, "收银台");
        ReceiptBean receiptBean2 = new ReceiptBean(2, "快捷支付");
        ReceiptBean receiptBean3 = new ReceiptBean(3, "扫码支付");
        beanList.add(receiptBean1);
        beanList.add(receiptBean2);
        beanList.add(receiptBean3);
        receiptAdapter = new ReceiptAdapter(getActivity(), R.layout.item_receiptfragment, beanList);
        receiptRecyclerView.setAdapter(receiptAdapter);
        manager.setOnItemSelectedListener(this);
        receiptAdapter.notifyDataSetChanged();
        receiptAdapter.setmOnItemClickListener(new ReceiptAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                // 支持手动点击滑动切换
                //判断当前item是否显示完整
                Rect rect = new Rect();
                boolean globalVisibleRect = view.getLocalVisibleRect(rect);  //获取view可见坐标左上角为0点
                Log.d("点击4", rect.left + "/" + rect.right + "/" + view.getWidth());
                if (rect.right < view.getWidth()) {   //此处判断右边坐标是否大于view的宽度
                    receiptRecyclerView.smoothScrollToPosition(Integer.valueOf(data));
                    LogUtils.d("点击3", data);

                } else {
                    LogUtils.d("点击1", data);
                }


            }
        });

    }

    @Override
    public void onItemSelected(RecyclerView recyclerView, View item, int position) {
        RecyclerViewHolder viewHolderForAdapterPosition = (RecyclerViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        LogUtils.d("点击2", position);
    }
}
