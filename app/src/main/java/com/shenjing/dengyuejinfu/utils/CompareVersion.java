package com.shenjing.dengyuejinfu.utils;

import com.blankj.utilcode.util.StringUtils;

/**
 * author : Leehor
 * date   : 2019/9/2316:37
 * version: 1.0
 * desc   :app版本比较
 */
public class CompareVersion {

    /**
     * 比较版本号
     * @param loc  本地版本
     * @param server 服务器版本
     * @return 1 ：新版本  0：版本相同 -1 旧版本
     */
    public static int compareVersionNumber(String loc, String server) {

        if (StringUtils.isEmpty(loc)
                || StringUtils.isEmpty(server)) {
            return -1;
        }
        if (loc.trim().equals(server.trim())) {
            return 0;
        }

        loc = loc.trim();
        server = server.trim();
        loc = loc.replace(".", ",");
        server = server.replace(".", ",");
        String[] locArr = loc.split(",");
        String[] serArr = server.split(",");

        int itemInt1, itemInt2;
        String itemStr1, itemStr2;
        int len = locArr.length > serArr.length ? serArr.length : locArr.length;

        for (int i = 0; i < len; i++) {

            itemInt1 = itemInt2 = 0;
            itemStr1 = locArr[i].trim();
            itemStr2 = serArr[i].trim();

            if (!StringUtils.isEmpty(itemStr1)) {
                itemInt1 = Integer.parseInt(itemStr1);
            }
            if (!StringUtils.isEmpty(itemStr2)) {
                itemInt2 = Integer.parseInt(itemStr2);
            }
            if (itemInt1 < itemInt2) {
                return 1;
            } else if (itemInt1 > itemInt2) {
                return -1;
            }
        }
        //防止出现类似: 1.1 Vs 1.1.1
        if (locArr.length < serArr.length) {
            len = serArr.length;
            String itemStr;
            int itemInt;
            for (int i = locArr.length; i < len; i++) {
                itemInt = 0;
                itemStr = serArr[i].trim();
                if (!StringUtils.isEmpty(itemStr)) {
                    itemInt = Integer.parseInt(itemStr);
                }
                if (itemInt > 0) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
