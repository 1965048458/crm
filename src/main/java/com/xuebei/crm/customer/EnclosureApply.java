package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2018/7/23.
 */
public class EnclosureApply {
    @Expose
    private Integer enclosureApplyId;
    @Expose
    private String applyTime;
    @Expose
    private String deptId;
    @Expose
    private String userId;
    @Expose
    private String reasons;
    @Expose
    private String startTime;
    @Expose
    private String endTime;
    @Expose
    private String statusCd;
    @Expose
    private String updaterId;
    @Expose
    private String updateTime;

    public Integer getEnclosureApplyId() {
        return enclosureApplyId;
    }

    public void setEnclosureApplyId(Integer enclosureApplyId) {
        this.enclosureApplyId = enclosureApplyId;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
