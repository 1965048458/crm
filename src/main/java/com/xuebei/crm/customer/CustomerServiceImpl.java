package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public Boolean isUserHasCustomer(String userId, String customerId) {
        return customerMapper.isUserHasCustomer(userId, customerId);
    }

    /**
     * 增加二级学院
     * @param department
     * @throws DepartmentNameDuplicatedException
     */
    public void addTopDepartment(Department department) throws DepartmentNameDuplicatedException {
        if (customerMapper.isTopDepartNameExist(department.getCustomer().getCustomerId(), department.getDeptName())) {
            throw new DepartmentNameDuplicatedException("二级学院名已存在，请重新输入");
        }

        customerMapper.insertTopDepartment(department);
    }

    /**
     * 新建学校
     */
    public void newSchool( String customer_id,String name,String schoolType,String profile,
                           String website,String creator_id,String create_ts,String updater_id,String update_ts){
        customerMapper.newSchool(customer_id, name, schoolType, profile,
                                 website, creator_id, create_ts, updater_id, update_ts);
    }

    @Override
    public List<Customer> queryCustomerInfo(String searchWord) {

        List<Customer> customerList = customerMapper.queryCustomerInfo(searchWord);

        return customerList;
    }

    @Override
    public List<Department> queryDepartment(String customerId){
        List<Department> departmentList = customerMapper.queryDepartment(customerId);
        Map<String, Department> departmentMap = new HashMap<>();
        for (Department department : departmentList) {
            departmentMap.put(department.getDeptId(), department);
        }
        List<Department> rltList = new ArrayList<>();
        for (Department department: departmentList) {
            if (department.getParent() == null) {
                rltList.add(department);
            } else {
                String prtId = department.getParent().getDeptId();
                Department prtDept = departmentMap.get(prtId);
                prtDept.addSubDept(department);
            }
        }
        return rltList;
    }

    @Override
    public List<String> searchSchool(String keyword){
        return customerMapper.searchSchool(keyword);
    }

}