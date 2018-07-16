package com.xuebei.crm.registerAndLogin;

import com.xuebei.crm.user.User;

import java.util.List;

public interface registerAndLoginService
{
	void insertUser(User user);

	List<User> searchTel(String tel);

	void changePwd(String tel, String pwd);
}