package com.xuebei.crm.project;

import com.xuebei.crm.company.CompanyUser;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.Support;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMapper {

    Integer insertProject(Project project);

    Integer insertProjectStart(ProjectStart projectStart);

    void insertContract(Contract contract);

    Integer insertRefund(Refund refund);

    void updateProjectStart(ProjectStart projectStart);

    void updateContract(Contract contract);

    void updateRefund(Refund refund);

    ProjectStart getProjectStart(@Param("projectId")Integer projectId, @Param("userId") String userId);

    Contract getContract(Integer projectId);

    List<Refund> getRefunds(@Param("projectId") Integer projectId);

    List<Project> queryProjectsByUserId(String userId);

    List<Opportunity> queryOpportunitiesByUserId(String userId);

    String queryOpportunityNameByOpportunityId(Integer opportunityId);

    List<Project> searchProject(ProjectSearchParam param);

    ProjectDetail queryProjectDetailById(String projectId);

    List<Support> querySupportsByProjectId(String projectId);

    List<ProjectDetail> queryMissionUn(@Param("userId") String userId,@Param("admin") boolean admin,@Param("subUserId") List<String> subUserId);

    List<ProjectDetail> queryMission(@Param("userId") String userId,@Param("admin") boolean admin,@Param("subUserId") List<String> subUserId);

    ProjectDetail queryMissionDetail(@Param("supportId") Integer supportId);

    List<Support> querySupportInformation(@Param("supportId")Integer supportId);

    void insertProgressInform(@Param("supportId")Integer supportId,
                              @Param("userId")String userId,
                              @Param("progress")String progress);

    void updateSupportProgress(@Param("userId")String userId,@Param("supportId")Integer supportId,@Param("progress")Integer progress);

    List<CompanyUser> queryCompanyUser(@Param("userId")String userId,@Param("keyword")String keyword);

    void setSupportLeader(@Param("userId")String userId,@Param("supportId")Integer supportId,@Param("leaderId")String leaderId);
}
