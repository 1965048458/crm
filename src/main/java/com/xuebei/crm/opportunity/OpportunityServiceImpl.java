package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.CustomerService;
import com.xuebei.crm.customer.Department;
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

}