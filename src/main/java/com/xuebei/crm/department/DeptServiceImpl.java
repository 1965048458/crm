package com.xuebei.crm.department;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/8/16.
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Department> departmentList(String customerId, String userId) {
        List<Department> departmentList = deptMapper.searchDepts(customerId, userId);
        //设置圈地状态
        setEnclosureStatus(departmentList);
        //添加联系人
        setSubDeptAndContacts(departmentList);
        return departmentList;
    }

    @Override
    public List<Department> myDepartmentList(String customerId, String userId) {
        List<Department> myDepartmentList = deptMapper.searchMyDepts(customerId,userId);
        setEnclosureStatus(myDepartmentList);
        setSubDeptAndContacts(myDepartmentList);
        return myDepartmentList;
    }

    private void setEnclosureStatus(List<Department> deptList) {
        for(Department department:deptList){
            switch (department.getStatusCd()) {
                case "PERMITTED":
                    department.setEnclosureStatus(EnclosureStatusEnum.MINE);
                    checkOpenSeaWarning(department);
                    break;
                case "APPLYING":
                    department.setEnclosureStatus(EnclosureStatusEnum.APPLYING);
                    break;
                case "EXPIRED":
                case "REJECTED":
                    if (department.getApplyByOthers() == 0) {
                        //未圈
                        department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
                    } else {
                        department.setEnclosureStatus(EnclosureStatusEnum.ENCLOSURE);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void checkOpenSeaWarning(Department department){
        EnclosureApply enclosureApply = department.getEnclosureApply();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int diffDays = 0;
        int diffHours = 0;
        try{
            Date endTime = dateFormat.parse(enclosureApply.getEndTime());
            long diffMillis = (endTime.getTime() - System.currentTimeMillis());
            diffDays = (int)diffMillis/(1000*3600*24);
            diffHours = (int)diffMillis/(1000*3600) - diffDays*24;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(diffDays<7){
            OpenSeaWarning openSeaWarning = new OpenSeaWarning();
            openSeaWarning.setDeptId(department.getDeptId());
            openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
            openSeaWarning.setLastTimeFollow(enclosureApply.getUpdateTime());
            openSeaWarning.setDeptName(department.getDeptName());
            openSeaWarning.setLeftDays(String.valueOf(diffDays));
            openSeaWarning.setLeftHours(String.valueOf(diffHours));
            department.setOpenSeaWarning(openSeaWarning);
        }
    }
    private void setSubDeptAndContacts(List<Department> departmentList) {
        //二级机构
        for(Department department:departmentList){
            //二级机构联系人总人数
            Integer contactsNum = 0;
            String deptId = department.getDeptId();
            //三级机构
            List<Department> subDeptList = deptMapper.searchSubDepts(deptId);
            if(subDeptList != null && !subDeptList.isEmpty()){
                department.setDepartmentList(subDeptList);
                for(Department subDepartment:subDeptList){
                    String subDeptId = subDepartment.getDeptId();
                    //三级机构联系人
                    List<Contacts> subContactsList = deptMapper.searchContacts(subDeptId);
                    if(subContactsList != null && !subContactsList.isEmpty()){
                        subDepartment.setContactsList(subContactsList);
                        //三级机构添加联系人总人数
                        subDepartment.setContactNumber(subContactsList.size());
                        contactsNum+=subContactsList.size();
                    }
                }
            }
            //二级机构联系人
            List<Contacts> contactsList = deptMapper.searchContacts(deptId);
            if(contactsList !=null && !contactsList.isEmpty()){
                department.setContactsList(contactsList);
                contactsNum+=contactsList.size();
            }
            //二级机构添加联系人总人数
            department.setContactNumber(contactsNum);
        }
    }

    @Override
    public String warningBeforeCreate(String deptName,String customerId,String userId) {
        String appliedByMeWarning = "您已经申请过或已经被您圈走了";
        String appliedByOthersWarning = "该部门已经被其他人圈走或申请";
        String editPermitted = "该部门还未被申请过，可以申请";
        List<Department> myDepts = deptMapper.searchMyAppliedDepts(customerId, userId);
        List<Department> othersDepts = deptMapper.searchOthersAppliedDepts(customerId, userId);
        for(Department department:myDepts){
            if(department.getDeptName().equals(deptName)){
                return appliedByMeWarning;
            }
        }
        for(Department department:othersDepts){
            if(department.getDeptName().equals(deptName)){
                return appliedByOthersWarning;
            }
        }
        return editPermitted;
    }

}
