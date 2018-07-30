package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

import java.util.List;

public class VisitRecord {

    @Expose
    private String visitId;
    @Expose
    private List<String> contactsIds;
    @Expose
    private List<ContactInfo> contactsInfo;

    public List<ContactInfo> getContactsInfo() {
        return contactsInfo;
    }

    public void setContactsInfo(List<ContactInfo> contactsInfo) {
        this.contactsInfo = contactsInfo;
    }

    @Expose

    private VisitTypeEnum visitType;
    @Expose
    private String visitResult;
    @Expose
    private String journalId;

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public List<String> getContactsIds() {
        return contactsIds;
    }

    public void setContactsIds(List<String> contactsIds) {
        this.contactsIds = contactsIds;
    }

    public VisitTypeEnum getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitTypeEnum visitType) {
        this.visitType = visitType;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

}
