package com.xuebei.crm.company;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.xuebei.crm.journal.JournalMapper;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        String crmUserId;
        String userId ;
        for (CompanyUser user: companyUsers
             ) {
            userId = UUIDGenerator.genUUID();
            crmUserId = companyMapper.getUserId(user.getCrmUserName(), user.getTel());
            if (crmUserId == null || crmUserId.equals("")){
                return;
            }
            companyMapper.joinCompany(crmUserId, userId, companyId);
        }
        companyMapper.addCompany(companyId, companyName);
    }

    @Override
    public CompanyUser getUserInfo(String crmUserId) {
        return companyMapper.getUserInfo(crmUserId);
    }

    @Override
    public List<Company> queryCompany(String word) {
        return companyMapper.queryCompany(word);
    }

    @Override
    public CompanyData queryCompanyData(List<String> childs,String customerId)
    {
        CompanyData companyData=new CompanyData();
        int aCount=0,bCount=0,cCount=0,dCount=0;
        int _2B=0,_2C=0,_2D=0;
        int a2f=0,b2f=0,c2f=0,d2f=0;
        List<TmpCompanyData> oppDataS=companyMapper.searchOpportunityId(childs,customerId);
        List<Integer> oppIdS=new ArrayList<>();
        Double amount=0.0;
        for (TmpCompanyData tmpCompanyData:oppDataS)
        {
            oppIdS.add(tmpCompanyData.getId());
            if (tmpCompanyData.getStatus().equals("D")) {
                if (tmpCompanyData.getAmount() != null) {
                    amount += tmpCompanyData.getAmount();
                }
                dCount++;
            }
        }
        companyData.setdCount(dCount);
        companyData.setAmount(amount);
        dCount=0;
        if (oppIdS.size()!=0)
        oppDataS.addAll(companyMapper.searchOppStatus(oppIdS));
        for (TmpCompanyData tmpCompanyData:oppDataS)
        {
            if (tmpCompanyData.getStatus().equals("A"))
            {
                aCount++;
            }
            else if (tmpCompanyData.getStatus().equals("B"))
            {
                bCount++;
            }
            else if (tmpCompanyData.getStatus().equals("C"))
            {
                cCount++;
            }
            else if (tmpCompanyData.getStatus().equals("D"))
            {
                dCount++;
            }
            if (tmpCompanyData.getNewStatus()!=null)
            {
                if (tmpCompanyData.getNewStatus().equals("B"))
                {
                    _2B++;
                }
                else if (tmpCompanyData.getNewStatus().equals("C"))
                {
                    _2C++;
                }
                else if (tmpCompanyData.getNewStatus().equals("D"))
                {
                    _2D++;
                }
                else if (tmpCompanyData.getNewStatus().equals("F"))
                {
                    if (tmpCompanyData.getStatus().equals("A"))
                    {
                       a2f++;
                    }
                    else if (tmpCompanyData.getStatus().equals("B"))
                    {
                        b2f++;
                    }
                    else if (tmpCompanyData.getStatus().equals("C"))
                    {
                        c2f++;
                    }
                    else if (tmpCompanyData.getStatus().equals("D"))
                    {
                        d2f++;
                    }
                }
            }
        }
        DecimalFormat df=new DecimalFormat("0.00");
        companyData.setSuccessRateA(aCount);
        companyData.setCoverageRateA("一");
        if (aCount==0)
        {
            companyData.setPermeabilityA("0.00");
        }
        else {
            companyData.setPermeabilityA(df.format(a2f * 1.0f / aCount));
        }
        companyData.setSuccessRateB(bCount);
        if (bCount==0) {
            companyData.setCoverageRateB("0.00");
            companyData.setPermeabilityB("0.00");
        }
        else {
            companyData.setCoverageRateB(df.format(_2B * 1.0f / bCount));
            companyData.setPermeabilityB(df.format(b2f * 1.0f / bCount));
        }
        companyData.setSuccessRateC(cCount);
        if (cCount==0) {
            companyData.setCoverageRateC("0.00");
            companyData.setPermeabilityC("0.00");
        }
        else {
            companyData.setCoverageRateC(df.format(_2C * 1.0f / cCount));
            companyData.setPermeabilityC(df.format(c2f * 1.0f / cCount));
        }
        companyData.setSuccessRateD(dCount);
        if (dCount==0) {
            companyData.setCoverageRateD("0.00");
            companyData.setPermeabilityD("0.00");
        }
        else {
            companyData.setCoverageRateD(df.format(_2D * 1.0f / dCount));
            companyData.setPermeabilityD(df.format(d2f * 1.0f / dCount));
        }
        return  companyData;
    }
}
