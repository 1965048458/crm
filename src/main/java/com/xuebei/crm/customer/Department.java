package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Department {

    @Expose
    private String deptId;
    @Expose
    private String deptName;
    @Expose
    private String profile;
    @Expose
    private String website;
    @Expose
    private EnclosureStatusEnum enclosureStatus;
    @Expose
    private Customer customer;
    @Expose
    private Department parent;
    @Expose
    private List<Contacts> contactsList = new ArrayList<>();
    @Expose
    private List<Department> departmentList = new ArrayList<>();
    @Expose
    private OpenSeaWarning openSeaWarning;

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Expose
    private Integer contactNumber = 0;

    public void addContact(int contactNumber){
        this.contactNumber+=contactNumber;
    }

    private List<EnclosureApply> enclosureApplyList = new ArrayList<>();

    public List<EnclosureApply> getEnclosureApplyList() {
        return enclosureApplyList;
    }

    public void setEnclosureApplyList(List<EnclosureApply> enclosureApplyList) {
        this.enclosureApplyList = enclosureApplyList;
    }

    public List<Contacts> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public OpenSeaWarning getOpenSeaWarning() {
        return openSeaWarning;
    }

    public void setOpenSeaWarning(OpenSeaWarning openSeaWarning) {
        this.openSeaWarning = openSeaWarning;
    }



    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public EnclosureStatusEnum getEnclosureStatus() {
        return enclosureStatus;
    }

    public void setEnclosureStatus(EnclosureStatusEnum enclosureStatus) {
        this.enclosureStatus = enclosureStatus;
    }

    public void addSubDept(Department department) {
        if (departmentList == null) {
            departmentList = new ArrayList<>();
        }
        departmentList.add(department);
    }

    public void addSubContact(Contacts contacts){
        contactsList.add(contacts);
    }

    public void addEnclosureApply(EnclosureApply enclosureApply){

        enclosureApplyList.add(enclosureApply);
    }
}
