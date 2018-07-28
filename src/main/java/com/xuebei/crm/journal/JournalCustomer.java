package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.Contacts;

import java.util.List;

public class JournalCustomer {

    private String customerId;
    @Expose
    private String name;
    @Expose
    private boolean contactsFold = true;
    @Expose
    private List<Contacts> contactsGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contacts> getContactsGroup() {
        return contactsGroup;
    }

    public void setContactsGroup(List<Contacts> contactsGroup) {
        this.contactsGroup = contactsGroup;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean getContactsFold() {
        return contactsFold;
    }
}
