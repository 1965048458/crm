package com.xuebei.crm.company;

import com.google.gson.annotations.Expose;

public class Company {

    @Expose
    private String companyId;
    @Expose
    private String companyName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
