package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.opportunity.Opportunity;

import java.util.List;

public class VisitRecord {

    @Expose
    private String visitId;
    @Expose
    private List<Contacts> chosenContacts;
    @Expose
    private VisitTypeEnum visitType;
    @Expose
    private String visitResult;
    @Expose
    private String journalId;
    @Expose
    private Integer opportunityId;
    @Expose
    private String opportunityName;

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
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

    public List<Contacts> getChosenContacts() {
        return chosenContacts;
    }

    public void setChosenContacts(List<Contacts> chosenContacts) {
        this.chosenContacts = chosenContacts;
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

}
