package com.backstage.util;

import java.util.UUID;

/**
 * 工具类
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public class Utils {

    /**
     * 自动生成UUID
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }
}
