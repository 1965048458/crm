package com.xuebei.crm.company;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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

    void addCompany(@Param("companyId")String companyId, @Param("companyName")String companyName);

    String getUserId(@Param("userName") String userName, @Param("tel") String tel);

    String queryUserType(@Param("userId") String userId);

    List<Company> queryCompanyList(@Param("crmUserId") String crmUserId);

    List<Company> queryCompany(@RequestParam("name") String name);

    Company queryCompanyComplete(@RequestParam("name") String name);


    String queryApplyStatus(@Param("crmUserId") String crmUserId,
                            @Param("companyId") String companyId);

    List<CompanyUser> queryApplyStaff(@Param("userId") String userId);

    void agreeApply(@Param("userId") String userId,
                    @Param("crmUserId") String crmUserId);

    void refuseApply(@Param("userId") String userId,
                     @Param("crmUserId") String crmUserId);

    String queryCompanyName(@Param("userId") String userId);

    String queryStatus(@Param("userId") String userId);

    String queryCrmUserId(@Param("userId") String userId);




}
