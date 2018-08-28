package com.xuebei.crm.message;


import com.google.gson.annotations.Expose;

public class ProjectApply {
    @Expose
    private String applyId;
    @Expose
    private String customerName;
    @Expose
    private String deptName;
    @Expose
    private String projectId;
    @Expose
    private String projectName;
    @Expose
    private String applyerId;
    @Expose
    private String applyerName;
    @Expose
    private String content;
    @Expose
    private String applyTime;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(String applyerId) {
        this.applyerId = applyerId;
    }

    public String getApplyerName() {
        return applyerName;
    }

    public void setApplyerName(String applyerName) {
        this.applyerName = applyerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}
