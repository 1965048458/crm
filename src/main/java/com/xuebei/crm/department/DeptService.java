package com.xuebei.crm.department;

import com.xuebei.crm.customer.Department;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 */
public interface DeptService {

    List<Department> departmentList(String customerId, String userId);

    List<Department> myDepartmentList(String customerId, String userId);

    String warningBeforeCreate(String deptName,String customerId, String userId);

    void enclosureApply(String deptId,String userId);

}
