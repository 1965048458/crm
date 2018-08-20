package com.xuebei.crm.customer;

public enum EnclosureStatusEnum {
    NONE("不参与圈地"), NORMAL("未圈"), ENCLOSURE("别人正在申请"), MINE("我的"),APPLYING("待审核");

    private String name;

    EnclosureStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
