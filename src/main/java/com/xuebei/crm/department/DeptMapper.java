package com.xuebei.crm.department;

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

    List<Department> searchOthersDepts(@Param("customerId") String customerId,
                                       @Param("userId") String userId);

    List<Department> searchMyDepts(@Param("customerId") String customerId,
                                       @Param("userId") String userId);


}
