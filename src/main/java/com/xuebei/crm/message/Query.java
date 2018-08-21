package com.xuebei.crm.message;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2018/8/21.
 */
public class Query {

    @Expose
    private String queryCustomerName;
    @Expose
    private String queryCustomerId;
    @Expose
    private String queryDeptName;
    @Expose
    private String queryDeptId;
    @Expose
    private String queryProjectId;
    @Expose
    private String queryProjectName;
    @Expose
    private String queryUserId;
    @Expose
    private String queryUserName;
    @Expose
    private QueryTypeEnum queryType;
    @Expose
    private String queryDetails;
    @Expose
    private String queryStartTime;


    public String getQueryCustomerName() {
        return queryCustomerName;
    }

    public void setQueryCustomerName(String queryCustomerName) {
        this.queryCustomerName = queryCustomerName;
    }

    public String getQueryCustomerId() {
        return queryCustomerId;
    }

    public void setQueryCustomerId(String queryCustomerId) {
        this.queryCustomerId = queryCustomerId;
    }

    public String getQueryDeptName() {
        return queryDeptName;
    }

    public void setQueryDeptName(String queryDeptName) {
        this.queryDeptName = queryDeptName;
    }

    public String getQueryDeptId() {
        return queryDeptId;
    }

    public void setQueryDeptId(String queryDeptId) {
        this.queryDeptId = queryDeptId;
    }

    public String getQueryProjectId() {
        return queryProjectId;
    }

    public void setQueryProjectId(String queryProjectId) {
        this.queryProjectId = queryProjectId;
    }

    public String getQueryProjectName() {
        return queryProjectName;
    }

    public void setQueryProjectName(String queryProjectName) {
        this.queryProjectName = queryProjectName;
    }

    public String getQueryUserId() {
        return queryUserId;
    }

    public void setQueryUserId(String queryUserId) {
        this.queryUserId = queryUserId;
    }

    public String getQueryUserName() {
        return queryUserName;
    }

    public void setQueryUserName(String queryUserName) {
        this.queryUserName = queryUserName;
    }

    public QueryTypeEnum getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryTypeEnum queryType) {
        this.queryType = queryType;
    }

    public String getQueryDetails() {
        return queryDetails;
    }

    public void setQueryDetails(String queryDetails) {
        this.queryDetails = queryDetails;
    }

    public String getQueryStartTime() {
        return queryStartTime;
    }

    public void setQueryStartTime(String queryStartTime) {
        this.queryStartTime = queryStartTime;
    }
}
