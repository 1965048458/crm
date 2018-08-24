package com.xuebei.crm.project;

import java.util.List;

public interface ProjectService {

    Integer addProject(Project project);

    void addContract(Contract contract);

    Integer addRefund(Refund refund);

    Integer addProjectStart(ProjectStart projectStart);

    void updateProjectStart(ProjectStart projectStart);

    void updateContract(Contract contract);

    void updateRefund(Refund refund);

    ProjectStart getProjectStart(Integer projectId,  String userId);

    Contract getContract(Integer projectId);

    List<Refund> getRefunds(Integer projectId);

    void startProject(ProjectStart projectStart);

    List<Project> searchProject(ProjectSearchParam param);

    String queryOpportunityNameByOpportunityId(Integer projectId);
}
