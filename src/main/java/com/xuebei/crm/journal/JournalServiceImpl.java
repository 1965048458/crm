package com.xuebei.crm.journal;

import com.xuebei.crm.customer.BigCustomer;
import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.ContactsDept;
import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.EnclosureApply;
import com.xuebei.crm.customer.EnclosureStatusEnum;
import com.xuebei.crm.customer.OpenSeaWarning;
import com.xuebei.crm.department.DeptMapper;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberMapper;
import com.xuebei.crm.member.MemberService;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.project.Project;
import com.xuebei.crm.project.ProjectMapper;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.Hoilday;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private MemberService memberService;

    /**
     * 创建一个新日志
     * 注意：调用该方法前，需要将 session 中 userId 装载入 journal 对象中(Not checked)
     * @param journal
     */
    @Override
    public void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException {
        checkBasicInfo(journal);
        checkVisitRecords(journal.getVisitRecords());

        List<Journal> drafts = journalMapper.findJournalDraft(journal.getUserId());
        if (drafts != null) {
            for (Journal draft: drafts)
                journalMapper.deleteJournal(draft.getUserId(), draft.getJournalId());
        }

        journalMapper.createJournal(journal);
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords());
    }

    @Override
    public void modifyJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException {
        checkBasicInfo(journal);
        Journal oldJournal = journalMapper.queryJournalById(journal.getJournalId());
        if (oldJournal == null || !oldJournal.getUserId().equals(journal.getUserId())) {
            throw new AuthenticationException("用户不拥有此日志");
        } else if (oldJournal.getHasSubmitted()) {
            throw new AuthenticationException("日志已提交");
        }
        checkVisitRecords(journal.getVisitRecords());
        journalMapper.updateJournal(journal);
        deleteVisitRecords(journal.getJournalId());
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords());
    }

    @Override
    public void deleteJournalById(String userId, String journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId)) {
            throw new AuthenticationException("用户不拥有此日志");
        }

        deleteVisitRecords(journalId);
        journalMapper.deleteJournal(userId, journalId);
    }

    @Override
    public  List<ManageJournal> getJournalState(String userId)
    {
           Date monthStart=new Date();
           monthStart.setHours(8);
           monthStart.setMinutes(30);
           monthStart.setSeconds(0);
           int date=monthStart.getDate();
           if (date<4)
           {
               monthStart.setDate(date-4);
           }
           else
           {
               monthStart.setDate(1);
           }

           List<ManageJournal> manageJournals=journalMapper.getJournalState(userId,monthStart,null);

               Date tmp=new Date();
               //tmp.setDate(tmp.getDate()+1);
               tmp.setHours(9);
               ManageJournal tmpM=new ManageJournal();
               tmpM.setTagertDate(tmp);
               manageJournals.add(tmpM);
          manageJournals.sort((journal1, journal2)-> journal1.getTagertDate().after(journal2.getTagertDate())?1:-1);
           List<ManageJournal> manageJournals2=new ArrayList<ManageJournal>();
           ManageJournal recordJournal=null;
           //monthStart.setDate(monthStart.getDate()+1);
           for (ManageJournal manageJournal :manageJournals)
           {
               if (manageJournal.getTagertDate().before(new Date(monthStart.getTime())))
               {
                      recordJournal=manageJournal;
               }
               else
               {
                   if(recordJournal!=null&&recordJournal.getRepairDate()!=null) {
                       recordJournal.setShowDate();
                       manageJournals2.add(recordJournal);
                   }
                   recordJournal = manageJournal;

                   long subDay=(recordJournal.getTagertDate().getTime()-monthStart.getTime())/86400000;
                   monthStart.setDate(monthStart.getDate()+1);

                   for(int i=0;i<subDay;i++)
                   {
                       Date tmpHoilday=new Date(monthStart.getTime()-86400000);
                       try {
                           if (!Hoilday.isHoliday(tmpHoilday)) {
                               ManageJournal tmpManageJ = new ManageJournal();
                               tmpManageJ.setShowDate(new Date(monthStart.getTime()-86400000));
                               manageJournals2.add(tmpManageJ);
                           }
                       }
                       catch (Exception e)
                       {

                       }
                       monthStart.setDate(monthStart.getDate() + 1);
                   }
               }
           }
           return  manageJournals2;
    }


    @Override
    public List<FollowJournal> getJournalFollow(String userId) {
        List<String> userIDs = journalMapper.queryFollowUserId(userId);
        List<FollowJournal> followJournals=new ArrayList<>();
        for (String user : userIDs) {
            Date monthStart = new Date();
            monthStart.setHours(8);
            monthStart.setMinutes(30);
            monthStart.setSeconds(0);
            monthStart.setDate(1);
            List<ManageJournal> manageJournals=journalMapper.getJournalState(user,monthStart,null);
            Date tmp=new Date();
            //tmp.setDate(tmp.getDate()+1);
            tmp.setHours(9);

            ManageJournal tmpM=new ManageJournal();
            tmpM.setTagertDate(tmp);
            manageJournals.add(tmpM);
            manageJournals.sort((journal1, journal2)-> journal1.getTagertDate().after(journal2.getTagertDate())?1:-1);
            List<ManageJournal> manageJournals2=new ArrayList<ManageJournal>();
            ManageJournal recordJournal=null;

            for (ManageJournal manageJournal :manageJournals)
            {
                if (manageJournal.getTagertDate().before(monthStart))
                {
                    recordJournal=manageJournal;
                }
                else
                {
                    if(recordJournal!=null&&recordJournal.getRepairDate()!=null) {
                        manageJournals2.add(recordJournal);

                    }
                    recordJournal = manageJournal;
                    long subDay2=(recordJournal.getTagertDate().getTime()-monthStart.getTime())/86400000;
                    monthStart.setDate(monthStart.getDate() + 1);
                    for(int i=0;i<subDay2;i++)
                    {
                        Date tmpHoilday=new Date(monthStart.getTime()-86400000);
                        try {
                            if (!Hoilday.isHoliday(tmpHoilday)) {
                                ManageJournal tmpManageJ = new ManageJournal();
                                tmpManageJ.setShowDate(new Date(monthStart.getTime()-86400000));
                                manageJournals2.add(tmpManageJ);

                            }
                        }
                        catch (Exception e)
                        {
                                   System.out.println("error4");
                                   break;
                        }
                        monthStart.setDate(monthStart.getDate()+1);
                    }

                }
            }
            int repairCount=0;
            int loseCount=0;
            for (ManageJournal manageJournal:manageJournals2)
            {
                if (manageJournal.getRepairDate()!=null)
                {
                    repairCount++;
                }
                else if (manageJournal.getTagertDate()==null)
                {
                    loseCount++;
                }
            }
           followJournals.add(new FollowJournal(journalMapper.queryNameById(user),repairCount,loseCount,30,100));
        }
        return followJournals;
    }
    public List<FollowJournal> getJournalFollow(String userId,int index) {
        List<String> userIDs = journalMapper.queryFollowUserId(userId);
        List<FollowJournal> followJournals=new ArrayList<>();
        for (String user : userIDs) {
            Date monthStart = new Date();
            monthStart.setHours(8);
            monthStart.setMinutes(30);
            monthStart.setSeconds(0);
            monthStart.setDate(1);
            Date monthEnd=new Date(monthStart.getTime());
            monthStart.setMonth(monthStart.getMonth()-index);
            monthEnd.setMonth(monthStart.getMonth()+1);
            List<ManageJournal> manageJournals=journalMapper.getJournalState(user,monthStart,monthEnd);
            monthEnd.setDate(1);
            monthEnd.setHours(9);

            ManageJournal tmpM=new ManageJournal();
            tmpM.setTagertDate(monthEnd);
            manageJournals.add(tmpM);
            manageJournals.sort((journal1, journal2)-> journal1.getTagertDate().after(journal2.getTagertDate())?1:-1);
            List<ManageJournal> manageJournals2=new ArrayList<ManageJournal>();
            ManageJournal recordJournal=null;
            //monthStart.setDate(monthStart.getDate()+1);
            for (ManageJournal manageJournal :manageJournals)
            {
                if (manageJournal.getTagertDate().before(monthStart))
                {
                    recordJournal=manageJournal;
                }
                else
                {
                    if(recordJournal!=null&&recordJournal.getRepairDate()!=null) {
                        manageJournals2.add(recordJournal);
                    }
                    recordJournal = manageJournal;
                    long subDay2=(recordJournal.getTagertDate().getTime()-monthStart.getTime())/86400000;
                    monthStart.setDate(monthStart.getDate()+1);

                    for(int i=0;i<subDay2;i++)
                    {
                        Date tmpHoilday=new Date(monthStart.getTime()-86400000);
                        try {
                            if (!Hoilday.isHoliday(tmpHoilday)) {
                                ManageJournal tmpManageJ = new ManageJournal();
                                tmpManageJ.setShowDate(new Date(monthStart.getTime()-86400000));

                                manageJournals2.add(tmpManageJ);

                            }
                        }
                        catch (Exception e)
                        {
                            System.out.println("error2");
                            break;
                        }
                        monthStart.setDate(monthStart.getDate() + 1);
                    }

                }
            }
            int repairCount=0;
            int loseCount=0;
            for (ManageJournal manageJournal:manageJournals2)
            {
                if (manageJournal.getRepairDate()!=null)
                {
                    repairCount++;
                }
                else if (manageJournal.getTagertDate()==null)
                {
                    loseCount++;
                }
            }
            followJournals.add(new FollowJournal(journalMapper.queryNameById(user),repairCount,loseCount,30,100));
        }
        return followJournals;
    }

    @Override
    public Journal queryJournalById(String userId, String journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId))
            throw new AuthenticationException("用户不拥有此日志");

        Journal journal = journalMapper.queryJournalById(journalId);
        journal.setVisitRecords(journalMapper.queryVisitLogs(journalId));
        for (VisitRecord visitRecord: journal.getVisitRecords()) {
            visitRecord.setChosenContacts(journalMapper.queryVisitContacts(visitRecord.getVisitId()));
        }

        return journal;
    }

    /**
     * 检查 journal 对象中 type summary plan hasSubmitted 成员是否为空，若为空则抛出异常
     * @param journal 被检查的 journal 对象
     * @throws InformationNotCompleteException 信息不完整
     */
    private void checkBasicInfo(Journal journal) throws InformationNotCompleteException {
        if (journal.getType() == null)
            throw new InformationNotCompleteException("journal.type 不能为空");
        if (journal.getSummary() == null)
            throw new InformationNotCompleteException("journal.summary 不能为空");
        if (journal.getPlan() == null)
            throw new InformationNotCompleteException("journal.plan 不能为空");
        if (journal.getHasSubmitted() == null)
            throw new InformationNotCompleteException("journal.hasSubmitted 不能为空");
    }

    /**
     * 检查访问记录对象中的 信息完整度 和 联系人权限
     * @param visitRecords 访问记录
     * @throws InformationNotCompleteException 信息不完整
     */
    private void checkVisitRecords(List<VisitRecord> visitRecords) throws InformationNotCompleteException {
        if (visitRecords == null)
            return;
        for (VisitRecord visitRecord: visitRecords) {
            if (visitRecord == null)
                throw new InformationNotCompleteException("visitRecord 不能为空");
            if (visitRecord.getVisitType() == null)
                throw new InformationNotCompleteException("visitType 不能为空");
            if (visitRecord.getVisitResult() == null)
                throw new InformationNotCompleteException("visitResult 不能为空");
//            if (visitRecord.getContactsIds() == null)
//                continue;
//            for (String contactsId: visitRecord.getContactsIds()) {
//                // TODO: contactsId 联系人是否为公司所有(权限)
//            }
        }
    }

    private void insertVisitRecords(String journalId, List<VisitRecord> visitRecords) {
        if (visitRecords == null)
            return;
        for (VisitRecord visitRecord: visitRecords) {
            visitRecord.setJournalId(journalId);
            journalMapper.insertVisitLog(visitRecord);
//            if (visitRecord.getContactsIds() == null)
//                continue;
            for (Contacts contacts: visitRecord.getChosenContacts()) {
                journalMapper.insertVisitContacts(visitRecord.getVisitId(), contacts.getContactsId());
            }
        }
    }

    private void deleteVisitRecords(String journalId) {
        List<VisitRecord> records = journalMapper.queryVisitLogs(journalId);
        for (VisitRecord record: records) {
            journalMapper.deleteVisitContacts(record.getVisitId());
        }
        journalMapper.deleteVisitLog(journalId);
    }

    public List<Journal> searchJournal2(String journalId)
    {
    	Journal myJournal = journalMapper.searchJournal(journalId);
    	String userName=myJournal.getUserId();
    	JournalSearchParam param=new JournalSearchParam();
    	param.setUserId(userName);
    	Date tmpDate=new Date(myJournal.getCreateTs().getTime());
    	if (tmpDate.getHours()<9&&tmpDate.getMinutes()<30) {
			tmpDate.setDate(tmpDate.getDate()-1);
		}
		tmpDate.setHours(8);
		tmpDate.setMinutes(30);
		tmpDate.setSeconds(0);
		Date tmpDate2=new Date(tmpDate.getTime());
		tmpDate2.setDate(tmpDate.getDate()+1);
		param.setStartTime(tmpDate);
		param.setEndTime(tmpDate2);
		List<Journal> testJ=journalMapper.searchMyJournal(param);
		return testJ;
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public List<Journal> searchJournal(JournalSearchParam param) {
        ///分解发送人字符串
        if (param.getSenderIds() != null && !param.getSenderIds().equals("")){
            param.setSdId(param.getSenderIds().trim().split(","));
        }

        List<Journal> myJournal = journalMapper.searchMyJournal(param);

        // 当未选择发送人，则找到所有下属作为筛选目标
        if (param.getSdId() == null || param.getSdId().length == 0) {
            Set<String> childs = getAllSubordinatesUserId(param.getUserId());
            String[] childsArray = new String[childs.size()];
            childs.toArray(childsArray);
            param.setSdId(childsArray);
        }

        // 查询下属的日志
        List<Journal> receivedJournal = journalMapper.searchReceivedJournal(param);

        List<Journal> allJournalList = new ArrayList<>();

        allJournalList.addAll(myJournal);
        allJournalList.addAll(receivedJournal);

        if (param.getIsMine() != null && param.getIsMine() == 1 ){
            allJournalList.removeAll(receivedJournal);
        }
        //按下属筛选时移除我的日志
        if (param.getSenderIds() != null && param.getSenderIds().length() != 0){
            allJournalList.removeAll(myJournal);
        }

        allJournalList.sort((journal1, journal2)-> journal1.getCreateTs().before(journal2.getCreateTs())?1:-1);
        //统计日志有多少人已读
        for (Journal jn : allJournalList  ) {
            jn.setVisitRecords(journalMapper.queryVisitLogs(jn.getJournalId()));
            for (VisitRecord visitRecord: jn.getVisitRecords()) {
                visitRecord.setChosenContacts(journalMapper.queryVisitContacts(visitRecord.getVisitId()));
                String opportunityid=projectMapper.queryOpportunityNameByOpportunityId(visitRecord.getOpportunityId());
                visitRecord.setOpportunityName(opportunityid);
                //System.out.println(projectMapper.queryOpportunity(visitRecord.getOpportunityId()));
                Opportunity ssaa=projectMapper.queryOpportunity(visitRecord.getOpportunityId());
                if (ssaa!=null) {
                    visitRecord.setOpportunity(projectMapper.queryOpportunity(visitRecord.getOpportunityId()));
                    visitRecord.getOpportunity().setTotalName("");
				}
                for (Contacts contacts: visitRecord.getChosenContacts()) {
                    ContactsDept contactsDept = customerMapper.queryContactsDept(contacts.getContactsId());
                    contacts.setTotalName(contactsDept.toString());
                }
            }
            List<User> journalRead = journalMapper.searchRead(jn.getJournalId());
            jn.setReadNum(journalRead.size());
            // 查询日志补丁
            jn.setJournalPatches(journalMapper.queryJournalPatch(jn.getJournalId()));
            // 跟新是否是自己
            jn.setIsMine(jn.getUserId().equals(param.getUserId()));
        }
        List<Journal> allJournalList2 = new ArrayList<>();
        Map<String, Date> reocrd=new HashMap<String, Date>();
        Map<String, Date> dateDawn=new HashMap<String,Date>();
        Map<String, Journal> tmpJ=new HashMap<String,Journal>();
        for(Journal jn:allJournalList)
        {
        	String playName=jn.getUserId();
        	if (!reocrd.containsKey(playName)) {
        		Date tmpDate=new Date(jn.getCreateTs().getTime());
        		reocrd.put(playName, jn.getCreateTs());
        		if (jn.getCreateTs().getHours()<8||(jn.getCreateTs().getHours()<9&&jn.getCreateTs().getMinutes()<30)) {
					tmpDate.setDate(tmpDate.getDate()-1);
				}
        		tmpDate.setHours(8);
        		tmpDate.setMinutes(30);
        		tmpDate.setSeconds(0);
        		dateDawn.put(playName, tmpDate);
        		tmpJ.put(playName, jn);
        		Date sss=new Date();
        		if (sss.getHours()<8||(sss.getHours()<9&&sss.getMinutes()<30)) {
        			sss.setDate(sss.getDate()-1);
				}
        		sss.setHours(8);
        		sss.setMinutes(30);
        		sss.setSeconds(0);
        		if (jn.getCreateTs().after(sss)) {
					jn.setIsToday(true);
				}
			}
        	else
        	{
        		Date tmpDate=jn.getCreateTs();
        		if (tmpDate.before(dateDawn.get(playName))) {
        			allJournalList2.add(tmpJ.get(playName));
	        		Date tmpDate2=new Date(jn.getCreateTs().getTime());
	        		reocrd.put(playName, jn.getCreateTs());
	        		if (jn.getCreateTs().getHours()<8||(jn.getCreateTs().getHours()<9&&jn.getCreateTs().getMinutes()<30)) {
						tmpDate2.setDate(tmpDate2.getDate()-1);
					}
	        		tmpDate2.setHours(8);
	        		tmpDate2.setMinutes(30);
	        		tmpDate2.setSeconds(0);
	        		dateDawn.put(playName, tmpDate2);
	        		tmpJ.put(playName, jn);
				}
        	}
        }
        for(Journal sss:tmpJ.values())
        {
        	allJournalList2.add(sss);
        }
        for (Journal journal:allJournalList2)
        {
            Date repairTs=journalMapper.queryRepairDate(journal.getJournalId());
              if (repairTs!=null)
              {
                  journal.setRepairTs(repairTs);
              }
        }
        return allJournalList2;
    }

    @SuppressWarnings("unchecked")
	@Override
    public  List searchDatail(String journalId){
        Journal myJournal = journalMapper.searchJournal(journalId);
        List<User> journalUnread = journalMapper.searchUnread(journalId);
        List<User> journalRead = journalMapper.searchRead(journalId);
        List result = new ArrayList();
        result.add(myJournal);
        result.add(journalUnread);
        result.add(journalRead);
        return result;
    }
    @Override
	public List<BigCustomer> getAllCustomers(String companyId,String userId)
    {
    	List<BigCustomer> bigCustomers=new ArrayList<BigCustomer>();
    	List<JournalCustomer> customerList=journalMapper.queryJournalCustomersByCompanyId(userId);
    	for(JournalCustomer customer:customerList){
    		try{
    			String customerId = customer.getCustomerId();
    			List<Department> departmentList=journalMapper.searchDepts(customerId, userId);
    			 //设置圈地状态
    	        setEnclosureStatus(departmentList);
    	        //添加联系人和三级机构
    	        setSubDeptAndContacts(departmentList);
    	      //按圈地状态分类dept
    	        EnumMap<EnclosureStatusEnum,List> deptMap = getDeptMap(departmentList);
    	        setEnclosureStatus(departmentList);
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
    	        }

    	        List<Department> rltList = new ArrayList<>();
    	        rltList.addAll(deptMap.get(EnclosureStatusEnum.MINE));
    	        //Debug(rltList);
    	        if (rltList.size()!=0) {
        			bigCustomers.add(new BigCustomer(customer.getName(), rltList));
				}
    		}catch(NullPointerException e){
                break;
            }
    	}
    	return bigCustomers;
    }
    
    
    private EnumMap<EnclosureStatusEnum, List> getDeptMap(List<Department> departmentList) {
        List<Department> mineDept = new ArrayList<>();
        List<Department> applyingDept = new ArrayList<>();
        List<Department> enclosureDept = new ArrayList<>();
        List<Department> normalDept = new ArrayList<>();
        for(Department department:departmentList){
        	if (exitPerson(department)) {
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
        }
        EnumMap<EnclosureStatusEnum,List> deptMap = new EnumMap<>(EnclosureStatusEnum.class);
        deptMap.put(EnclosureStatusEnum.MINE,mineDept);
        deptMap.put(EnclosureStatusEnum.APPLYING,applyingDept);
        deptMap.put(EnclosureStatusEnum.ENCLOSURE,enclosureDept);
        deptMap.put(EnclosureStatusEnum.NORMAL,normalDept);

        return deptMap;
    }
    private boolean exitPerson(Department d)
    {
    	boolean tag=false;
    	if (d.getContactsList()!=null&&d.getContactsList().size()!=0) {
    		tag=true;
		}
    	if (d.getDepartmentList()!=null) {
    		List<Department> rmdepartments=new ArrayList<Department>();
			for(Department department:d.getDepartmentList())
			{
		    	if (department.getContactsList()!=null&&department.getContactsList().size()!=0) {
		    		tag=true;
				}
		    	else
		    	{
		    		rmdepartments.add(department);
		    	}
			}
			for(Department as:rmdepartments)
			{
				d.getDepartmentList().remove(as);
			}
		}
    	return tag;
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
                    for (Contacts contacts : subContactsList) {
                        String totalName = "";
                        String deptName = department.getDeptName();
                        if (deptName != null && deptName != "") {
                            totalName = deptName + "-" + totalName;
                        }
                        String subDeptName = subDepartment.getDeptName();
                        if (subDeptName != null && subDeptName != "") {
                            totalName = totalName + "-" + subDeptName;
                        }
                        //合成联系人的部门院校名称
                        totalName = contacts.getCustomerName() + "-" + totalName + "-" + (contacts.getTypeName() != null? contacts.getTypeName() : "无") + "-" + contacts.getRealName()
                                + ":" + contacts.getContactsId() + ":" + contacts.getCustomerId();
                        contacts.setTotalName(totalName);
                    }
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
            for (Contacts contacts : contactsList) {
                String totalName = "";
                String deptName = department.getDeptName();
                if (deptName != null && deptName != "") {
                    totalName = deptName + "-" + totalName;
                }
                //合成联系人的部门院校名称
                totalName = contacts.getCustomerName() + "-" + totalName + (contacts.getTypeName() != null? contacts.getTypeName() : "无") + "-" + contacts.getRealName()
                        + ":" + contacts.getContactsId() + ":" + contacts.getCustomerId();
                contacts.setTotalName(totalName);
            }
            department.setContactsList(contactsList);
            contactsNum += contactsList.size();
        }
        //二级机构添加联系人总人数
        department.setContactNumber(contactsNum);
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
            try{
                String delayApplyStatus = deptMapper.delayApplyStatus(department.getDeptId());
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
            }catch (NullPointerException e){
                OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                openSeaWarning.setDeptId(department.getDeptId());
                openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
                openSeaWarning.setLastTimeFollow(enclosureApply.getUpdateTime());
                openSeaWarning.setDeptName(department.getDeptName());
                openSeaWarning.setLeftDays(String.valueOf(diffDays));
                openSeaWarning.setLeftHours(String.valueOf(diffHours));
                openSeaWarning.setDelayApplied(false);
                department.setOpenSeaWarning(openSeaWarning);
            }
        }
        if(diffMillis<0){
            department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
            deptMapper.setMyDeptExpired(department.getDeptId());
        }
    }
    @Override
    public List<JournalCustomer> getAllContacts(String companyId,String userId) {
        List<JournalCustomer> customerList = journalMapper.queryJournalCustomersByCompanyId(userId);
        List<Member> memberList = memberService.searchSubMemberList(userId);
        for(JournalCustomer customer:customerList){
            try{
                String customerId = customer.getCustomerId();
                List<String> deptList = journalMapper.queryDeptId(userId,customerId);
                for(Member member:memberList){
                    deptList.addAll(journalMapper.queryDeptId(member.getMemberId(),customerId));
                }
                List<Contacts> contactsList = new ArrayList<>();
                for(String deptId:deptList){
                    contactsList.addAll(journalMapper.queryContactsByCustomerId(deptId));
                }
                customer.setContactsGroup(contactsList);
            }catch(NullPointerException e){
                break;
            }
        }
        return customerList;
    }

    public Set<String> getAllSubordinatesUserId(String userId) {
        Set<String> userSet = new HashSet<>();
        Set<String> tSet = new HashSet<>();
        tSet.add(userId);

        while (!tSet.isEmpty()) {
            Set<String> childsSet = new HashSet<>();
            for (String si: tSet) {
                List<String> childs = journalMapper.querySubordinatesByUserId(si);
                for (String child: childs) {
                    if (!userSet.contains(child) && !tSet.contains(child)) {
                        childsSet.add(child);
                    }
                }
            }
            userSet.addAll(tSet);
            tSet = childsSet;
        }

        return userSet;
    }

    public Set<Project> getAllSubordinatesProjects(Set<String> userGroup) {
        // 这样实现目的是，防止以后查询projects的接口发生改变，发生碰撞
        Set<Project> projectSet = new HashSet<>();
        for (String userId: userGroup) {
            List<Project> projects = projectMapper.queryProjectsByUserId(userId);
            projectSet.addAll(projects);
        }
        return projectSet;
    }

    public Set<Opportunity> getAllSubordinatesOpportunity(Set<String> userGroup) {
        Set<Opportunity> opportunitySet = new HashSet<>();
        for (String userId: userGroup) {
            List<Opportunity> opportunities = projectMapper.queryOpportunitiesByUserId(userId);
            opportunitySet.addAll(opportunities);
        }
        return opportunitySet;
    }

}
