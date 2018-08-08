package com.xuebei.crm.opportunity;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/23.
 */
public class Opportunity {
    @Expose
    private String opportunityId;
    @Expose
    private String customerId;
    @Expose
    private String opportunityName;
    @Expose
    private String salesStatus;
    @Expose
    private double amount;
    @Expose
    private Date checkDate;
    @Expose
    private Date clinchDate;
    @Expose
    private double totalCoast;
    @Expose
    private String content;
    @Expose
    private String contactId;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getClinchDate() {
        return clinchDate;
    }

    public void setClinchDate(Date clinchDate) {
        this.clinchDate = clinchDate;
    }

    public double getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(double totalCoast) {
        this.totalCoast = totalCoast;
    }
}
