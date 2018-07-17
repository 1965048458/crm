package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

public class Department {

    @Expose
    private String deptId;
    @Expose
    private String deptName;
    @Expose
    private String profile;
    @Expose
    private String website;
    @Expose
    private EnclosureStatusEnum enclosureStatus;
    @Expose
    private Customer customer;

    private Department parent;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public EnclosureStatusEnum getEnclosureStatus() {
        return enclosureStatus;
    }

    public void setEnclosureStatus(EnclosureStatusEnum enclosureStatus) {
        this.enclosureStatus = enclosureStatus;
    }

}
