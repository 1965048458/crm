package com.xuebei.crm.opportunity;

import com.google.gson.annotations.Expose;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Support {

    @Expose
    private Integer supportId;
    @Expose
    private Integer salesOpportunityId;
    @Expose
    private SupportTypeEnum supportType;
    @Expose
    private Date expireDate;
    @Expose
    private SupportOrderEnum order;
    @Expose
    private String content;
    @Expose
    private String creatorId;




    public Support() {}
    public Support(Integer salesOpportunityId, SupportTypeEnum supportType, Date expireDate, SupportOrderEnum order,
                   String content, String creatorId) {
        this.salesOpportunityId = salesOpportunityId;
        this.supportType = supportType;
        this.expireDate = expireDate;
        this.order = order;
        this.content = content;
        this.creatorId = creatorId;
    }

    public Integer getSupportId() {
        return supportId;
    }

    public void setSupportId(Integer supportId) {
        this.supportId = supportId;
    }

    public Integer getSalesOpportunityId() {
        return salesOpportunityId;
    }

    public void setSalesOpportunityId(Integer salesOpportunityId) {
        this.salesOpportunityId = salesOpportunityId;
    }

    public SupportTypeEnum getSupportType() {
        return supportType;
    }

    public void setSupportType(SupportTypeEnum supportType) {
        this.supportType = supportType;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public SupportOrderEnum getOrder() {
        return order;
    }

    public void setOrder(SupportOrderEnum order) {
        this.order = order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String showExpire() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(expireDate);
    }
}
