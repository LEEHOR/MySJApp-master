package com.shenjing.dengyuejinfu.utils;

import android.os.Environment;

import java.io.File;

/**
 * author : Leehor
 * date   : 2019/9/1816:22
 * version: 1.0
 * desc   :存储空间管理
 */
public class StoreSpaceUtils {
    /**
     * 获取默认存储路径
     * @return
     */
    public static String getDefaultPath(){
        if(isExistSDCard()){
            return getSDCardPath();
        }else{
            return Environment.getExternalStorageDirectory().getPath();
        }
    }

    /**
     * 判断sd卡是否存在
     * @return
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
    }

    /**
     * 获取sd卡的路径
     * @return
     */
    public static String getSDCardPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }

        if(sdDir == null){
            return null;
        }
        return sdDir.toString();
    }
}
