package com.xuebei.crm.department;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.*;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/8/16.
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private MemberService memberService;

    /**
     * 组织机构列表中显示我申请过的机构
     *
     * @param customerId
     * @param userId
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Department> departmentList(String customerId, String userId) {

        List<Department> departmentList = deptMapper.searchDepts(customerId, userId);
        //设置圈地状态
        setEnclosureStatus(departmentList);
        //添加联系人和三级机构
        setSubDeptAndContacts(departmentList);
        //按圈地状态分类dept
        EnumMap<EnclosureStatusEnum,List> deptMap = getDeptMap(departmentList);

        //下属人员
        List<Member> subMemberList = memberService.searchSubMemberList(userId);
        for(Member member:subMemberList){
            //下属圈地
            List<Department> subMyDepts = deptMapper.searchMyDepts(customerId,member.getMemberId());
            setEnclosureStatus(subMyDepts);
            setSubDeptAndContacts(subMyDepts);
            for(Department department:subMyDepts){
                department.setApplyName(member.getMemberName());
            }

            //下属申请
            List<Department> subApplyingDepts = deptMapper.searchApplyingDepts(customerId,member.getMemberId());
            setEnclosureStatus(subApplyingDepts);
            setSubDeptAndContacts(subApplyingDepts);
            for (Department department:subApplyingDepts){
                department.setApplyName(member.getMemberName());
            }

            deptMap.get(EnclosureStatusEnum.MINE).addAll(subMyDepts);
            deptMap.get(EnclosureStatusEnum.APPLYING).addAll(subApplyingDepts);
        }

        List<Department> rltList = new ArrayList<>();
        rltList.addAll(deptMap.get(EnclosureStatusEnum.MINE));
        rltList.addAll(deptMap.get(EnclosureStatusEnum.APPLYING));
        rltList.addAll(deptMap.get(EnclosureStatusEnum.ENCLOSURE));
        rltList.addAll(deptMap.get(EnclosureStatusEnum.NORMAL));
        return rltList;

    }

    /**
     * 添加二级机构页面中显示我的二级机构
     *
     * @param customerId
     * @param userId
     * @return
     */
    @Override
    public List<Department> myDepartmentList(String customerId, String userId) {
        List<Department> myDepartmentList = deptMapper.searchMyDepts(customerId, userId);
        setEnclosureStatus(myDepartmentList);
        setSubDeptAndContacts(myDepartmentList);
        List<Member> subMemberList = memberService.searchSubMemberList(userId);
        for(Member member:subMemberList){
            List<Department> subMyDepts = deptMapper.searchMyDepts(customerId,member.getMemberId());
            setEnclosureStatus(subMyDepts);
            setSubDeptAndContacts(subMyDepts);
            myDepartmentList.addAll(subMyDepts);
        }
        return myDepartmentList;
    }

    private EnumMap<EnclosureStatusEnum, List> getDeptMap(List<Department> departmentList) {
        List<Department> mineDept = new ArrayList<>();
        List<Department> applyingDept = new ArrayList<>();
        List<Department> enclosureDept = new ArrayList<>();
        List<Department> normalDept = new ArrayList<>();
        for(Department department:departmentList){
            switch (department.getEnclosureStatus()){
                case MINE:
                    mineDept.add(department);
                    break;
                case APPLYING:
                    applyingDept.add(department);
                    break;
                case ENCLOSURE:
                    enclosureDept.add(department);
                    break;
                case NORMAL:
                    normalDept.add(department);
                    break;
                default:
                    break;
            }
        }
        EnumMap<EnclosureStatusEnum,List> deptMap = new EnumMap<>(EnclosureStatusEnum.class);
        deptMap.put(EnclosureStatusEnum.MINE,mineDept);
        deptMap.put(EnclosureStatusEnum.APPLYING,applyingDept);
        deptMap.put(EnclosureStatusEnum.ENCLOSURE,enclosureDept);
        deptMap.put(EnclosureStatusEnum.NORMAL,normalDept);

        return deptMap;
    }

    private void setEnclosureStatus(List<Department> deptList) {
        for (Department department : deptList) {
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

    private void checkOpenSeaWarning(Department department) {
        EnclosureApply enclosureApply = department.getEnclosureApply();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diffMillis = 0;
        int diffDays = 0;
        int diffHours = 0;
        try {
            Date endTime = dateFormat.parse(enclosureApply.getEndTime());
            diffMillis = (endTime.getTime() - System.currentTimeMillis());
            diffDays = (int) (diffMillis / (1000 * 3600 * 24));
            diffHours = (int) (diffMillis / (1000 * 3600) - diffDays * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (diffMillis <= 1000*3600*24*7 && diffMillis>=0) {
            String delayApplyStatus = deptMapper.delayApplyStatus(department.getDeptId());
            if (delayApplyStatus.equals("") || delayApplyStatus==null) {
                OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                openSeaWarning.setDeptId(department.getDeptId());
                openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
                openSeaWarning.setLastTimeFollow(enclosureApply.getUpdateTime());
                openSeaWarning.setDeptName(department.getDeptName());
                openSeaWarning.setLeftDays(String.valueOf(diffDays));
                openSeaWarning.setLeftHours(String.valueOf(diffHours));
                openSeaWarning.setDelayApplied(false);
                department.setOpenSeaWarning(openSeaWarning);
            } else {
                switch (delayApplyStatus) {
                    case "APPLYING":
                    case "REJECTED":
                        OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                        openSeaWarning.setDeptId(department.getDeptId());
                        openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
                        openSeaWarning.setLastTimeFollow(enclosureApply.getUpdateTime());
                        openSeaWarning.setDeptName(department.getDeptName());
                        openSeaWarning.setLeftDays(String.valueOf(diffDays));
                        openSeaWarning.setLeftHours(String.valueOf(diffHours));
                        openSeaWarning.setDelayApplied(true);
                        department.setOpenSeaWarning(openSeaWarning);
                        break;
                    case "PERMITTED":
                    default:
                        break;
                }
            }
        }
        if(diffMillis<0){
            department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
            deptMapper.setMyDeptExpired(department.getDeptId());
        }
    }

    private void setSubDeptAndContacts(List<Department> departmentList) {
        //二级机构
        for (Department department : departmentList) {
            if (department.getEnclosureStatus() == EnclosureStatusEnum.MINE) {
                addSubDeptAndContacts(department);
            }
        }
    }

    private void addSubDeptAndContacts(Department department) {
        //二级机构联系人总人数
        Integer contactsNum = 0;
        String deptId = department.getDeptId();
        //三级机构
        List<Department> subDeptList = deptMapper.searchSubDepts(deptId);
        if (subDeptList != null && !subDeptList.isEmpty()) {
            for (Department subDepartment : subDeptList) {
                String subDeptId = subDepartment.getDeptId();
                //三级机构联系人
                List<Contacts> subContactsList = deptMapper.searchContacts(subDeptId);
                if (subContactsList != null && !subContactsList.isEmpty()) {
                    subDepartment.setContactsList(subContactsList);
                    //三级机构添加联系人总人数
                    subDepartment.setContactNumber(subContactsList.size());
                    contactsNum += subContactsList.size();
                }
            }
            department.setDepartmentList(subDeptList);
        }
        //二级机构联系人
        List<Contacts> contactsList = deptMapper.searchContacts(deptId);
        if (contactsList != null && !contactsList.isEmpty()) {
            department.setContactsList(contactsList);
            contactsNum += contactsList.size();
        }
        //二级机构添加联系人总人数
        department.setContactNumber(contactsNum);
    }

    /**
     * 添加二级机构前的检查
     *
     * @param deptName   编辑的二级机构
     * @param customerId
     * @param userId
     * @return 被我申请过，被别人申请过，没人申请过
     */
    @Override
    public WarningBeforeCreateEnum warningBeforeCreate(String deptName, String customerId, String userId) {
        List<Department> myDepts = deptMapper.searchMyAppliedDepts(customerId, userId);
        List<Department> othersDepts = deptMapper.searchOthersAppliedDepts(customerId, userId);
        for (Department department : myDepts) {
            if (department.getDeptName().equals(deptName)) {
                return WarningBeforeCreateEnum.APPLY_BY_ME;
            }
        }
        for (Department department : othersDepts) {
            if (department.getDeptName().equals(deptName)) {
                return WarningBeforeCreateEnum.APPLY_BY_OTHERS;
            }
        }
        return WarningBeforeCreateEnum.NO_ONE_APPLY;
    }

    @Override
    public void enclosureApply(String deptId, String userId, String reasons) {
        deptMapper.applyDepartment(deptId, userId, reasons);
    }

    @Override
    public void enclosureDelayApply(String deptId, String userId, String reasons) {
        deptMapper.delayApplyDepartment(deptId, userId, reasons);
    }

}
