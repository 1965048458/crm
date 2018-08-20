package com.xuebei.crm.department;

/**
 * Created by Administrator on 2018/8/20.
 */
public enum  WarningBeforeCreateEnum {
    appliedByMe("您已经申请过或已经圈走了"),
    appliedByOthers("已经被别人申请了或被别人圈走了"),
    noOneApplied("还没有任何人提出过申请，可以申请");

    private String name;

    WarningBeforeCreateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
