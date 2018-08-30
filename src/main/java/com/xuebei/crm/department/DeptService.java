package com.xuebei.crm.department;

import com.xuebei.crm.customer.Department;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 */
public interface DeptService {

    //我的组织机构列表中显示的东西，包括我的，待审核，未圈
    List<Department> departmentList(String customerId, String userId);

    //被我圈走的组织机构
    List<Department> myDepartmentList(String customerId, String userId);

    WarningBeforeCreateEnum warningBeforeCreate(String deptName,String customerId, String userId);

    void enclosureApply(String deptId,String userId,String reasons);

    void enclosureDelayApply(String deptId, String userId,String reasons);

}
