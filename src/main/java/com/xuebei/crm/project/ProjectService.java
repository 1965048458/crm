package com.xuebei.crm.project;

import com.xuebei.crm.opportunity.Support;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */
public interface ProjectService {

    Integer addProject(Project project);

    List<Project> searchProject(ProjectSearchParam param);

    ProjectDetail getProjectDetail(String projectId);

    List<Support> queryMission(String userId);
}
