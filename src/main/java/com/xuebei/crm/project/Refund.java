package com.xuebei.crm.project;


import com.google.gson.annotations.Expose;

public class Refund {
    @Expose
    private Integer refundId;
    @Expose
    private Integer projectId;
    @Expose
    private Double refundNum;
    @Expose
    private String condition;
    @Expose
    private String time;

    @Expose
    private Integer isRefunded;

    public Integer getIsRefunded() {
        return isRefunded;
    }

    public void setIsRefunded(Integer isRefunded) {
        this.isRefunded = isRefunded;
    }

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Double getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(Double refundNum) {
        this.refundNum = refundNum;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
