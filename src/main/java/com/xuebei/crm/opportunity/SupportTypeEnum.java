package com.xuebei.crm.opportunity;

public enum SupportTypeEnum {
    A("方案"), B("资源示例"), C("试用"), D("人员外出支持"), E("项目评估"), F("为代理商陪标"), G("代理商授权"), H("撰写招标参数"), I("其他");

    private String name;

    SupportTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
