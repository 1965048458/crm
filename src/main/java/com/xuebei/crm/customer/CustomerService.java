package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;

import java.util.List;

public interface CustomerService {

    Boolean isUserHasCustomer(String userId, String customerId);

    void addTopDepartment(Department department) throws DepartmentNameDuplicatedException;

    void addSubDepartment(String parentDeptId, String deptName);

    void newSchool(String customer_id,String name,String schoolType,String profile,
                   String website,String creator_id,String create_ts,String updater_id,String update_ts);

    List<Customer> queryCustomerInfo(String searchWord);

    List<Department> queryDepartment(String customerId,String userId);

    List querySearchList(List<Department> deptList);

    void enclosureDelayApply(String deptId);

    List<String> searchSchool(String keyword);

    List<Customer> getMyCustomers(String userId);

    Boolean isDepartmentNameDuplicated(String customerId, String deptName);

    Boolean isSubDepartmentNameDuplicated(String parentDeptId, String deptName);
}
