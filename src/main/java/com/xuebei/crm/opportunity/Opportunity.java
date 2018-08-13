package com.xuebei.crm.opportunity;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/23.
 */
public class Opportunity {
    @Expose
    private Integer opportunityId;
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
    @Expose
    private String userId;
    @Expose
    private String create_ts;
    @Expose
    private String updater_id;
    @Expose
    private String update_ts;

    public String getUpdater_id() {
        return updater_id;
    }

    public void setUpdater_id(String updater_id) {
        this.updater_id = updater_id;
    }

    public String getUpdate_ts() {
        return update_ts;
    }

    public void setUpdate_ts(String update_ts) {
        this.update_ts = update_ts;
    }






    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
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

    public String getCreate_ts() {
        return create_ts;
    }

    public void setCreate_ts(String create_ts) {
        this.create_ts = create_ts;
    }
}
