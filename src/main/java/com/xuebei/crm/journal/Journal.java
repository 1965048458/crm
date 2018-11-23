package com.xuebei.crm.journal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.CustomJsonDateDeserializer;
import com.xuebei.crm.utils.DateJsonSerializer;

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
    @JsonDeserialize(using= CustomJsonDateDeserializer.class)
    @JsonSerialize(using= DateJsonSerializer.class)
    private Date createTs;
    @Expose
    private Date modifyTs;

    public Date getRepairTs() {
        return repairTs;
    }

    public void setRepairTs(Date repairTs) {
        this.repairTs = repairTs;
    }

    @Expose
    private Date repairTs;
    @Expose
    private Boolean hasSubmitted;
    @Expose
    private List<VisitRecord> visitRecords;

	@Expose
    private User user;

    @Expose
    private Integer readNum;

    @Expose
    private List<JournalPatch> journalPatches;

    @Expose
    private Boolean isMine;
    
    @Expose
    private Boolean isToday=false;

    public Boolean getIsToday() {
		return isToday;
	}

	public void setIsToday(Boolean isToday) {
		this.isToday = isToday;
	}

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

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public List<JournalPatch> getJournalPatches() {
        return journalPatches;
    }

    public void setJournalPatches(List<JournalPatch> journalPatches) {
        this.journalPatches = journalPatches;
    }

    public Boolean getIsMine() {
        return isMine;
    }

    public void setIsMine(Boolean isMine) {
        this.isMine = isMine;
    }
}
