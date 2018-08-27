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

    List<OpportunityModify> queryModifyRecord(int opportunityId);

    List<VisitRecord> queryVisitRecord(int opportunityId);

   List<Support> queryApplySupport(int opportunityId);

    void insertFailReason(int opportunityId, String failReason);

    void deleteOpportunity(int opportunityId);

    void convertOpportunity(int opportunityId);



}