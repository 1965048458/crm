package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;

public interface CustomerService {

    Boolean isUserHasCustomer(String userId, String customerId);

    void addTopDepartment(Department department) throws DepartmentNameDuplicatedException;

}
