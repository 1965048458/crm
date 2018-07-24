package com.xuebei.crm.project;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/24.
 */
public class Project {
    @Expose
    private String projectId;
    @Expose
    private String projectNo;
    @Expose
    private String projectNm;
    @Expose
    private String backGround;
    @Expose
    private String status;
    @Expose
    private String projectContent;
    @Expose
    private Date deadLine;
    @Expose
    private Integer priority;
    @Expose
    private String agent;
    @Expose
    private String opportunityId;
    @Expose
    private String customerId;
    @Expose
    private String userId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectNm() {
        return projectNm;
    }

    public void setProjectNm(String projectNm) {
        this.projectNm = projectNm;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }
}
