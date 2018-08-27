package com.xuebei.crm.customer;

public enum EnclosureStatusEnum {
    NORMAL("未圈", 3), ENCLOSURE("别人正在申请", 2), MINE("我的", 0),APPLYING("待审核", 1);

    private String name;

    private int orderInteger;

    EnclosureStatusEnum(String name, int orderInt) {
        orderInteger = orderInt;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getOrderValue(){
        return orderInteger;
    }
}
