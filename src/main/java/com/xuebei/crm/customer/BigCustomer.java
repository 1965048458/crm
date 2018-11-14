package com.xuebei.crm.customer;

import java.util.List;

import com.google.gson.annotations.Expose;

public class BigCustomer {

	@Expose
	private String realname;
	@Expose
	private List<Customer> depts;
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public List<Customer> getDepts() {
		return depts;
	}
	public void setDepts(List<Customer> depts) {
		this.depts = depts;
	}
}
