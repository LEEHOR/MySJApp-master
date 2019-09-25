package com.shenjing.dengyuejinfu.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.AppUtils;
import com.shenjing.dengyuejinfu.R;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Leehor
 * on 2019/6/28
 * on 17:04
 * 高德地图定位
 */
public class GaodeMapLocationHelper {
    private static AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public static AMapLocationClientOption mLocationOption = null;

    public static AMapLocation sLocation = null;
    public static boolean isGpsStatus = false;
    private static Set<MyLocationListener> locationCallBacks = new HashSet<>();

    public static void init(Context context) {
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 返回的定位结果包含地址信息
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(false);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                if (location != null) {
                    //定位成功,取消定位
                    sLocation = location;
                    for (MyLocationListener callBack : locationCallBacks) {
                        callBack.onLocationSuccess(location);
                    }
                } else {
                    //获取定位数据失败
                    for (MyLocationListener callBack : locationCallBacks) {
                        callBack.onLocationFailure(location.getErrorCode());
                    }
                }
            }
        });
    }

    public interface MyLocationListener {
        void onLocationSuccess(AMapLocation location);

        void onLocationFailure(int locType);//返回定位错误码
    }

    public static void registerLocationCallback(MyLocationListener onLocationCallBack) {

        locationCallBacks.add(onLocationCallBack);
    }

    public static void unRegisterLocationCallback(MyLocationListener onLocationCallBack) {
        locationCallBacks.remove(onLocationCallBack);
    }

    // 获取之前定位位置,如果之前未曾定位,则重新定位
    public static void getLocation() {
        if (sLocation == null) {
            getCurrentLocation();
        } else {
            for (MyLocationListener callBack : locationCallBacks) {
                callBack.onLocationSuccess(sLocation);
            }
        }
    }

    // 获取当前位置,无论是否定位过,重新进行定位
    public static void getCurrentLocation() {
        if (mlocationClient == null) {
            return;
        }
        // 设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                if (location != null) {
                    //定位成功,取消定位
                    sLocation = location;
                    for (MyLocationListener callBack : locationCallBacks) {
                        callBack.onLocationSuccess(location);
                    }
                } else {
                    //获取定位数据失败
                    for (MyLocationListener callBack : locationCallBacks) {
                        callBack.onLocationFailure(location.getErrorCode());
                    }
                }
            }
        });

    }


    // 启动定位
    public static void startLocation() {
        if (mlocationClient.isStarted()) {
            mlocationClient.stopLocation();
            mlocationClient.startLocation();
        } else {
            mlocationClient.startLocation();
        }
    }

    public static void destroy() {
        mlocationClient.onDestroy();
    }

    public static void closeLocation() {
        if (mlocationClient.isStarted()) {
            mlocationClient.stopLocation();
        }

    }

    /**
     * 前台定位
     */
    public static void ForegroundLocation() {
        //切入前台后关闭后台定位功能
        if (null != mlocationClient) {
            mlocationClient.disableBackgroundLocation(true);
        }
    }

    public static void BackgroundLocation(Context context) {
        if (null != mlocationClient) {
            mlocationClient.enableBackgroundLocation(2001, buildNotification(context));
        }
    }


    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";
    private static NotificationManager notificationManager = null;
    static boolean isCreateChannel = false;

    @SuppressLint("NewApi")
    private static Notification buildNotification(Context context) {

        Notification.Builder builder = null;
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = AppUtils.getAppName();
            if (!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(context.getApplicationContext(), channelId);
        } else {
            builder = new Notification.Builder(context.getApplicationContext());
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getApplicationInfo().className)
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.build();
        }
        return notification;
    }


}
