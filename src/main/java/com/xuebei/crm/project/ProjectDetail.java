package com.xuebei.crm.project;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.FollowUpRecord;
import com.xuebei.crm.opportunity.Support;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private Date clinchDate;
    @Expose
    private String creatorName;
    @Expose
    private Date createTs;
    @Expose
    private String leaderName;
    @Expose
    private String leaderPhone;
    @Expose
    private List<FollowUpRecord> followUpRecords;
    @Expose
    private List<Support> projectSupports;

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
}
