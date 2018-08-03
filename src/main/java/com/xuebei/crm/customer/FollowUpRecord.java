package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FollowUpRecord {

    @Expose
    private String projectName;

    @Expose
    private Date followUpTime;

    @Expose
    private String followUpPersonRealName;

    @Expose
    private String followUpResult;

    public FollowUpRecord() {
        projectName = "虚拟仿真的建设";
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getFollowUpTime() {
        return followUpTime;
    }

    public void setFollowUpTime(Date followUpTime) {
        this.followUpTime = followUpTime;
    }

    public String getFollowUpPersonRealName() {
        return followUpPersonRealName;
    }

    public void setFollowUpPersonRealName(String followUpPersonRealName) {
        this.followUpPersonRealName = followUpPersonRealName;
    }

    public String getFollowUpResult() {
        return followUpResult;
    }

    public void setFollowUpResult(String followUpResult) {
        this.followUpResult = followUpResult;
    }

    public String getFollowUpTimeStr() {
        DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(followUpTime);
    }

}
