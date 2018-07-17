package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

public class Contacts {

    @Expose
    private String contactsId;
    @Expose
    private String realName;
    @Expose
    private String gender;
    @Expose
    private String phone;
    @Expose
    private String wechat;
    @Expose
    private String tel;
    @Expose
    private String QQ;
    @Expose
    private String email;
    @Expose
    private String officeAddr;
    @Expose
    private String profile;
    @Expose
    private String specialRelationship;
    @Expose
    private Department department;

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeAddr() {
        return officeAddr;
    }

    public void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSpecialRelationship() {
        return specialRelationship;
    }

    public void setSpecialRelationship(String specialRelationship) {
        this.specialRelationship = specialRelationship;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
