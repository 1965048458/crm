package com.xuebei.crm.member;

/**
 * Created by Administrator on 2018/8/7.
 */
public enum MemberTypeEnum {
    NORMAL("普通员工"),ADMIN("管理员");
    private String type;

    MemberTypeEnum(String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {

        return type;
    }
}
