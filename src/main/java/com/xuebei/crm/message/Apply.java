package com.xuebei.crm.message;

import com.google.gson.annotations.Expose;

import java.sql.DatabaseMetaData;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/21.
 */
public class Apply {
    @Expose
    private String customerName;
    @Expose
    private String customerId;
    @Expose
    private String applyUserId;
    @Expose
    private String applyUserName;
    @Expose
    private Date applyTime;
    @Expose
    private String applyDetails;
    @Expose
    private String applyId;
    @Expose
    private ApplyTypeEnum applyType;

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ApplyTypeEnum getApplyType() {
        return applyType;
    }

    public void setApplyType(ApplyTypeEnum applyType) {
        this.applyType = applyType;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyDetails() {
        return applyDetails;
    }

    public void setApplyDetails(String applyDetails) {
        this.applyDetails = applyDetails;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
