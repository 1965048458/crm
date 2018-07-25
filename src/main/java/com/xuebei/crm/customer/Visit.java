package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2018/7/25.
 */
public class Visit {
    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }
    @Expose
    private String visitId;

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
    @Expose
    private String visitTime;
    @Expose
    private String visitDept;
    @Expose
    private String userId;

    public String getVisitDept() {
        return visitDept;
    }

    public void setVisitDept(String visitDept) {
        this.visitDept = visitDept;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



}
