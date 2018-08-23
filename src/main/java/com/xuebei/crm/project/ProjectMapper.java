package com.xuebei.crm.project;

import com.xuebei.crm.opportunity.Opportunity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {

    Integer insertProject(Project project);

    void insertContract(Contract contract);

    Integer insertRefund(Refund refund);

    void updateContract(Contract contract);

    void updateRefund(Refund refund);

    List<Project> queryProjectsByUserId(String userId);

    List<Opportunity> queryOpportunitiesByUserId(String userId);

    String queryOpportunityNameByOpportunityId(Integer opportunityId);

    List<Project> searchProject(ProjectSearchParam param);
}
