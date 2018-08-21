package com.xuebei.crm.opportunity;

public enum SupportOrderEnum {
    NORMAL("普通"), URGENT("紧急"), GREATEURGENT("十分紧急");

    private String name;

    SupportOrderEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
