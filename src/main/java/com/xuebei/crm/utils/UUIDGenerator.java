package com.xuebei.crm.utils;

import java.util.UUID;

/**
 * Created by Rong Weicheng on 2018/7/12.
 */
public class UUIDGenerator {

    private UUIDGenerator() {
        //forbidden the constructor.
    }

    public static String genUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
