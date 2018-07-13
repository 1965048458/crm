package com.xuebei.crm.user;

public enum UserTypeEnum {

    NORMAL("普通用户"), ADMIN("系统管理员");

    private String name;

    UserTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
