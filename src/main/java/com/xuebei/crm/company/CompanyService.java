package com.xuebei.crm.company;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
public interface CompanyService {

    void insertMember(CompanyUser companyUser);

    void addCompany(String companyName, List<CompanyUser> companyUsers);
}
