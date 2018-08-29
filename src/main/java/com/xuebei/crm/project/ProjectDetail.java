package com.xuebei.crm.project;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.FollowUpRecord;
import com.xuebei.crm.opportunity.Support;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDetail {

    @Expose
    private String projectId;
    @Expose
    private String projectName;
    @Expose
    private ProjectContacts projectContacts;
    @Expose
    private Double amount;
    @Expose
    private String content;
    @Expose
    private String status;
    @Expose
    private String applyStatus;
    @Expose
    private Date clinchDate;
    @Expose
    private String userId;
    @Expose
    private String creatorId;
    @Expose
    private String agent;
    @Expose
    private String creatorName;
    @Expose
    private Date createTs;
    @Expose
    private Integer progress;
    @Expose
    private String leaderId;
    @Expose
    private String leaderName;
    @Expose
    private String leaderPhone;
    @Expose
    private String isAdmin;
    @Expose
    private List<FollowUpRecord> followUpRecords;
    @Expose
    private List<Support> projectSupports;
    @Expose Support support;

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public ProjectContacts getProjectContacts() {
        return projectContacts;
    }

    public void setProjectContacts(ProjectContacts projectContacts) {
        this.projectContacts = projectContacts;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getClinchDate() {
        return clinchDate;
    }

    public void setClinchDate(Date clinchDate) {
        this.clinchDate = clinchDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderPhone() {
        return leaderPhone;
    }

    public void setLeaderPhone(String leaderPhone) {
        this.leaderPhone = leaderPhone;
    }

    public List<FollowUpRecord> getFollowUpRecords() {
        return followUpRecords;
    }

    public void setFollowUpRecords(List<FollowUpRecord> followUpRecords) {
        this.followUpRecords = followUpRecords;
    }

    public List<Support> getProjectSupports() {
        return projectSupports;
    }

    public void setProjectSupports(List<Support> projectSupports) {
        this.projectSupports = projectSupports;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String showClinch() {
        if (clinchDate != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(clinchDate);
        } else {
            return "-";
        }
    }

    public String showAmount() {
        if (amount != null) {
            DecimalFormat df = new DecimalFormat("#,###.00");
            return df.format(amount);
        } else {
            return "-";
        }
    }

    public String showCustomerName() {
        if (projectContacts != null && projectContacts.getCustomerName() != null) {
            return projectContacts.getCustomerName();
        } else {
            return "-";
        }
    }

    public String showTopDeptName() {
        if (projectContacts != null) {
            return projectContacts.showTopDeptName();
        } else {
            return "-";
        }
    }

    public String showContactsName() {
        if (projectContacts != null) {
            return projectContacts.getContactsName();
        } else {
            return "-";
        }
    }

    private String getContactsType() {
        if (projectContacts != null && projectContacts.getType() != null) {
            return projectContacts.getType();
        } else {
            return null;
        }
    }

    public String showContacts() {
        if (getContactsType() != null) {
            return getContactsType() + '-' + showContactsName();
        } else {
            return showContactsName();
        }
    }

    public String showCreatorName() {
        if (creatorName != null) {
            return creatorName;
        } else {
            return "-";
        }
    }

    public String showCreateTs() {
        if (createTs != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return format.format(createTs);
        } else {
            return "-";
        }
    }

    public String showStatus(){
        Map<String, String> map = new HashMap<>();
        map.put("0", "未开始");
        map.put("1", "进行中");
        map.put("2", "交付及回款");
        map.put("3", "已结束");
        if(getStatus().equals("1")){
            //
        }
        return map.get(getStatus());
    }
}
