package com.xuebei.crm.project;

import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.Support;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */
@Mapper
public interface ProjectMapper {

    Integer insertProject(Project project);

    List<Project> queryProjectsByUserId(String userId);

    List<Opportunity> queryOpportunitiesByUserId(String userId);

    String queryOpportunityNameByOpportunityId(Integer opportunityId);

    List<Project> searchProject(ProjectSearchParam param);

    ProjectDetail queryProjectDetailById(String projectId);

    List<Support> querySupportsByProjectId(String projectId);

    List<Support> queryMission(@Param("userId") String userId);
}
