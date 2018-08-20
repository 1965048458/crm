package com.xuebei.crm.project;

import com.xuebei.crm.opportunity.Opportunity;
import org.apache.ibatis.annotations.Mapper;

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
}
