package com.xuebei.crm.company;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/25.
 */
public class CompanyUser {

    @Expose
    private String userId;
    @Expose
    private String userPos;
    @Expose
    private String companyId;
    @Expose
    private String crmUserId;
    @Expose
    private String tel;
    @Expose
    private String crmUserName;
    @Expose
    private String department;
    @Expose
    private String email;


    @Expose
    private String createTs;
    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }





    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPos() {
        return userPos;
    }

    public void setUserPos(String userPos) {
        this.userPos = userPos;
    }

    public String getCrmUserId() {
        return crmUserId;
    }

    public void setCrmUserId(String crmUserId) {
        this.crmUserId = crmUserId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCrmUserName() {
        return crmUserName;
    }

    public void setCrmUserName(String crmUserName) {
        this.crmUserName = crmUserName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

}
