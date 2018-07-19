package com.xuebei.crm.journal;

public enum VisitTypeEnum {
    VISIT("拜访"), OFFLINE("拜访"), PHONE("电话");

    private String name;

    VisitTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
