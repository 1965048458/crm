package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.CustomerService;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.journal.VisitRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("opportunityService")
public class OpportunityServiceImpl implements OpportunityService {
    @Autowired
    private OpportunityMapper opportunityMapper;

    @Autowired
    private CustomerService customerService;

    @Override
    public List<Customer> getMyCustomers(String userId) {
        try {
            List<Customer> customerList = customerService.getMyCustomers(userId);
            for (Customer customer : customerList) {
                List<Department> departmentList = customerService.queryDepartment(customer.getCustomerId(), userId);
                customer.setContacts(departmentList);
            }
            return customerList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer addSale(Opportunity opportunity) {
        Integer opportunityId = opportunityMapper.addSale(opportunity);
        return opportunityId;
    }

    @Override
    public void addOpportunityContact(Integer opportunityId, String contactId) {
        opportunityMapper.addOpportunityContact(opportunityId, contactId);
    }

    @Override
    public List<Opportunity> queryOpportunity(OpportunitySearchParam opportunitySearchParam){
        List<Opportunity> opportunities = opportunityMapper.queryOpportunity(opportunitySearchParam) ;
        return opportunities;
    }

    @Override
    public Opportunity opportunityDetail(String opportunityId){
        return opportunityMapper.opportunityDetail(opportunityId);
    }

    @Override
    public String queryOpportunityCreator(String opportunityId){
        return opportunityMapper.queryOpportunityCreator(opportunityId);
    }

    @Override
    public void modifyOpportunity(Opportunity opportunity) {
        opportunityMapper.modifyOpportunity(opportunity);
    }

    @Override
    public void addModificationRecord(int opportunityId, String userId){
        opportunityMapper.addModificationRecord(opportunityId,userId);
    }

    @Override
    public List<OpportunityModify> queryModifyRecord(int opportunityId) {
        return opportunityMapper.queryModifyRecord(opportunityId);
    }

    @Override
    public List<VisitRecord> queryVisitRecord(int opportunityId) {
        return opportunityMapper.queryVisitRecord(opportunityId);
    }

//    @Override
//    public List<ApplySupport> queryApplySupport(int opportunityId){
//      return opportunityMapper.queryApplySupport(opportunityId);
//    }


    @Override
    public void insertFailReason(int opportunityId, String failReason) {
        opportunityMapper.insertFailReason(opportunityId,failReason);
    }
}