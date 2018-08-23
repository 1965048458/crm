package com.xuebei.crm.customer;

public enum EnclosureStatusEnum {
    NORMAL("未圈"), ENCLOSURE("别人正在申请"), MINE("我的"),APPLYING("待审核");

    private String name;

    EnclosureStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getOrderValue(){
        switch (getName()){
            case "我的":
                return 0;
            case "待审核":
                return 1;
            case "别人正在申请":
                return 2;
            case "未圈":
                return 3;
            default:
                break;
        }
        return 0;
    }
}
