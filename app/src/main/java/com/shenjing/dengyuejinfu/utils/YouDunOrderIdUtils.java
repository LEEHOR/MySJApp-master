package com.shenjing.dengyuejinfu.utils;

import com.shenjing.dengyuejinfu.common.BaseParams;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author : Leehor
 * date   : 2019/9/1617:34
 * version: 1.0
 * desc   :生成友盾OrderId
 */
public class YouDunOrderIdUtils {
    public static String getYDOrderId() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        return "A" + BaseParams.userId + sdf.format(date);
    }
}
