package com.xuebei.crm.project;

import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.FollowUpRecord;
import com.xuebei.crm.opportunity.Support;
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
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Integer addProject(Project project) {
        return projectMapper.insertProject(project);
    }

    @Override
    public List<Project> searchProject(ProjectSearchParam param) {

        List<Project> projectList = projectMapper.searchProject(param);
        return projectList;
    }

    public ProjectDetail getProjectDetail(String projectId) {
        ProjectDetail projectDetail = projectMapper.queryProjectDetailById(projectId);
        if (projectDetail == null) {
            return null;
        }
        List<FollowUpRecord> records = customerMapper.queryFollowUpRecordsByProjectId(projectId);
        projectDetail.setFollowUpRecords(records);
        List<Support> supports = projectMapper.querySupportsByProjectId(projectId);
        projectDetail.setProjectSupports(supports);
        return projectDetail;
    }
}
