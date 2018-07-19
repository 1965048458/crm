package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Customer {

    @Expose
    private String customerId;
    @Expose
    private String customerName;
    @Expose
    private CustomerTypeEnum customerType;
    @Expose
    private String profile;
    @Expose
    private String website;
    @Expose
    private List warningDetails;
    @Expose
    private List contacts;
    @Expose
    private List customer;

    public List getCustomer() {
        return customer;
    }

    public void setCustomer(List customer) {
        this.customer = customer;
    }

    public List getContacts() {
        return contacts;
    }

    public void setContacts(List contacts) {
        this.contacts = contacts;
    }
    public List getWarningDetails() {
        return warningDetails;
    }

    public void setWarningDetails(List warningDetails) {
        this.warningDetails = warningDetails;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public CustomerTypeEnum getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerTypeEnum customerType) {
        this.customerType = customerType;
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

}
