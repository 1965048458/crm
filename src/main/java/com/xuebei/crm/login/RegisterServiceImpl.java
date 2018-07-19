package com.xuebei.crm.login;

import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private LoginRegisterMapper loginRegisterMapper;

    @Override
    public void insertUser(User user) {
        loginRegisterMapper.insertUser(user);
    }

    @Override
    public User searchTel(String tel){
        return loginRegisterMapper.searchTel(tel);
    }


}