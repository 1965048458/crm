package com.xuebei.crm.company;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Company {

    @Expose
    private String companyId;
    @Expose
    private String companyName;
    @Expose
    List<CompanyUser> companyUserList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    private String status;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<CompanyUser> getCompanyUserList() {
        return companyUserList;
    }

    public void setCompanyUserList(List<CompanyUser> companyUserList) {
        this.companyUserList = companyUserList;
    }
}
