package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;

public interface CustomerService {

    Boolean isUserHasCustomer(String userId, String customerId);

    void addTopDepartment(Department department) throws DepartmentNameDuplicatedException;

    void newSchool(String customer_id,String name,String schoolType,String profile,
                   String website,String creator_id,String create_ts,String updater_id,String update_ts);

}
