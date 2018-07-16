package com.xuebei.crm.user;

import com.google.gson.annotations.Expose;

/**
 * Created by Rong Weicheng on 2018/7/10.
 */
public class User {
    @Expose
    private String userId;
    @Expose
    private String realName;
    @Expose
    private String tel;
    @Expose
    private String CRMUserId;
    @Expose
    private String companyId;
    @Expose
    private UserTypeEnum userType;
    @Expose
    private String avatarUrl;
    @Expose
    private String pwd ;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCRMUserId() { return CRMUserId; }

    public void setCRMUserId(String CRMUserId) { this.CRMUserId = CRMUserId; }

    public String getCompanyId() { return companyId; }

    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
