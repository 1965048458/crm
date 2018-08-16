package com.xuebei.crm.department;

import com.xuebei.crm.customer.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Department> departmentList(String customerId, String userId) {

        return null;
    }

    @Override
    public void applyDepartment(String deptId,String userId){

    }

    @Override
    public void delayApplyDepartment(String deptId, String userId){

    }

}
