package com.xuebei.crm.project;

import com.google.gson.annotations.Expose;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;

public class ProjectSearchParam {

    private String creator;

    @Override
    public String toString() {
        return "ProjectSearchParam{" +
                "creator='" + creator + '\'' +
                ", subMember=" + Arrays.toString(subMember) +
                ", subUsers='" + subUsers + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", customerName='" + customerName + '\'' +
                ", userId='" + userId + '\'' +
                ", before=" + before +
                ", after=" + after +
                ", isAdmin='" + isAdmin + '\'' +
                '}';
    }

    private String[] subMember;
    private String subUsers;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    private String status;
    private String customerName;
    private String userId;
    private Integer before;
    private Integer after;
    private String isAdmin;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getSubUsers() {
        return subUsers;
    }

    public void setSubUsers(String subUsers) {
        this.subUsers = subUsers;
    }

    public Integer getBefore() {
        return before;
    }

    public void setBefore(Integer before) {
        this.before = before;
    }

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String[] getSubMember() {
        return subMember;
    }

    public void setSubMember(String[] subMember) {
        this.subMember = subMember;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
