package com.xuebei.crm.company;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    List<Company> queryCompanyListByCrmUserId(@Param("crmUserId") String crmUserId);

    String queryUserId(@Param("crmUserId") String crmUserId,
                       @Param("companyId") String companyId);
}
