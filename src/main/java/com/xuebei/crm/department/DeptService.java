package com.xuebei.crm.department;

import com.xuebei.crm.customer.Department;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 */
public interface DeptService {
    List<Department> departmentList(String customerId, String userId);

    void applyDepartment(String deptId,String userId);

    void delayApplyDepartment(String deptId, String userId);
}
