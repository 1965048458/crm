package com.xuebei.crm.department;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 */
@Mapper
public interface DeptMapper {

    List<Department> searchDepts(@Param("customerId") String customerId,
                                 @Param("userId") String userId);

    List<Department> searchMyDepts(@Param("customerId") String customerId,
                                          @Param("userId") String userId);

    List<Department> searchSubDepts(@Param("deptId") String deptId);

    List<Contacts> searchContacts(@Param("deptId") String deptId);

    List<Department> searchOthersAppliedDepts(@Param("customerId") String customerId,
                                       @Param("userId") String userId);

    List<Department> searchMyAppliedDepts(@Param("customerId") String customerId,
                                       @Param("userId") String userId);

    void applyDepartment(@Param("deptId") String deptId,
                         @Param("userId") String userId,
                         @Param("applyReasons") String reasons);

    void deleteApplyDepartment(@Param("deptId") String deptId,
                               @Param("userId") String userId);

    void delayApplyDepartment(@Param("deptId") String deptId,
                              @Param("userId") String userId);


}
