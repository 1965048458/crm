package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public Boolean isUserHasCustomer(String userId, String customerId) {
        return customerMapper.isUserHasCustomer(userId, customerId);
    }

    /**
     * 增加二级学院
     * @param department
     * @throws DepartmentNameDuplicatedException
     */
    public void addTopDepartment(Department department) throws DepartmentNameDuplicatedException {
        if (customerMapper.isDepartNameExist(department.getCustomer().getCustomerId(), department.getDeptName())) {
            throw new DepartmentNameDuplicatedException("二级学院名已存在，请重新输入");
        }

        customerMapper.insertDepartment(department);
    }

    public void addSubDepartment(String parentDeptId, String deptName) {
        Department prtDept = customerMapper.queryDepartmentById(parentDeptId);

        Department dept = new Department();
        dept.setDeptId(UUIDGenerator.genUUID());
        dept.setDeptName(deptName);
        dept.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
        dept.setCustomer(prtDept.getCustomer());
        dept.setParent(prtDept);

        customerMapper.insertDepartment(dept);
    }

    /**
     * 新建学校
     */
    public void newSchool( String customer_id,String name,String schoolType,String profile,
                           String website,String creator_id,String create_ts,String updater_id,String update_ts){
        customerMapper.newSchool(customer_id, name, schoolType, profile,
                                 website, creator_id, create_ts, updater_id, update_ts);
    }

    @Override
    public List<Customer> queryCustomerInfo(String searchWord) {

        List<Customer> customerList = customerMapper.queryCustomerInfo(searchWord);

        return customerList;
    }

    @Override
    public List<Department> queryDepartment(String customerId,String userId){
        List<Department> departmentList = customerMapper.queryDepartment(customerId);
        List<Contacts> contactsList = customerMapper.queryContacts(customerId);
        for (Department department:departmentList){
            setEnclosureStatus(userId, department);
        }
        Map<String, Department> departmentMap = new HashMap<>();
        for (Department department : departmentList) {
            departmentMap.put(department.getDeptId(), department);
        }
        for (Contacts contacts: contactsList ){
            String departmentId = contacts.getDepartmentId();
            Department department = departmentMap.get(departmentId);
            department.addSubContact(contacts);
            department.addContact(1);
        }
        List<Department> rltList = new ArrayList<>();
        for (Department department: departmentList) {
            if (department.getParent() == null) {
                rltList.add(department);
            } else {
                String prtId = department.getParent().getDeptId();
                Department prtDept = departmentMap.get(prtId);
                prtDept.addSubDept(department);
                prtDept.addContact(department.getContactNumber());
            }
        }
        //TODO 判断哪些部门可以显示，哪些部门或联系人可以搜索
        for(Department department:rltList){
            if(department.getEnclosureStatus().equals(EnclosureStatusEnum.MINE) ||
                    department.getEnclosureStatus().equals(EnclosureStatusEnum.NORMAL)){
                department.setCanUnFold(true);
            }
        }
        return rltList;
    }

    private void setEnclosureStatus(String userId, Department department) {
        //该部门收到的最新圈地请求
        EnclosureApply enclosureApply = customerMapper.queryNewEnclosureApply(department.getDeptId());
        if(enclosureApply==null){
            return;
        }
        //是我的圈地
        if(enclosureApply.getUserId().equals(userId)){
            //获取圈地开始后所有的拜访记录
            List<Visit> visitList= customerMapper.queryMyVisit(department.getDeptId(),enclosureApply.getStartTime(),userId);
            int diffDays;
            int followTimes = 0;
            String lastTime;
            //从未拜访
            if(visitList.isEmpty()){
                //比较申请圈地时间与当前时间的间隔
                 diffDays = diffDays(enclosureApply.getStartTime());
                 lastTime = enclosureApply.getStartTime();
            }
            //拜访过
            else {
                Visit visit = visitList.get(0);
                //比较最新的一次拜访时间与当前时间的间隔
                diffDays = diffDays(visit.getVisitTime());
                followTimes = visitList.size();
                lastTime = visit.getVisitTime();

            }
            //申请圈地后未到83天
            if(diffDays<83) department.setEnclosureStatus(EnclosureStatusEnum.MINE);
                //距离申请圈地后83天 90天以内
            else if (diffDays>=83 && diffDays<90){
                OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
                openSeaWarning.setFollowTimes(followTimes);
                openSeaWarning.setLastTimeFollow(lastTime);
                openSeaWarning.setDeptName(department.getDeptName());
                openSeaWarning.setDeptId(department.getDeptId());
                department.setEnclosureStatus(EnclosureStatusEnum.MINE);
            }
            //距离申请圈地后超过90天
            else {
                department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
            }
        }
        //不是我圈的地
        else {
            Visit visit = customerMapper.queryElseVisit(department.getDeptId(),enclosureApply.getStartTime(),userId);
            int diffDays;
            if(visit==null){
                diffDays = diffDays(enclosureApply.getStartTime());
            }else {
                diffDays = diffDays(visit.getVisitTime());
            }
            if(diffDays<90) department.setEnclosureStatus(EnclosureStatusEnum.ENCLOSURE);
            else department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
        }
    }

    private static int diffDays(String visitTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int days = Integer.MAX_VALUE;
        try{
            Date visitDate = format.parse(visitTime);
            days = (int) ((System.currentTimeMillis()- visitDate.getTime()) / (1000*3600*24));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    @Override
    public List querySearchList(List<Department> deptList){
        List<String> searchList = new ArrayList<>();
        querySearchList(deptList,searchList);
        return searchList;
    }

    @Override
    public void enclosureDelayApply(String deptId){
        EnclosureApply enclosureApply = customerMapper.queryNewEnclosureApply(deptId);
        customerMapper.insertEnclosureDelayApply(enclosureApply);
    }
    private void querySearchList(List<Department> deptList, List<String > searchList){
        for(Department department:deptList){
            if(department.getEnclosureStatus().equals(EnclosureStatusEnum.MINE) ||
                    department.getEnclosureStatus().equals(EnclosureStatusEnum.NORMAL)){
                searchList.add(department.getDeptName());
                if(!department.getContactsList().isEmpty() || department.getContactsList()!=null){
                    for(Contacts contacts:department.getContactsList()){
                        searchList.add(contacts.getRealName());
                    }
                }
                if(!department.getDepartmentList().isEmpty() || department.getDepartmentList()!=null){
                    querySearchList(department.getDepartmentList(), searchList);
                }
            }
        }
    }
    @Override
    public List<String> searchSchool(String keyword){
        return customerMapper.searchSchool(keyword);
    }


    @Override
    public List<Customer> getMyCustomers(String userId){
        return customerMapper.getMyCustomers(userId);
    }

    /**
     * 检查机构名是否重复
     * 检查规则：同一学校（企业）下不能有重名机构
     * @param customerId 客户ID
     * @param deptName 新增机构名
     */
    public Boolean isDepartmentNameDuplicated(String customerId, String deptName) {
        return customerMapper.isDepartNameExist(customerId, deptName);
    }

    /**
     * 检查子机构名是否重复
     * 创建子机构时，参数只有父机构的ID；由于同一学校（企业）下都不能有重名机构，因此检查机构名重复调用相同的接口
     */
    public Boolean isSubDepartmentNameDuplicated(String parentDeptId, String deptName) {
        Department parentDept = customerMapper.queryDepartmentById(parentDeptId);
        return customerMapper.isDepartNameExist(parentDept.getCustomer().getCustomerId(), deptName);
    }
}
