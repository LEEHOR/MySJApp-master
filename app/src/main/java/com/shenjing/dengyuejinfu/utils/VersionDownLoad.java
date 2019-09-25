package com.shenjing.dengyuejinfu.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.common.Constant;
import java.io.File;

/**
 * author : Leehor
 * date   : 2019/9/2316:53
 * version: 1.0
 * desc   : 版本更新下载
 */
public class VersionDownLoad {
    public static void startDownLoad(Context context,String apkDownLoadUrl){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (TextUtils.isEmpty(apkDownLoadUrl)){
            return;
        }
        if (!FileUtils.createOrExistsDir(Constant.SAVE_DIR_DOWNLOAD_APK)) {
            ToastUtils.showLong("创建文件夹失败");
            return;
        }
        File file = new File(Constant.SAVE_DIR_DOWNLOAD_APK, apkDownLoadUrl.substring(apkDownLoadUrl.lastIndexOf("/") + 1));
        Uri uri= Uri.fromFile(file);
        if (FileUtils.isFileExists(file)){
            file.delete();
        }
      //apkDownLoadUrl  https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"));
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(context.getResources().getString(R.string.app_name));
        request.setDescription(context.getResources().getString(R.string.download_apk));
        request.setDestinationUri(uri);
        downloadManager.enqueue(request);
        SPUtils.getInstance().put(Constant.APK_DOWNLOAD_URL,file.getAbsolutePath(),false);
    }
}
