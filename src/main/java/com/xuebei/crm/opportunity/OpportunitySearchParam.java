package com.xuebei.crm.opportunity;


import com.google.gson.annotations.Expose;
import com.xuebei.crm.member.Member;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class OpportunitySearchParam {
    //我负责的商机/下属负责的商机
    private String scene;

    private String userId;

    private String salesStatus;

    private String customerName;

    private String sortMode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createEnd;
    @Expose
    private List<String> subUserId;


    private String subUser;

    private String [] chooseSubUser;

    public String[] getChooseSubUser() {
        return chooseSubUser;
    }

    public void setChooseSubUser(String[] chooseSubUser) {
        this.chooseSubUser = chooseSubUser;
    }

    public String getSubUser() {
        return subUser;
    }

    public void setSubUser(String subUser) {
        this.subUser = subUser;
    }

    public List<String> getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(List<String> subUserId) {
        this.subUserId = subUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSortMode() {
        return sortMode;
    }

    public void setSortMode(String sortMode) {
        this.sortMode = sortMode;
    }

    public Date getCreateStart() {
        return createStart;
    }

    public void setCreateStart(Date createStart) {
        this.createStart = createStart;
    }

    public Date getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(Date createEnd) {
        this.createEnd = createEnd;
    }


}
