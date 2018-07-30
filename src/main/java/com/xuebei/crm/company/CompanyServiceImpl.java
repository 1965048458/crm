package com.xuebei.crm.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/7/25.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public void insertMember(String name, String tel) {
        //companyMapper.insertMember(name, tel);
        return;
    }
}
