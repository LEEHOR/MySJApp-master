package com.shenjing.dengyuejinfu.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.shenjing.dengyuejinfu.R;

import java.util.List;

/**
 * author : Leehor
 * date   : 2019/9/1717:33
 * version: 1.0
 * desc   :加载轮播图
 */
public class SetCustomBannerUtils {
    public static  void setCustomBanner(CustomBanner<String> customBanner, final List<String> imageList, final ImageView.ScaleType scaleType) {

        customBanner.setPages(new CustomBanner.ViewCreator<String>() {

            @Override
            public View createView(Context context, int i) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(scaleType);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int i, String s) {
                GlideUtils.initImageWithFileCache(context,s,(ImageView) view);
            }
        },imageList)
                //                //设置指示器为普通指示器
                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setIndicatorRes(R.drawable.indicator_shape_point_select, R.drawable.indicator_shape_point_unselect)
//                //设置指示器的方向
                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
//                //设置指示器的指示点间隔
                .setIndicatorInterval(20)
                //设置自动翻页
                .startTurning(5000);
    }
}
