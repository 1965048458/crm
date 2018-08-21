package com.xuebei.crm.message;

/**
 * Created by Administrator on 2018/8/21.
 */
public enum QueryTypeEnum {
    ENCLOSURE_APPLY("圈地申请"),ENCLOSURE_DELAY_APPLY("延期申请"),PROJECT_APPLY("启动申请");

    private String type;

    QueryTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {

        return type;
    }
}
