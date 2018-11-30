package com.xuebei.crm.journal;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.customer.BigCustomer;
import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.Hoilday;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private CustomerMapper customerMapper;

    private String acquireUserId(HttpServletRequest request) throws AuthenticationException {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            throw new AuthenticationException("会话已过期");
        }
        return userId;
    }

    @RequestMapping("/action/editSubmit")
    public GsonView editJournal(@RequestBody Journal journal,
                                HttpServletRequest request) {
        try {
            journal.setUserId(acquireUserId(request));
        } catch (AuthenticationException e) {
            return GsonView.createErrorView(e.getMessage());
        }
     
        try {
/*            if (journal.getJournalId() != null) {
                // 更新日志内容
                journalService.modifyJournal(journal);
            } else*/ {
                // 插入新日志
                journalService.createJournal(journal);
                if (journal.getCreateTs()!=null)
                {
                    journalMapper.insertRepairDate(journal.getJournalId());
                }
                List<String> deptIdList = journalMapper.queryDeptIdByJournalId(journal.getJournalId());
                String userId = (String)request.getSession().getAttribute("userId");
                for(String deptId:deptIdList){
                    customerMapper.updateEnclosureApplyEndTs(userId,deptId);
                }

            }
        } catch (InformationNotCompleteException | AuthenticationException e) {
            GsonView failedView = new GsonView();
            failedView.addStaticAttribute("successFlg", true);
            failedView.addStaticAttribute("errMsg", e.getMessage());
            return failedView;
        }

        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/delete")
    public GsonView deleteJournal(@RequestParam("journalId") String journalId,
                                  HttpServletRequest request) {
        try {
            journalService.deleteJournalById(acquireUserId(request), journalId);
        } catch (AuthenticationException e) {
            GsonView failedView = new GsonView();
            failedView.addStaticAttribute("successFlg", false);
            failedView.addStaticAttribute("errMsg", e.getMessage());
            return failedView;
        }

        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    /**
     * 接收人删除日志
     * 要检查：日志存在且日志不是草稿，日志的接收人有申请删除的用户
     */
    @RequestMapping("/receiverDelete")
    public GsonView receiverDeleteJournal(@RequestParam("journalId") String journalId,
                                          HttpServletRequest request) {
        try {
            Journal journal = journalMapper.queryJournalById(journalId);
            if (journal == null || !journal.getHasSubmitted()) {
                return GsonView.createErrorView("日志不存在,或草稿");
            }
            Integer line = journalMapper.receiverDeleteJournal(journalId, acquireUserId(request));
            if (line != 1) {
                return GsonView.createErrorView("该日志接收人没有改用户");
            }
        } catch (AuthenticationException e) {
            return GsonView.createErrorView(e.getMessage());
        }
        return GsonView.createSuccessView();
    }
    @RequestMapping("/repair")
    public GsonView getRepairJournal(HttpServletRequest request)
    {
        GsonView gsonView = new GsonView();
        String userId = (String)request.getSession().getAttribute("userId");
        JournalSearchParam param=new JournalSearchParam();
        param.setUserId(userId);
        Date tmpDate=new Date(); Date tmpDate2=new Date();
        if (tmpDate.getHours()<8||(tmpDate.getHours()<9&&tmpDate.getMinutes()<30)) {
            tmpDate.setDate(tmpDate.getDate()-1);
        }
        tmpDate.setHours(8);
        tmpDate.setMinutes(30);
        tmpDate.setSeconds(0);
        tmpDate2.setDate(tmpDate2.getDate()-3);
        tmpDate2.setHours(8);
        tmpDate2.setMinutes(30);
        tmpDate2.setSeconds(0);
        param.setStartTime(tmpDate2);
        param.setEndTime(tmpDate);
        List<Journal> testJ=journalMapper.searchMyJournal(param);
        //testJ.sort((journal1, journal2)-> journal1.getCreateTs().after(journal2.getCreateTs())?1:-1);
        Date firstD=new Date(tmpDate2.getTime()+86400000);
        Date secondD=new Date(firstD.getTime()+86400000);
        List<ShowDate> showDate=new ArrayList<>();
        boolean tagF=false;
        boolean tagS=false;
        boolean tagT=false;
        for (Journal jj:testJ)
        {
            if (!tagF&&jj.getCreateTs().before(firstD))
            {
                tagF=true;
            }
            if(!tagS&&jj.getCreateTs().after(firstD)&&jj.getCreateTs().before(secondD))
            {
                tagS=true;
            }
            if (!tagT&&jj.getCreateTs().after(secondD))
            {
                tagT=true;
            }
        }
        try {
            if (!tagF && !Hoilday.isHoliday(tmpDate2)) {
                showDate.add(new ShowDate(tmpDate2));
            }
            if (!tagS && !Hoilday.isHoliday(firstD)) {
                showDate.add(new ShowDate(firstD));
            }
            if (!tagT && !Hoilday.isHoliday(secondD)) {
                showDate.add(new ShowDate(secondD));
            }
            gsonView.addStaticAttribute("repairDate", showDate);
        }
        catch (Exception e)
        {

        }
        String type=companyMapper.queryUserType(userId);
        if (type.equals("ADMIN"))
        {
            gsonView.addStaticAttribute("isAdmin",true);
        }
        else
        {
            gsonView.addStaticAttribute("isAdmin",false);
        }
        return  gsonView;
    }

    @RequestMapping("/query")
    public GsonView getJournalInfoById(@RequestParam("journalId") String journalId,
                                       HttpServletRequest request) throws AuthenticationException {
        GsonView gsonView = new GsonView();
        String userId = (String)request.getSession().getAttribute("userId");
        Journal journal = journalService.queryJournalById(acquireUserId(request), journalId);
        List<User> colleagues = journalMapper.queryColleagues(acquireUserId(request));
        String companyId = companyMapper.queryCompanyIdByUserId(acquireUserId(request));
        //List<JournalCustomer> customerList = journalService.getAllContacts(companyId,userId);
        List<BigCustomer> bigCu=journalService.getAllCustomers(companyId,userId);
        Set<String> userGroup = journalService.getAllSubordinatesUserId(acquireUserId(request));
//        Set<Project> projectList = journalService.getAllSubordinatesProjects(userGroup);
        Set<Opportunity> opportunitySet = journalService.getAllSubordinatesOpportunity(userGroup);
        gsonView.addStaticAttribute("journal", journal);
        gsonView.addStaticAttribute("colleagues", colleagues);
        gsonView.addStaticAttribute("customer", bigCu);
/*        for (JournalCustomer dd:customerList)
        {
            for (Contacts cc:dd.getContactsGroup())
            {
                System.out.println(cc.getRealName());
            }
        }*/
//        gsonView.addStaticAttribute("projects", projectList);
        gsonView.addStaticAttribute("opportunities", opportunitySet);
        return gsonView;
    }

    @RequestMapping("/action/getColleagueList")
    public GsonView getColleagueList(HttpServletRequest request,@RequestParam(value="repairDate") String repairDate) {
        List<BigCustomer> customerList;
        List<User> colleagues;
        Set<Opportunity> opportunitySet;
        String userId = (String)request.getSession().getAttribute("userId");
        try {
            colleagues = journalMapper.queryColleagues(acquireUserId(request));
            String companyId = companyMapper.queryCompanyIdByUserId(acquireUserId(request));
            customerList = journalService.getAllCustomers(companyId,userId);
            Set<String> userGroup = journalService.getAllSubordinatesUserId(acquireUserId(request));
            opportunitySet = journalService.getAllSubordinatesOpportunity(userGroup);
        } catch (AuthenticationException e) {
            return GsonView.createErrorView(e.getMessage());
        }
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("colleagues", colleagues);
        gsonView.addStaticAttribute("customer", customerList);
        gsonView.addStaticAttribute("opportunities", opportunitySet);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if (repairDate!=null&&!repairDate.equals("")) {
            try {
                gsonView.addStaticAttribute("createTs", sdf.parse(repairDate));
            } catch (Exception e) {
                gsonView.addStaticAttribute("createTs", null);
            }
        }
        return gsonView;
    }
    
    @RequestMapping("/searchCompany")
    public GsonView searchCompany(@RequestParam("searchWord") String keyword,HttpServletRequest request) {
        List<BigCustomer> customerList;       
        String userId = (String)request.getSession().getAttribute("userId");
        try {
            String companyId = companyMapper.queryCompanyIdByUserId(acquireUserId(request));
            customerList = journalService.getAllCustomers(companyId,userId);
        } catch (AuthenticationException e) {
            return GsonView.createErrorView(e.getMessage());
        }
        for(int i=0;i<customerList.size();i++)
        {
        	if (customerList.get(i).getRealname().indexOf(keyword)>-1) {
				
			}
        	else
        	{
        		if (!recursionCompany(customerList.get(i).getDepts(),keyword)) {
        			customerList.remove(i);
        			i--;
				}
        	}
        }
        GsonView gsonView = new GsonView();;
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("ccustomer", customerList);
        return gsonView;
    }
   
    
    @SuppressWarnings("unused")
   	private boolean recursionCompany(List<Department> departmentList,String keyword)
       {
       	boolean tagFlag=false;
       	List<Department> listInt=new ArrayList<Department>();
       	for(Department department:departmentList)
       	{
       		boolean tagcFlag=false;
       		if (department.getDeptName().indexOf(keyword)>-1) {
       			tagcFlag=true;
   			}
       		else
       		{
   				if (department.getDepartmentList() != null && department.getDepartmentList().size() != 0) {
   					if (recursionCompany(department.getDepartmentList(), keyword)) {
   						tagcFlag = true;
   					}
   				}
   				if (department.getContactsList() != null && department.getContactsList().size() != 0) {
   					List<Contacts> listContacts=new ArrayList<Contacts>();
   					for (Contacts contacts : department.getContactsList()) {
   						if (contacts.getRealName().indexOf(keyword) > -1) {
   							tagcFlag = true;
   						}
   						else if (contacts.getTypeName()!=null&&contacts.getTypeName().indexOf(keyword)>-1) {
   								tagcFlag=true;								
   						}
   						else {
   							listContacts.add(contacts);
   						}
   					}
   					for(Contacts c:listContacts)
   					{
   						department.getContactsList().remove(c);
   					}
   				}
       		}
       		if (tagcFlag) {
   				tagFlag=true;
   			}
       		else
       		{
       			listInt.add(department);
       		}
       	}
       	for(Department k:listInt)
       	{
       		departmentList.remove(k);
       	}
       	return tagFlag;
       }
    @RequestMapping("/edit")
    public String editJournalPage(@RequestParam(value="type", required = false) String type,
                              @RequestParam(value="journalId", required = false) String journalId,@RequestParam(value="repairDate", required = false) String repairDate,
                              ModelMap modelMap, HttpServletRequest request) {

        if (journalId == null && type == null) {
            return "error/500";
        }
        if (journalId==null&&repairDate==null) {
            try {
                JournalSearchParam param=new JournalSearchParam();
                param.setUserId(acquireUserId(request));
                Date tmpDate=new Date();
                if (tmpDate.getHours()<8||(tmpDate.getHours()<9&&tmpDate.getMinutes()<30)) {
                    tmpDate.setDate(tmpDate.getDate()-1);
                }
                tmpDate.setHours(8);
                tmpDate.setMinutes(30);
                tmpDate.setSeconds(0);
                Date tmpDate2=new Date(tmpDate.getTime()+86400000);
                param.setStartTime(tmpDate);
                param.setEndTime(tmpDate2);
                List<Journal> testJ=journalMapper.searchMyJournal(param);
                if (testJ.size()>0)
                {
                    testJ.sort((journal1, journal2)-> journal1.getCreateTs().before(journal2.getCreateTs())?1:-1);
                    journalId=testJ.get(0).getJournalId();
                }
            } catch (AuthenticationException e) {
                return "error/500";
            }
        }

        try {
            if (journalId == null) {
                List<Journal> journalList = journalMapper.findJournalDraft(acquireUserId(request));
                Journal journal = null;
                if (journalList.size() > 1) {
                    int size = journalList.size();
                    for (int i = 0; i < size-1; i++) {
                        String id = journalList.get(i).getJournalId();
                        journalService.deleteJournalById(acquireUserId(request), id);
                    }
                }
                if (journalList.size() > 0)
                    journal = journalList.get(journalList.size() - 1);
                if (journal != null) {
                    modelMap.addAttribute("journalType", journal.getType());
                    modelMap.addAttribute("journalId", journal.getJournalId());
                } else {
                    modelMap.addAttribute("journalType", type);
                    modelMap.addAttribute("journalId", 0);
                }
            } else {
                Journal journal = journalService.queryJournalById(acquireUserId(request), journalId);
                if (journal == null) {
                    return "error/404";
                }
                modelMap.addAttribute("journalType", journal.getType());
                modelMap.addAttribute("journalId", journal.getJournalId());
            }
        } catch (AuthenticationException e) {
            return "error/500";
        }
        if (repairDate!=null)
            modelMap.addAttribute("repairDate", repairDate);


        return "editJournal";
    }

    @RequestMapping("/manager")
    public  GsonView manager(HttpServletRequest request)
    {
        GsonView gsonView=new GsonView();
        try {
            String userId = acquireUserId(request);
            List<ManageJournal> reportJ=journalService.getJournalState(userId);
            gsonView.addStaticAttribute("manager",reportJ);
            int repairCount=0;
            int loseCount=0;
            for (ManageJournal manageJournal:reportJ)
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
            float delay=(float)request.getSession().getAttribute("delay");
            float miss=(float)request.getSession().getAttribute("miss");
            gsonView.addStaticAttribute("repairC",repairCount);
            gsonView.addStaticAttribute("loseC",loseCount);
            gsonView.addStaticAttribute("delay",delay);
            gsonView.addStaticAttribute("miss",miss);
            gsonView.addStaticAttribute("totalC",loseCount*miss+repairCount*delay);
        }
        catch (Exception e)
        {

        }
        return gsonView;
    }
    @RequestMapping("/money")
    public GsonView money(HttpServletRequest request)
    {
        GsonView gsonView=new GsonView();
        float delay=(float)request.getSession().getAttribute("delay");
        float miss=(float)request.getSession().getAttribute("miss");
        gsonView.addStaticAttribute("delay",delay);
        gsonView.addStaticAttribute("miss",miss);
        return  gsonView;
    }

    @RequestMapping("/changemoney")
    public GsonView changemoney(HttpServletRequest request,@RequestParam("delay") float delay,@RequestParam("miss") float miss)
    {
        request.getSession().setAttribute("delay",delay);
        request.getSession().setAttribute("miss",miss);
        String compandId=(String)request.getSession().getAttribute("companyId");
        companyMapper.changemoney(delay,miss,compandId);
        return new GsonView();
    }



    @RequestMapping("/info")
    public void info(HttpServletRequest request, HttpServletResponse response,@RequestParam("index") int index){
        String userId = (String) request.getSession().getAttribute("userId");
        float delay=(float)request.getSession().getAttribute("delay");
        float miss=(float)request.getSession().getAttribute("miss");
        if (userId==null)
                return;
        List<FollowJournal> followJournals = journalService.getJournalFollow(userId,index,delay,miss);
        List<String> map=new ArrayList<>();
        map.add("姓名");
        map.add("本月补交次数/"+delay+"元");
        map.add("本月漏交次数/"+miss+"元");
        map.add("本月罚款总额");
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("日志明细表");
        int j=1;
        Row row = sheet.createRow(0);
        int i = 0;
        for(String key : map){
            Cell cell = row.createCell(i);
            cell.setCellValue( key);
            i++;
        }
        for (FollowJournal followJournal :followJournals)
        {
            Row tmpRow = sheet.createRow(j);
            Cell cell0 = tmpRow.createCell(0);
            cell0.setCellValue(followJournal.getName());
            Cell cell1 = tmpRow.createCell(1);
            cell1.setCellValue(followJournal.getDelayCount());
            Cell cell2 = tmpRow.createCell(2);
            cell2.setCellValue(followJournal.getDropCount());
            Cell cell3 = tmpRow.createCell(3);
            cell3.setCellValue(followJournal.getToalMoney());
            j++;
        }
        OutputStream fos = null;
        try {
            fos = response.getOutputStream();
            String userAgent = request.getHeader("USER-AGENT");
            String fileName = (new Date().getMonth()-index+1)+"月日志明细表";
            try {
                if(StringUtils.contains(userAgent, "Mozilla")){
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                }else {
                    fileName = URLEncoder.encode(fileName, "utf8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置contentType为excel格式
            response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/follow")
    public  GsonView follow(HttpServletRequest request)
    {
        GsonView gsonView=new GsonView();
        try {
            String userId = acquireUserId(request);
            float delay=(float)request.getSession().getAttribute("delay");
            float miss=(float)request.getSession().getAttribute("miss");
            List<FollowJournal> followJournals=journalService.getJournalFollow(userId,delay,miss);
            List<JournalExcel> journalExcels=new ArrayList<>();
            int month=new Date().getMonth();
            journalExcels.add(new JournalExcel(month,1));
            journalExcels.add(new JournalExcel(month,2));
            journalExcels.add(new JournalExcel(month,3));
            gsonView.addStaticAttribute("followJournal",followJournals);
            gsonView.addStaticAttribute("journalExcel",journalExcels);
            gsonView.addStaticAttribute("delay",delay);
            gsonView.addStaticAttribute("miss",miss);
        }
        catch (Exception e)
        {

        }
        return  gsonView;
    }
    @RequestMapping("/toList")
    public String toJournalList(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("isAdmin",false);
        return "journalList";
    }
    @RequestMapping("/toList2")
    public String toJournalList2(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("isAdmin",true);
        return "journalList";
    }
    @RequestMapping("/tooppor")
    public String toOppor(HttpServletRequest request, ModelMap modelMap) {
    	modelMap.addAttribute("tagflag", 1);
        return "opportunityDetail";
    }

    @RequestMapping("/list")
    public GsonView list(JournalSearchParam param, HttpServletRequest request){

        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        param.setUserId(userId);
        List<Journal> journals =journalService.searchJournal(param);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("journalList", journals);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }
 
    @RequestMapping("/userJournal")
    public GsonView userJournal(String journalId)
    {
         GsonView gsonView = new GsonView();
         gsonView.addStaticAttribute("journalLists", journalService.searchJournal2(journalId));
         gsonView.addStaticAttribute("successFlg", true);
         return gsonView;
    }
    
    @RequestMapping("/detail")
    public GsonView detail(String journalId){
        List journal = journalService.searchDatail(journalId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("journal", journal.get(0));
        gsonView.addStaticAttribute("unread", journal.get(1));
        gsonView.addStaticAttribute("read", journal.get(2));
        return gsonView;
    }

    @RequestMapping("/searchLog")
    public String searchJournal(){
        return "selectLog";
    }

    /**
     * 增加日志的补丁
     * 做的事：检查该日志 ID 是否为该用户所有
     * @param journalId 要增加补丁的日志ID
     * @param content 增加的补丁内容
     * @return
     */
    @RequestMapping("/action/journalAttachment")
    public GsonView journalAttachment(@RequestParam("journalId") String journalId,
                                      @RequestParam("content") String content,
                                      HttpServletRequest request) {
        try {
            boolean auth = journalMapper.userHasJournal(acquireUserId(request), journalId);
            if (!auth) {
                return GsonView.createErrorView("用户不拥有此日志");
            }
            journalMapper.insertJournalPatch(journalId, content);
        } catch (AuthenticationException e) {
            return GsonView.createErrorView(e.getMessage());
        }

        return GsonView.createSuccessView();
    }

    @RequestMapping("subMemberList")
    public GsonView subMemberList(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        GsonView gsonView = new GsonView();
        List<Member> subMemberList = memberService.searchSubMemberList(userId);//"0022287b3f7a404d8fcca44aa76842c2"
        gsonView.addStaticAttribute("successFlg",true);
        gsonView.addStaticAttribute("subMemberList",subMemberList);
        return gsonView;
    }

    @RequestMapping("/customerAndProjects")
    public GsonView getCustomerProjects(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        String companyId = companyMapper.queryCompanyIdByUserId(userId);
        Set<String> userSet = journalService.getAllSubordinatesUserId(userId);
        GsonView gsonView = new GsonView();
        List<JournalCustomer> customers = journalService.getAllContacts(companyId,userId);
        Set<Opportunity> opportunities = journalService.getAllSubordinatesOpportunity(userSet);
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("customers", customers);
        gsonView.addStaticAttribute("opportunities", opportunities);
        return gsonView;
    }

}
