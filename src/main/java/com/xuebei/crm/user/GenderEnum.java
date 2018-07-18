package com.xuebei.crm.user;

public enum GenderEnum {
    MALE("男性"), FEMALE("女性");

    private String name;

    GenderEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}