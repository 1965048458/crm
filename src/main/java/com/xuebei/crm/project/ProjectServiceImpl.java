package com.xuebei.crm.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */
@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public void addProject(Project project) {
        projectMapper.insertProject(project);
    }

    @Override
    public List<Project> searchProject(ProjectSearchParam param) {

        List<Project> projectList = projectMapper.searchProject(param);
        return projectList;
    }
}
