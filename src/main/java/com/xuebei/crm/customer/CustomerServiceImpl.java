package com.xuebei.crm.customer;

import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
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
        if (customerMapper.isTopDepartNameExist(department.getCustomer().getCustomerId(), department.getDeptName())) {
            throw new DepartmentNameDuplicatedException("二级学院名已存在，请重新输入");
        }

        customerMapper.insertTopDepartment(department);
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
            department.addContact();
        }
        List<Department> rltList = new ArrayList<>();
        for (Department department: departmentList) {
            if (department.getParent() == null) {
                rltList.add(department);
            } else {
                String prtId = department.getParent().getDeptId();
                Department prtDept = departmentMap.get(prtId);
                prtDept.addSubDept(department);
            }
        }
        for(Department department:rltList){

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
            //从未拜访
            int diffDays;
            int followTimes = 0;
            if(visitList.isEmpty()){
                //比较申请圈地时间与当前时间的间隔
                 diffDays = diffDays(enclosureApply.getStartTime());
            }
            //拜访过
            else {
                Visit visit = visitList.get(0);
                //比较最新的一次拜访时间与当前时间的间隔
                diffDays = diffDays(visit.getVisitTime());
                followTimes = visitList.size();
            }
            //申请圈地后未到83天
            if(diffDays<83) department.setEnclosureStatus(EnclosureStatusEnum.MINE);
                //距离申请圈地后83天 90天以内
            else if (diffDays>=83 && diffDays<90){
                OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                openSeaWarning.setFollowTimes(followTimes);
                openSeaWarning.setLastTimeFollow(enclosureApply.getStartTime());
                openSeaWarning.setDepartment(department);
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
    public List<String> searchSchool(String keyword){
        return customerMapper.searchSchool(keyword);
    }

}
