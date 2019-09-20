package com.shenjing.dengyuejinfu.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.R;


/**
 * Created by Leehor
 * on 2018/10/29
 * on 15:45
 */
public class RecyclerViewItemDecoration_StickyHeader extends RecyclerView.ItemDecoration {
    private float dimension;
    private Paint paint;
    private TextPaint textPaint;
    private Resources resources;
    private Paint.FontMetrics fontMetrics;
    private int topGap;
    private DecorationCallback callback;
    private Context context;

    public RecyclerViewItemDecoration_StickyHeader(Context context, DecorationCallback decorationCallback) {
        this.context = context;
        this.callback = decorationCallback;
        resources = context.getResources();
        dimension = context.getResources().getDimensionPixelOffset(R.dimen.dp_10);
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.material_blue_700,null));

        textPaint = new TextPaint();
        fontMetrics = new Paint.FontMetrics();

        textPaint.setTypeface(Typeface.DEFAULT_BOLD); //加粗
        textPaint.setTextSize(60); //大小
        textPaint.setColor(resources.getColor(R.color.black,null));
        textPaint.getFontMetrics(fontMetrics); //字体风格
        textPaint.setTextAlign(Paint.Align.LEFT); //文字位置
        textPaint.setAntiAlias(true);  //抗锯齿
        topGap = resources.getDimensionPixelOffset(R.dimen.dp_16);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        long groupId = callback.getGroupId(childAdapterPosition);
        if (groupId < 0) return;
        if (childAdapterPosition == 0 || isFirstInGroup(childAdapterPosition)) {
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }

    }

   /* @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        float left = parent.getPaddingLeft();
        float right =parent.getWidth()- parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            long groupId = callback.getGroupId(childAdapterPosition);
            if (groupId < 0) return;
            String textLine = callback.getGroupFirstLine(childAdapterPosition).toUpperCase();
            if (childAdapterPosition == 0 || isFirstInGroup(childAdapterPosition)){
                float top = view.getTop() - topGap;
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, paint);//绘制红色矩形
                c.drawText(textLine, left, bottom, textPaint);//绘制文本
            }
        }

    }*/

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        float left = parent.getPaddingLeft();
        float right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;
        long preGroupId, groupId = -1;
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);   //得到当前绘制item
            int childAdapterPosition = parent.getChildAdapterPosition(childAt);  //得到当前绘制item的position
            preGroupId = groupId;
            groupId = callback.getGroupId(childAdapterPosition);
            if (groupId < 0 || groupId == preGroupId) continue;//当上一个和下一个粘性头部字符相同继续绘制

            String textLine = callback.getGroupFirstLine(childAdapterPosition).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;  //字符为空继续

            int bottom = childAt.getBottom();//当前item底部距离父容器顶部的距离
            int max = Math.max(topGap, childAt.getTop()); //最大值
            if (childAdapterPosition + 1 < itemCount) {  //是否为最后一个
                long nextGroupId = callback.getGroupId(childAdapterPosition + 1); //下一个
                if (nextGroupId != groupId && bottom < max) {   //动态处理
                    max = bottom;
                }
            }
            c.drawRect(left, max - topGap, right, max, paint);
            c.drawText(textLine, left, max, textPaint);
        }
    }

    /**
     * 是否为同组第一个
     *
     * @param pos
     * @return
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            long prevGroupId = callback.getGroupId(pos - 1);
            long groupId = callback.getGroupId(pos);
            return prevGroupId != groupId;
        }
    }

    public interface DecorationCallback {

        long getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
