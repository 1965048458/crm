package com.xuebei.crm.project;

import com.google.gson.Gson;
import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.company.CompanyUser;
import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.CustomerService;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.OpportunityService;
import com.xuebei.crm.opportunity.Support;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.xuebei.crm.login.LoginController.SUCCESS_FLG;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JournalService journalService;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/projectDetail")
    public String projectDetail(@RequestParam("projectId") String projectId,HttpServletRequest request,
                                ModelMap modelMap) {
        String userId = (String) request.getSession().getAttribute("userId");
        ProjectDetail projectDetail = projectService.getProjectDetail(projectId);
        String userType = companyMapper.queryUserType(userId);
        projectDetail.setIsAdmin(userType);
        modelMap.addAttribute("projectDetail", projectDetail);

        if (projectDetail == null) {
            return "error/404";
        }

        return "projectDetail";
    }

    @RequestMapping("/modifyProject")
    public String modifyProject(@RequestParam("projectId") String projectId,
                                ModelMap modelMap) {
        modelMap.addAttribute("projectId", projectId);
        return "modifyProject";
    }


    @RequestMapping("/new")
    public String newProject() {
        return "newProject";
    }

    @RequestMapping("/mission")
    public String mission(){
        return "mission";
    }

    /**
     * 新建项目
     * @param project
     * @return
     */
    @RequestMapping("/add")
    public GsonView addProject(@RequestBody Project project,
                               HttpServletRequest request) {
        HttpSession session =  request.getSession();
        String userId = (String) session.getAttribute("userId");
        GsonView gsonView = new GsonView();
        project.setUserId(userId);
        projectService.addProject(project);
        opportunityService.addOpportunityContact(project.getProjectId(), project.getContactId());

        gsonView.addStaticAttribute(SUCCESS_FLG, true);
        return gsonView;
    }

    @RequestMapping("apply")
    public String projectSupport() {
        return "applyProjectSupport";
    }

    @RequestMapping("/projectList")
    public String project(){
        return "projectList";
    }

    @RequestMapping("/searchProject")
    public GsonView searchProject(ProjectSearchParam param,
                                  HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        param.setUserId(userId);

        GsonView gsonView = new GsonView();
        String userType = companyMapper.queryUserType(userId);
        param.setIsAdmin(userType);

        Set<String> childs = journalService.getAllSubordinatesUserId(param.getUserId());

        if (param.getSubUsers() != null && !param.getSubUsers().equals("") ){
            String[] subUser = param.getSubUsers().split(",");
            param.setSubMember(subUser);
        }else{
            //我及下属
            String[] childsArray = new String[childs.size()];
            childs.toArray(childsArray);
            param.setSubMember(childsArray);
        }

        if (param.getCreator() != null && param.getCreator().equals("sub")){
            //删除自己的ID
            childs.remove(userId);
            String[] childsArray = new String[childs.size()];
            childs.toArray(childsArray);
            param.setSubMember(childsArray);
        }

        List<Project> projectList = projectService.searchProject(param);
        Iterator<Project> it = projectList.iterator();
        while (it.hasNext()){
            Project project = it.next();
            if (project.getLeaderId() == null){
                project.setLeaderName("无");
            }else if (project.getLeaderId().equals(userId)){
                project.setLeaderName("我");
            }
            if (project.getDeadLine() != null){
                project.setStrDeadLine(project.getDeadLine());
            }
            Contacts contact = customerService.queryOpportunityDetail(project.getProjectId().toString());
            if (contact == null){
                continue;
            }
            project.setCustomerName(contact.getCustomerName() +"-"+ contact.getDepartmentName());
        }
        if (userType.equals("ADMIN")){
            gsonView.addStaticAttribute("ADMIN", true);
        }else{
            gsonView.addStaticAttribute("ADMIN", false);
        }
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("projectList", projectList);
        return gsonView;
    }

    @RequestMapping("/applyStart")
    public String startProject(@RequestParam(value = "projectId") Integer projectId,
                               ModelMap modelMap){
        String projectName = projectService.queryOpportunityNameByOpportunityId(projectId);
        modelMap.addAttribute("projectId", projectId);
        modelMap.addAttribute("projectName", projectName);
        return "applyStartProject";
    }

    @RequestMapping("/projectCheck")
    public String checkProject(@RequestParam(value = "projectId") Integer projectId,
                               ModelMap modelMap){
        String projectName = projectService.queryOpportunityNameByOpportunityId(projectId);
        ProjectDetail projectDetail = projectService.getProjectDetail(projectId.toString());
        modelMap.addAttribute("projectDetail", projectDetail);
        modelMap.addAttribute("projectId", projectId);
        modelMap.addAttribute("projectName", projectName);
        return "checkProject";
    }

    @RequestMapping("/refuseProject")
    public GsonView refuse(@RequestParam("projectId") Integer projectId){
        GsonView gsonView = new GsonView();
        projectService.refuseProject(projectId);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/passProject")
    public GsonView pass(@RequestParam("projectId") Integer projectId){
        GsonView gsonView = new GsonView();
        projectService.passProject(projectId);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/assignLeader")
    public GsonView assignLeader(@RequestParam("projectId") Integer projectId, @RequestParam("leader") String leader){
        GsonView gsonView = new GsonView();
        projectService.assignLeader(projectId, leader);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/getContractInfo")
    public GsonView getContractInfo(@RequestParam("projectId") Integer projectId,
                                    HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        ProjectStart projectStart = projectService.getProjectStart(projectId, userId);
        Contract contract = projectService.getContract(projectId);
        List<Refund> refunds = projectService.getRefunds(projectId);
        if (projectStart != null){
            projectStart.setContract(contract);
            projectStart.setRefunds(refunds);
        }
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("projectStart", projectStart);
        return gsonView;
    }

    @RequestMapping("/submitProject")
    public GsonView submitProject(@RequestBody ProjectStart projectStart,
                                  HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        projectStart.setUserId(userId);
        GsonView gsonView = new GsonView();
        projectService.startProject(projectStart);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/missionList")
    public GsonView missionList(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        boolean admin = false;
        String userType = companyMapper.queryUserType(userId);
        if (userType.equals("ADMIN")) {
            admin = true;
        }
        List<Member> members = memberService.searchSubMemberList(userId);
        List<String> subUserId = new ArrayList<>();
        Iterator<Member> it = members.iterator();
        while (it.hasNext()) {
            subUserId.add(it.next().getMemberId());
        }
        if(subUserId.isEmpty()){
            subUserId.add("000");
        }
        List<ProjectDetail> unfinish = projectService.queryMissionUn(userId, admin,subUserId);
        List<ProjectDetail> finish = projectService.queryMission(userId, admin,subUserId);
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("unfinish", unfinish);
        gsonView.addStaticAttribute("finish", finish);
        return gsonView;
    }

    @RequestMapping("/missionDetail")
    public GsonView missionDetail(@RequestParam("supportId") Integer supportId,
                                  HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        ProjectDetail support = projectService.queryMissionDetail(supportId);
        List<Support> information = projectService.querySupportInformation(supportId);
        boolean admin = false;
        boolean operator = false;
        if (support.support.getLeader() != null) {
            if (userId.equals(support.support.getLeader())) {
                operator = true;
            }

        }
        String userType = companyMapper.queryUserType(userId);
        if (userType.equals("ADMIN")) {
            admin = true;
        }
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("support", support);
        gsonView.addStaticAttribute("information", information);
        gsonView.addStaticAttribute("admin", admin);
        gsonView.addStaticAttribute("operator", operator);
        return gsonView;
    }

    @RequestMapping("/addProgressInform")
    public GsonView addProgressInform(@RequestParam("supportId") Integer supportId,
                                      @RequestParam("progress") String progress,
                                      HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        projectService.insertProgressInform(supportId, userId, progress);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }


    @RequestMapping("/updateSupportProgress")
    public GsonView updateSupportProgress(@RequestParam("supportId") Integer supportId,
                                          @RequestParam("progress") Integer progress,
                                          HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        projectService.updateSupportProgress(userId, supportId, progress);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/queryCompanyUser")
    public GsonView queryCompanyUser(@RequestParam("keyword")String keyword,
                                     HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        List<CompanyUser> companyUsers = projectService.queryCompanyUser(userId,keyword);
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("companyUsers", companyUsers);
        return gsonView;
    }

    @RequestMapping("/chooseExecutor")
    public GsonView chooseExecutor(@RequestParam("supportId") Integer supportId,
                                   @RequestParam("leaderId") String leaderId,
                                   HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        projectService.setSupportLeader(userId, supportId, leaderId);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("modify/projectDetail")
    public  GsonView projectDetail (@RequestParam("projectId")String projectId,
                                    HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        ProjectDetail project =projectService.getProjectDetail(projectId);
        gsonView.addStaticAttribute("project",project);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("modification")
    public GsonView modificationOpportunity(@RequestBody Opportunity opportunity,
                                            HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        opportunity.setUserId(userId);
        projectService.modifyProject(opportunity);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }
}
