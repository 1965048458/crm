package com.xuebei.crm.opportunity;

import com.google.gson.annotations.Expose;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/7/23.
 */
public class OpportunityModify {
    @Expose
    private Integer opportunityId;
    @Expose
    private String customerId;
    @Expose
    private String customerName;
    @Expose
    private String opportunityName;
    @Expose
    private String userId;
    @Expose
    private String realName;
    @Expose
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @Expose
    private String comment;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
