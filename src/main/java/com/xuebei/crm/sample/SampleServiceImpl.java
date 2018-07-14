package com.xuebei.crm.sample;

import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {
    @Autowired
    private SampleMapper sampleMapper;

    @Override
    public List<User> searchUser(String keyword) {
        return sampleMapper.searchUser(keyword);
    }

    @Override
    public void insertUser(User user) {
        sampleMapper.insertUser(user);
    }

    @Override
    public void editUser(User user) {
        sampleMapper.editUser(user);
    }
}