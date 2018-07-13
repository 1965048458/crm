package com.xuebei.crm.sample;

import com.xuebei.crm.user.User;

import java.util.List;

public interface SampleService
{
	List<User> searchUser(String keyword);

	void insertUser(User user);

	void editUser(User user);
}