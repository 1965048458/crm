package com.xuebei.crm.opportunity;

import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("opportunityService")
public class OpportunityServiceImpl implements OpportunityService {
    @Autowired
    private OpportunityMapper opportunityMapper;


}