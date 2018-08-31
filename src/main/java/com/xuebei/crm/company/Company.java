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
    @Expose
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }
    @Expose
    private int applyStaff;
    @Expose
    private int message;

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getApplyStaff() {
        return applyStaff;
    }

    public void setApplyStaff(int applyStaff) {
        this.applyStaff = applyStaff;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

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
