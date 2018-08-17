package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.journal.VisitRecord;
import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpportunityService
{
    List<Customer> getMyCustomers(String userId);

    Integer addSale(Opportunity opportunity);

    void addOpportunityContact(Integer opportunityId,  String contactId);

    List<Opportunity> queryOpportunity(OpportunitySearchParam opportunitySearchParam);

    Opportunity opportunityDetail(String opportunityId);

    String queryOpportunityCreator(String opportunityId);

    void modifyOpportunity(Opportunity opportunity);

    void addModificationRecord(int opportunityId, String userId);

    List<OpportunityModify> queryModifyRecord(@Param("opportunityId")int opportunityId);

    List<VisitRecord> queryVisitRecord(@Param("opportunityId")int opportunityId);

}