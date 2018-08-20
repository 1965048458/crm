package com.xuebei.crm.project;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */
public interface ProjectService {

    void addProject(Project project);

    List<Project> searchProject(ProjectSearchParam param);
}
