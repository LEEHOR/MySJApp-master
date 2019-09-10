package com.shenjing.mytextapp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.blankj.utilcode.util.LogUtils;
import com.shenjing.mytextapp.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : Leehor
 * date   : 2019/8/2213:44
 * version: 1.0
 * desc   :
 */
public class PhoneUtils {

    public static String[] getPhoneContactsData(Uri uri, Context context) {
        String[] contact = new String[2];
        ContentResolver cr = App.getAppContext().getContentResolver();
        LogUtils.d("地址", uri);
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex1 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1);
                int columnIndex4 = cursor.getColumnIndex(ContactsContract.Contacts.Data.DATA4);
                int display_name = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(display_name);
                String phone1 = cursor.getString(columnIndex1);
                String phone2 = cursor.getString(columnIndex4);
                if (phone1 != null) {
                    String phone_1 = phone1.replaceAll("\\s*", "");
                    contact[0] = phone_1;
                } else {
                    if (phone2 != null) {
                        if (phone2.startsWith("+86")) {
                            String phone_2 = phone2.substring(phone2.lastIndexOf("6") + 1);
                            contact[0] = phone_2;
                        } else {
                            contact[0] = phone2;
                        }

                    } else {
                        contact[0] = "未知";
                    }

                }
                if (name != null) {
                    contact[1] = name;
                } else {
                    contact[1] = "未知";
                }

            }
            cursor.close();

        }
        return contact;
    }

    /**
     * 获取通讯录的内容
     *
     * @param uri
     * @return
     */
    public static String[] getPhoneContacts(Uri uri, Context context) {
        String[] contact = new String[2];
        try {
            //得到ContentResolver对象
            ContentResolver cr = context.getContentResolver();
            //取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                //取得联系人姓名
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                contact[0] = cursor.getString(nameFieldColumnIndex).replace(" ", "");
                contact[0] = filterEmoji(contact[0]).replace(" ", "");
                if (contact[0].length() == 0) {
                    contact[0] = "未知";
                }
                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                // 查看联系人有多少个号码，如果没有号码，返回0
                int phoneCount = cursor
                        .getInt(cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                Cursor phoneCursor;
                if (phoneCount > 0) {
                    // 获得联系人的电话号码列表
                    phoneCursor = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + ContactId, null, null);
                    if (phoneCursor.moveToFirst()) {
                        StringBuffer str = new StringBuffer();
                        do {
                            //遍历所有的联系人下面所有的电话号码
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ", "");
                            str.append(phoneNumber + ",");
                            //使用Toast技术显示获得的号码
                            //Toast.makeText(context, "联系人电话：" + phoneNumber, Toast.LENGTH_LONG).show();
                        }
                        while (phoneCursor.moveToNext());
                        if (str.toString().length() > 0) {
                            contact[1] = str.toString().substring(0, str.toString().length() - 1);
                        }
                    }
                    phoneCursor.close();
                }
                cursor.close();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return contact;
    }


    public static class Contact {
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
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
