package com.xuebei.crm.project;

import com.xuebei.crm.company.CompanyUser;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.utils.UUIDGenerator;
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
    public void addContract(Contract contract) {
        projectMapper.insertContract(contract);
    }

    @Override
    public Integer addRefund(Refund refund) {
        return projectMapper.insertRefund(refund);
    }

    @Override
    public Integer addProjectStart(ProjectStart projectStart) {
        return projectMapper.insertProjectStart(projectStart);
    }

    @Override
    public void updateProjectStart(ProjectStart projectStart) {
        projectMapper.updateProjectStart(projectStart);
    }

    @Override
    public void updateContract(Contract contract) {
        projectMapper.updateContract(contract);
    }

    @Override
    public void updateRefund(Refund refund) {
        projectMapper.updateRefund(refund);
    }

    @Override
    public void refuseProject(Integer projectId) {
        projectMapper.refuseProject(projectId);
    }

    @Override
    public void passProject(Integer projectId) {
        projectMapper.passProject(projectId);
    }

    @Override
    public void assignLeader(Integer projectId, String leader) {
        projectMapper.assignLeader(projectId, leader);
    }

    @Override
    public void startProject(ProjectStart projectStart) {

        if (projectStart == null)
            return;

        Contract contract = projectStart.getContract();
        if (contract != null){
            if (contract.getContractId() != null && !contract.getContractId().equals("") ){
                updateContract(contract);
            }else{
                String contractId = UUIDGenerator.genUUID();
                contract.setProjectId(projectStart.getProjectId());
                contract.setContractId(contractId);
                addContract(contract);
            }
        }
        List<Refund> refunds = projectStart.getRefunds();
        if (refunds != null){
            for (Refund refund : refunds){
                if(refund.getRefundId() != null && refund.getRefundId() != 0 ){
                    updateRefund(refund);
                }else{
                    refund.setProjectId(projectStart.getProjectId());
                    addRefund(refund);
                }
            }
        }

        if (projectStart.getId() != null && projectStart.getId() != 0){
            updateProjectStart(projectStart);
        }else {
            addProjectStart(projectStart);
        }
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


    @Override
    public List<ProjectDetail> queryMissionUn(String userId,boolean admin,List<String> subUserId){
        List<ProjectDetail> projectDetail = projectMapper.queryMissionUn(userId,admin,subUserId);
        if (projectDetail == null) {
            return null;
        }
        return projectDetail;
    }

    @Override
    public List<ProjectDetail> queryMission(String userId,boolean admin,List<String> subUserId) {
        List<ProjectDetail> projectDetail = projectMapper.queryMission(userId,admin,subUserId);
        if (projectDetail == null) {
            return null;
        }
        return projectDetail;
    }

    @Override
    public ProjectStart getProjectStart(Integer projectId, String userId) {
        return projectMapper.getProjectStart(projectId, userId);
    }

    @Override
    public Contract getContract(Integer projectId) {
        return projectMapper.getContract(projectId);
    }

    @Override
    public List<Refund> getRefunds(Integer projectId) {
        return projectMapper.getRefunds(projectId);
    }

    @Override
    public String queryOpportunityNameByOpportunityId(Integer projectId) {
        return projectMapper.queryOpportunityNameByOpportunityId(projectId);
    }

    @Override
    public ProjectDetail queryMissionDetail(Integer supportId) {
        return projectMapper.queryMissionDetail(supportId);
    }

    @Override
    public List<Support> querySupportInformation(Integer supportId) {
        return projectMapper.querySupportInformation(supportId);
    }

    @Override
    public void insertProgressInform(Integer supportId, String userId, String progress) {
        projectMapper.insertProgressInform(supportId,userId,progress);
    }

    @Override
    public void updateSupportProgress(String userId,Integer supportId, Integer progress) {
        projectMapper.updateSupportProgress(userId,supportId,progress);
    }

    @Override
    public List<CompanyUser> queryCompanyUser(String userId,String keyword) {
        return projectMapper.queryCompanyUser(userId,keyword);
    }

    @Override
    public void setSupportLeader(String userId,Integer supportId, String leaderId) {
        projectMapper.setSupportLeader(userId,supportId,leaderId);
    }

    @Override
    public void modifyProject(Opportunity opportunity) {
        projectMapper.modifyProject(opportunity);
    }
}
