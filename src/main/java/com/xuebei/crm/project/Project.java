package com.xuebei.crm.project;

import com.google.gson.annotations.Expose;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/24.
 */
public class Project {
    @Expose
    private Integer projectId;
    @Expose
    private String customerId;
    @Expose
    private String projectName;
    @Expose
    private String customerName;
    @Expose
    private Date deadLine;
    @Expose
    private String strDeadLine;
    @Expose
    private String leaderId;
    @Expose
    private String leaderName;
    @Expose
    private String status;
    @Expose
    private String content;
    @Expose
    private String agent;
    @Expose
    private Double amount;
    @Expose
    private String contactId;
    @Expose
    private String contactName;
    @Expose
    private String userId;
    @Expose
    private String createTime;
    @Expose
    private String updaterId;
    @Expose
    private String updateTime;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStrDeadLine(Date deadLine) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        this.strDeadLine = ft.format(deadLine);
    }

    public String getStrDeadLine() {
        return strDeadLine;
    }

    public Date getDeadLine() {
        return deadLine;
    }
    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int hashCode() {
        if (projectId != null) {
            return projectId.hashCode();
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Project) {
            return projectId .equals(((Project)obj).projectId);
        } else {
            return false;
        }
    }
}
