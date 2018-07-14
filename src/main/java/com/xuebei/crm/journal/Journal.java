package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.user.User;

import java.util.Date;
import java.util.List;

public class Journal {

    @Expose
    private String journalId;
    @Expose
    private String userId;
    @Expose
    private JournalTypeEnum type;
    @Expose
    private String summary;
    @Expose
    private String plan;
    @Expose
    private Date createTs;
    @Expose
    private Date modifyTs;
    @Expose
    private Boolean hasSubmitted;

    @Expose
    private List<VisitRecord> visitRecords;

    @Expose
    private List<User> receivers;

    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JournalTypeEnum getType() {
        return type;
    }

    public void setType(JournalTypeEnum type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Date getModifyTs() {
        return modifyTs;
    }

    public void setModifyTs(Date modifyTs) {
        this.modifyTs = modifyTs;
    }

    public Boolean getHasSubmitted() {
        return hasSubmitted;
    }

    public void setHasSubmitted(Boolean hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
    }

    public List<VisitRecord> getVisitRecords() {
        return visitRecords;
    }

    public void setVisitRecords(List<VisitRecord> visitRecords) {
        this.visitRecords = visitRecords;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

}
