package com.xuebei.crm.project;

import com.xuebei.crm.company.CompanyUser;
import com.xuebei.crm.opportunity.Support;

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

    ProjectDetail getProjectDetail(String projectId);

    String queryOpportunityNameByOpportunityId(Integer projectId);

    List<ProjectDetail> queryMissionUn(String userId);

    List<ProjectDetail> queryMission(String userId);

    ProjectDetail queryMissionDetail(Integer supportId);

    List<Support> querySupportInformation(Integer supportId);

    void insertProgressInform(Integer supportId,String userId, String progress);

    void updateSupportProgress(Integer supportId, Integer progress);

    List<CompanyUser> queryCompanyUser(String userId);

    void setSupportLeader(Integer supportId,String leaderId);


}
