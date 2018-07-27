package com.xuebei.crm.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public void insertMember(String name, String tel) {
        companyMapper.insertMember(name, tel);
    }

    @Override
    public void addCompany(String companyName, String myPos, List<CompanyUser> colList) {
        companyMapper.addCompany(companyName, myPos, colList);
    }
}
