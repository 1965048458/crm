package com.xuebei.crm.customer;

import org.apache.ibatis.annotations.Mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerMapper {

    Boolean isUserHasCustomer(@Param("userId") String userId,
                              @Param("customerId") String customerId);

    Boolean isTopDepartNameExist(@Param("customerId") String customerId,
                                 @Param("departmentName") String departmentName);

    void insertTopDepartment(Department department);

}
