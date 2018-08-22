package com.xuebei.crm.project;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */
public interface ProjectService {

    Integer addProject(Project project);

    List<Project> searchProject(ProjectSearchParam param);
}
