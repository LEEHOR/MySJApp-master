package com.shenjing.dengyuejinfu.common;

/**
 * author : Leehor
 * date   : 2019/8/209:57
 * version: 1.0
 * desc   : 参数
 */
public class BaseParams {

    public static final  String ISFIRSTIN_KEY="isFirst_key"; //是否第一次登录
    public static final  String USER_NAME_KEY="user_name";  //用户名
    public static final  String USER_TOKEN_KEY="user_token"; //用户token
    public static final  String USER_ID_KEY="user_id";   //用户id
    public static final String COOKIE_KEY="cookie"; //cookie


    //特殊的路由跳转
    public static final String RouterPath="path";  //跳转路径
    public static final String Router_type="router_type";
    public static final int Router_code_mainActivity=1001;  //mainActivity跳转登录resultCode
    public static final String Router_type_mainActivity="main_type";  //mainActivity跳转登录resultCode
    public static final String Router_position_mainActivity="main_position";  //mainActivity所在的fragment
    public static final int MainActivity_Type=1001;
    public static final int ChangePass_Type=1002;
    public static final int  SettingActivity_Type=1003;
    public static final String IncreaseQuotaType="QuotaType";
    //权限请求



    //user常量
    public static String userName;
    public static  String userId;
    public static  String userToken="";

    //webView跳转常量
    public static final String webViewTitle="webTitle";
    public static  final  String webViewUrl="webUrl";

    //分享跳转
    public static final String share_title="shareTitle";  //分享标题
    public static final String share_url="shareUrl"; //分享的链接
    public static final String share_describe="shareDescribe"; //分享的描述
    public static final String share_thumbnail="shareThumbnail"; //分享的缩略图

    //定位
    public static String  location_address;//定位地址
    public static double  location_longitude;//经度
    public static double location_latitude;//维度

}
