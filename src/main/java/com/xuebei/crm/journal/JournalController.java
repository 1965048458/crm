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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            if (journal.getJournalId() != null) {
                // 更新日志内容
                journalService.modifyJournal(journal);
            } else {
                // 插入新日志
                journalService.createJournal(journal);
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

    @RequestMapping("/query")
    public GsonView getJournalInfoById(@RequestParam("journalId") String journalId,
                                       HttpServletRequest request) throws AuthenticationException {
        GsonView gsonView = new GsonView();
        System.out.println("this");
        String userId = (String)request.getSession().getAttribute("userId");
        Journal journal = journalService.queryJournalById(acquireUserId(request), journalId);
        List<User> colleagues = journalMapper.queryColleagues(acquireUserId(request));
        String companyId = companyMapper.queryCompanyIdByUserId(acquireUserId(request));
        List<JournalCustomer> customerList = journalService.getAllContacts(companyId,userId);
        Set<String> userGroup = journalService.getAllSubordinatesUserId(acquireUserId(request));
//        Set<Project> projectList = journalService.getAllSubordinatesProjects(userGroup);
        Set<Opportunity> opportunitySet = journalService.getAllSubordinatesOpportunity(userGroup);
        gsonView.addStaticAttribute("journal", journal);
        gsonView.addStaticAttribute("colleagues", colleagues);
        gsonView.addStaticAttribute("customer", customerList);
//        gsonView.addStaticAttribute("projects", projectList);
        gsonView.addStaticAttribute("opportunities", opportunitySet);
        return gsonView;
    }

    @RequestMapping("/action/getColleagueList")
    public GsonView getColleagueList(HttpServletRequest request) {
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
                              @RequestParam(value="journalId", required = false) String journalId,
                              ModelMap modelMap, HttpServletRequest request) {

        if (journalId == null && type == null) {
            return "error/500";
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


        return "editJournal";
    }

    @RequestMapping("/toList")
    public String toJournalList(HttpServletRequest request, ModelMap modelMap) {
    	
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
