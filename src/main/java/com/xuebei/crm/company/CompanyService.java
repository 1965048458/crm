package com.xuebei.crm.company;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
public interface CompanyService {

    void insertMember(String name, String tel);

    void addCompany(String companyName, String myPos, List<CompanyUser> colList);
}
