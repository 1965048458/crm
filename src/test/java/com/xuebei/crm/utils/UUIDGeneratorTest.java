package com.xuebei.crm.utils;

import org.junit.Test;

/**
 * Created by Rong Weicheng on 2018/7/12.
 */
public class UUIDGeneratorTest {
    @Test
    public void genUUID() throws Exception {
        String uuid = UUIDGenerator.genUUID();
        System.out.println(uuid);
    }

}