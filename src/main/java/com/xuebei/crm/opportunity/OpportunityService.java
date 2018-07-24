package com.xuebei.crm.opportunity;

import com.xuebei.crm.user.User;

import java.util.List;

public interface OpportunityService
{
	List<User> searchUser(String keyword);

	void insertUser(User user);

	void editUser(User user);
}