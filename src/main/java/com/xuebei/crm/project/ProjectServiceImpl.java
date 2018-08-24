package com.xuebei.crm.project;

import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectMapper projectMapper;

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
}
