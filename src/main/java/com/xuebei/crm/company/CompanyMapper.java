package com.xuebei.crm.company;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    List<Company> queryCompanyListByCrmUserId(@Param("crmUserId") String crmUserId);

    String queryUserId(@Param("crmUserId") String crmUserId,
                       @Param("companyId") String companyId);

    void joinCompany(@Param("crmUserId") String crmUserId,
                     @Param("userId") String userId,
                     @Param("companyId") String companyId);

    String queryCompanyIdByUserId(@Param("userId") String userId);

    void addCompany(String companyId, String companyName);

    String getUserId(String userName, String tel);

}
