package com.xuebei.crm.department;

/**
 * Created by Administrator on 2018/8/20.
 */
public enum  WarningBeforeCreateEnum {
    APPLY_BY_ME("该学院/部门已创建"),
    APPLY_BY_OTHERS("该学院/部门已创建"),
    NO_ONE_APPLY("还没有任何人提出过申请，可以申请");

    private String name;

    WarningBeforeCreateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
