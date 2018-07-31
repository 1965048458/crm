package com.xuebei.crm.company;

import com.xuebei.crm.utils.UUIDGenerator;
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
    public void insertMember(CompanyUser companyUser) {
        companyMapper.joinCompany( companyUser.getCrmUserId(), companyUser.getUserId(), companyUser.getCompanyId());
    }

    @Override
    public void addCompany(String companyName, List<CompanyUser> companyUsers) {
        String companyId = UUIDGenerator.genUUID();
        companyMapper.addCompany(companyId, companyName);

        String crmUserId = null;
        String userId = null;
        for (CompanyUser user: companyUsers
             ) {
            userId = UUIDGenerator.genUUID();
            crmUserId = companyMapper.getUserId(user.getCrmUserName(), user.getTel());
            companyMapper.joinCompany(crmUserId, userId, companyId);
        }

    }
}
