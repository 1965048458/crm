package com.xuebei.crm.journal;

public enum JournalTypeEnum {
    DAILY("日报", "今日总结", "明日计划"), WEEKLY("周报", "本周总结", "下周计划"), MONTHLY("月报", "本月总结", "下月计划");

    private String name;
    private String summaryName;
    private String planName;

    JournalTypeEnum(String name, String summaryName, String planName) {
        this.name = name;
        this.summaryName = summaryName;
        this.planName = planName;
    }

    public String getName() {
        return name;
    }

    public String getSummaryName() {
        return summaryName;
    }

    public String getPlanName() {
        return planName;
    }

}
