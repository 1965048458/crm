package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
