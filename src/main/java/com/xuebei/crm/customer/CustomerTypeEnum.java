package com.xuebei.crm.customer;

public enum CustomerTypeEnum {

    SCHOOL("学校"), COMPANY("公司");

    private String name;

    CustomerTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
