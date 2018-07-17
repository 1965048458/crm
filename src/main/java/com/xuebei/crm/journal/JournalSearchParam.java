package com.xuebei.crm.journal;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Rong Weicheng on 2018/7/14.
 */
public class JournalSearchParam {
    private String userId;
    private JournalTypeEnum journalType;
    private String senderIds;
    private String[] sdId;

    private String customer;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

    private Integer isRead;

    private Integer isMine;

    public Boolean getMy() {
        return isMy;
    }

    public void setMy(Boolean my) {
        isMy = my;
    }

    private Boolean isMy;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JournalTypeEnum getJournalType() {
        return journalType;
    }

    public void setJournalType(JournalTypeEnum journalType) {
        this.journalType = journalType;
    }

    public String getSenderIds() {
        return senderIds;
    }

    public void setSenderIds(String senderIds) {
        this.senderIds = senderIds;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsMine() {
        return isMine;
    }

    public void setIsMine(Integer isMine) {
        this.isMine = isMine;
    }

    public String[] getSdId() {
        return sdId;
    }

    public void setSdId(String[] sdId) {
        this.sdId = sdId;
    }
}
