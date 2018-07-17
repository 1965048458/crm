package com.xuebei.crm.customer;

public enum EnclosureStatusEnum {
    NONE("不参与圈地"), NORMAL("未圈"), ENCLOSURE("已圈");

    private String name;

    EnclosureStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
