package com.xuebei.crm.login;

import com.xuebei.crm.user.User;

public interface RegisterService
{
    void insertUser(User user);

    User searchTel(String tel);

}