package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.user.User;

import java.util.List;

public interface OpportunityService
{
    List<Customer> getMyCustomers(String userId);

    void addSale(Opportunity opportunity);
}