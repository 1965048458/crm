package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2018/7/27.
 */
public class ContactInfo {

    @Expose
    private String visitContactsId;

    @Expose
    private String contactName;

    @Expose
    private String customerName;

    public String getVisitContactsId() {
        return visitContactsId;
    }

    public void setVisitContactsId(String visitContactsId) {
        this.visitContactsId = visitContactsId;
    }

    @Expose
    private String typeName;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
