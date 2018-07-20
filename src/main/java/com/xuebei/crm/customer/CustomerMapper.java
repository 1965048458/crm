package com.xuebei.crm.customer;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {

    Boolean isUserHasCustomer(@Param("userId") String userId,
                              @Param("customerId") String customerId);

    Boolean isTopDepartNameExist(@Param("customerId") String customerId,
                                 @Param("departmentName") String departmentName);

    void insertTopDepartment(Department department);

    void newSchool(@Param("customer_id")String customer_id,
                   @Param("name")String name,
                   @Param("schoolType")String schoolType,
                   @Param("profile")String profile,
                   @Param("website")String website,
                   @Param("creator_id")String creator_id,
                   @Param("create_ts")String create_ts,
                   @Param("updater_id")String updater_id,
                   @Param("update_ts")String update_ts);
    Department queryDepartmentById(@Param("deptId") String deptId);

    ContactsType queryContactsTypeById(@Param("contactsTypeId") String contactsTypeId);

    void insertContacts(Contacts contacts);

    List<Customer> queryCustomerInfo(@Param("searchWord") String searchWord);

    List<Department> queryDepartment(@Param("customerId") String customerId);

    List<String> searchSchool(@Param("keyword")String keyword);

    List<ContactsType> queryContactsTypes(@Param("customerId") String customerId);
}
