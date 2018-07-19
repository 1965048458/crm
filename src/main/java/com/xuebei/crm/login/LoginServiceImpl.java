package com.xuebei.crm.login;

import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginRegisterMapper loginRegisterMapper;

    @Override
    public User searchTel(String tel){
        return loginRegisterMapper.searchTel(tel);
    }

    @Override
    public void changePwd(String tel,String pwd) {
        loginRegisterMapper.changePwd(tel,pwd);
    }

}