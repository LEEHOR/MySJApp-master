package com.shenjing.dengyuejinfu.ui.broadcastReceiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shenjing.dengyuejinfu.common.Constant;

/**
 * 更新的广播接收器
 */
public class DownloadReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            String apkUrl = SPUtils.getInstance().getString(Constant.APK_DOWNLOAD_URL, "");
            if (!StringUtils.isSpace(apkUrl)) {
                // KLog.d("版本","安装了/"+ url);
                LogUtils.d("安装目录"+apkUrl);
                AppUtils.installApp(apkUrl);
              // InstallApk.openAPKFile(context,apkUrl);
            }
        }
    }
}
