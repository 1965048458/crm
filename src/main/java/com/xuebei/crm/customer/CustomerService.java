package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;

import java.util.List;

public interface CustomerService {

    Boolean isUserHasCustomer(String userId, String customerId);

    void addTopDepartment(Department department) throws DepartmentNameDuplicatedException;

    List<Customer> queryCustomerInfo(String searchWord);
}
