package com.xuebei.crm.project;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectService {

    Integer addProject(Project project);

    void addContract(Contract contract);

    Integer addRefund(Refund refund);

    void updateContract(Contract contract);

    void updateRefund(Refund refund);

    void startProject(ProjectStart projectStart);

    List<Project> searchProject(ProjectSearchParam param);

    String queryOpportunityNameByOpportunityId(Integer projectId);
}
