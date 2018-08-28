package com.xuebei.crm.customer;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {

    Boolean isUserHasCustomer(@Param("userId") String userId,
                              @Param("customerId") String customerId);

    Boolean isTopDepartNameExist(@Param("customerId") String customerId,
                                 @Param("departmentName") String departmentName);

    Boolean isSubDepartNameExist(@Param("prtId")String parentId,
                                 @Param("departmentName")String departmentName);


    Department searchDeptByName(@Param("customerId") String customerId,
                                @Param("departmentName")String departmentName);

    void updateDept(@Param("website")String website,
                          @Param("profile") String profile,
                          @Param("deptId") String deptId);



    void insertDepartment(Department department);

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

    Contacts queryContactsById(@Param("contactsId") String contactsId);

    void insertContacts(Contacts contacts);

    List<Customer> queryCustomerInfo(@Param("searchWord") String searchWord);

    Customer queryCustomer(@Param("customerId") String customerId);

    List<Department> queryDepartment(@Param("customerId") String customerId,
                                     @Param("userId") String userId);

    List<Department> querySubDepartment(@Param("deptId") String deptId);


    List<Contacts> queryContacts(@Param("customerId") String customerId);

    void updateEnclosureApply(@Param("deptId") String deptId,
                              @Param("userId") String userId);

    void insertEnclosureApply(EnclosureApply enclosureApply);

    List<EnclosureApply> queryEnclosureApply(String deptId);

    EnclosureApply queryNewEnclosureApply(@Param("deptId")String deptId);

    List<Visit> queryMyVisit(@Param("deptId") String deptId,
                            @Param("startTime") String startTime,
                           @Param("userId") String userId
                            );

    Visit queryElseVisit(@Param("deptId") String deptId,
                       @Param("startTime") String startTime,
                       @Param("userId") String userId
    );

    void insertEnclosureDelayApply(EnclosureApply enclosureApply);

    List<String> searchSchool(@Param("keyword")String keyword);

    List<ContactsType> queryContactsTypes(@Param("customerId") String customerId);

    ContactsDept queryContactsDept(@Param("contactsId") String contactsId);

    List<Customer> getMyCustomers(@Param("userId") String userId);

    List<Customer> getCommonCustomers(@Param("userId") String userId);

    List<FollowUpRecord> queryFollowUpRecordsByContactsId(@Param("contactsId") String contactsId);

    String lastFollowTs(@Param("customerId") String customerId);

    Integer insertCustomerCompanyRelation(@Param("customerId") String customerId,
                                          @Param("companyId") String companyId);


    Integer insertContactsType(@Param("customerId") String customerId,
                               @Param("contactsTypeName") String contactsType);

    Boolean isContactsTypeExist(@Param("customerId") String customerId,
                                @Param("contactsTypeName") String contactsTypeNa);

    Contacts queryOpportunityDetail(String opportunityId);

    List<FollowUpRecord> queryFollowUpRecordsByProjectId(@Param("projectId") String projectId);

}
