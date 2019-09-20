package com.shenjing.dengyuejinfu.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shenjing.dengyuejinfu.R;


/**
 * Created by Leehor
 * on 2018/10/29
 * on 15:47
 */
public class RecyclerSimplePadding_color extends RecyclerView.ItemDecoration {
    private  float dimension;
    private Paint paint;
    public RecyclerSimplePadding_color(Context context) {
         dimension = context.getResources().getDimensionPixelOffset(R.dimen.dp_1);
         paint =new Paint();
         paint.setColor(context.getResources().getColor(R.color.material_grey_400,null));
    }

    /**
     * 底部分割线不带颜色
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = (int) dimension;//类似加了一个bottom padding
    }


    /**
     * 加上分割线颜色就重写此方法
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount ; i++) {
            View childAt = parent.getChildAt(i);
           int top= childAt.getBottom();
           int bottom= (int) (childAt.getBottom()+dimension);
           c.drawRect(left,top,right,bottom,paint);
        }
    }
}
