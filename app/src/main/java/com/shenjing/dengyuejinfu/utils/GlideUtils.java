package com.shenjing.dengyuejinfu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * author : Leehor
 * date   : 2019/9/1816:42
 * version: 1.0
 * desc   :Glid加载图片
 */
public class GlideUtils {

    public static void initImageWithFileCache(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }
    public static void initImageNoCache(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .skipMemoryCache(true)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }
    public static void initImageByResouceIdNoCache(Context context, int drawableId, ImageView imageView){
        GlideApp.with(context)
                .load(drawableId)
                .skipMemoryCache(true)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }
    public static void initImageByBitMap(Context context, Bitmap bitmap, ImageView imageView){
        GlideApp.with(context)
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }
    public static void initImageByFile(Context context, File file, ImageView imageView){
        GlideApp.with(context)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }
    public static void clearMemoryCache(Context context){
        GlideApp.get(context).clearMemory();
    }
    public static void clearFileCache(Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideApp.get(context).clearDiskCache();
            }
        }).start();
    }
}