package com.xuebei.crm.customer;

public enum CustomerTypeEnum {

    SCHOOL("学校"), COMPANY("公司"),高校("高校"),
    高职("高职"), 中职("中职"), k12("k12");


    private String name;

    CustomerTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
