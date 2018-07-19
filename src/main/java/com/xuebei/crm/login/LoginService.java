package com.xuebei.crm.login;

import com.xuebei.crm.user.User;

public interface LoginService
{

    User searchTel(String tel);

    void changePwd(String tel, String pwd);
}