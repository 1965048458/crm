package com.xuebei.crm.registerAndLogin;

import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("registerAndLoginService")
public class RegisterAndLoginServiceImpl implements RegisterAndLoginService {
    @Autowired
    private RegisterAndLoginMapper registerAndLoginMapper;

    @Override
    public void insertUser(User user) {
        registerAndLoginMapper.insertUser(user);
    }

    @Override
    public List<User> searchTel(String tel){
        return registerAndLoginMapper.searchTel(tel);
    }

    @Override
    public void changePwd(String tel,String pwd) {
        registerAndLoginMapper.changePwd(tel,pwd);
    }

}