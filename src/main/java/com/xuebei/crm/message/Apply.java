package com.xuebei.crm.message;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2018/8/21.
 */
public class Apply {
    @Expose
    private String applyUserId;
    @Expose
    private String applyUserName;
    @Expose
    private String applyTime;
    @Expose
    private String applyDetails;
    @Expose
    private String applyId;
    @Expose
    private ApplyTypeEnum applyType;

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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
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
