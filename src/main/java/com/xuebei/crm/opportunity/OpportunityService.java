package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpportunityService
{
    List<Customer> getMyCustomers(String userId);

    Integer addSale(Opportunity opportunity);

    void addOpportunityContact(Integer opportunityId,  String contactId);


}