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
}
