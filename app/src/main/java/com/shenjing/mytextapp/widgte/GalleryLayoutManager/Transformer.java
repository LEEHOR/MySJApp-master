package com.shenjing.mytextapp.widgte.GalleryLayoutManager;

import android.view.View;

/**
 * author : Leehor
 * date   : 2019/8/1315:38
 * version: 1.0
 * desc   : item滑动缩放
 */
public class Transformer implements GalleryLayoutManager.ItemTransformer {
    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        //以圆心进行缩放
        item.setPivotX(item.getWidth() / 2.0f);
        item.setPivotY(item.getHeight() / 2.0f);
        // 0.18f是放大的View面积和缩小的View面积的比值 值越大差面积相差越大
        float scale = 1 - 0.18f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
    }
}
