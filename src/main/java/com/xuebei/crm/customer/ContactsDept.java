package com.xuebei.crm.customer;

public class ContactsDept {

    private String contactsId;

    private String customerName;

    private String prtDeptName;

    private String deptName;

    private String realName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPrtDeptName() {
        return prtDeptName;
    }

    public void setPrtDeptName(String prtDeptName) {
        this.prtDeptName = prtDeptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }



    public String toString() {
        if (prtDeptName == null) {
            return customerName + "-" + deptName + '-' + realName;
        } else {
            return customerName + '-' + prtDeptName + "-" + deptName + '-' + realName;
        }
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

}
