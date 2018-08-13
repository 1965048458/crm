package com.xuebei.crm.opportunity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date update_ts;

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

    public Date getUpdate_ts() {
        return update_ts;
    }

    public void setUpdate_ts(Date update_ts) {
        this.update_ts = update_ts;
    }
}
