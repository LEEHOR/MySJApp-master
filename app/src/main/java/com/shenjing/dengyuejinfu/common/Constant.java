package com.shenjing.dengyuejinfu.common;

import com.shenjing.dengyuejinfu.utils.StoreSpaceUtils;

/**
 * author : Leehor
 * date   : 2019/8/209:59
 * version: 1.0
 * desc   :常量类
 */
public class Constant {

    //SP字段
    public static final boolean isFirstIn=false; //是否首次进入


    //SDCard路径
    public static String SDCARD_PATH = StoreSpaceUtils.getSDCardPath();

    /**
     * 本地存储总目录
     */
    public static String SAVE_DIR_BASE = SDCARD_PATH.concat("/com.shenjing.dengyuejinfu/");
    /**
     *Glide缓存
     */
    public static String SAVE_DIR_GLIDE_CACHE=SAVE_DIR_BASE.concat("GlideCache/");
    /**
     * 相机拍摄图片存储位置
     */
    public static String SAVE_DIR_TAKE_PHOTO = SAVE_DIR_BASE.concat("takePhoto/");
}
