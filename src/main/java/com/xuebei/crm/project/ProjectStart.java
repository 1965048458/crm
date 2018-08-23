package com.xuebei.crm.project;


import com.google.gson.annotations.Expose;

import java.util.List;

public class ProjectStart {
    @Expose
    private Integer projectId;
    @Expose
    private String projectName;
    @Expose
    private String applyContent;
    @Expose
    private Contract contract;
    @Expose
    private List<Refund> refunds;
    @Expose
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }
}
