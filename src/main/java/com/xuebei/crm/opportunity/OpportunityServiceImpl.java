package com.xuebei.crm.opportunity;

import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("opportunityService")
public class OpportunityServiceImpl implements OpportunityService {
    @Autowired
    private OpportunityMapper opportunityMapper;

    @Override
    public List<User> searchUser(String keyword) {
        return opportunityMapper.searchUser(keyword);
    }

    @Override
    public void insertUser(User user) { opportunityMapper.insertUser(user);
    }

    @Override
    public void editUser(User user) {
        opportunityMapper.editUser(user);
    }
}