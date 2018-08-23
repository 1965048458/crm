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
    public void updateContract(Contract contract) {
        projectMapper.updateContract(contract);
    }

    @Override
    public void updateRefund(Refund refund) {
        projectMapper.updateRefund(refund);
    }

    @Override
    public void startProject(ProjectStart projectStart) {
        Contract contract = projectStart.getContract();
        if (contract != null){
            contract.setProjectId(projectStart.getProjectId());
            if (contract.getContractId() != null){
                updateContract(contract);
            }else{
                String contractId = UUIDGenerator.genUUID();
                contract.setContractId(contractId);
                addContract(contract);
            }
        }
        List<Refund> refunds = projectStart.getRefunds();
        if (refunds != null){
            for (Refund refund : refunds){
                refund.setProjectId(projectStart.getProjectId());
                if(refund.getRefundId() != null){
                    updateRefund(refund);
                }else{
                    addRefund(refund);
                }
            }
        }
        //todo insert apply table
    }

    @Override
    public List<Project> searchProject(ProjectSearchParam param) {

        List<Project> projectList = projectMapper.searchProject(param);
        return projectList;
    }

    @Override
    public String queryOpportunityNameByOpportunityId(Integer projectId) {
        return projectMapper.queryOpportunityNameByOpportunityId(projectId);
    }
}
