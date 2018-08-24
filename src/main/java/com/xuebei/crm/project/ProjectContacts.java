package com.xuebei.crm.project;

import com.google.gson.annotations.Expose;

public class ProjectContacts {
    @Expose
    private String customerName;
    @Expose
    private String topDeptName;
    @Expose
    private String subDeptName;
    @Expose
    private String contactsName;
    @Expose
    private String type;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTopDeptName() {
        return topDeptName;
    }

    public void setTopDeptName(String topDeptName) {
        this.topDeptName = topDeptName;
    }

    public String getSubDeptName() {
        return subDeptName;
    }

    public void setSubDeptName(String subDeptName) {
        this.subDeptName = subDeptName;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String showTopDeptName() {
        if (topDeptName == null) {
            return subDeptName;
        } else {
            return topDeptName;
        }
    }
}