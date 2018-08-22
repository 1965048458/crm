package com.xuebei.crm.project;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.CustomerService;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.opportunity.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.xuebei.crm.login.LoginController.SUCCESS_FLG;

/**
 * Created by Administrator on 2018/7/24.
 */
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
    private JournalService journalService;

    @RequestMapping("/projectDetail")
    public String detail(){
        return "projectDetail";
    }


    @RequestMapping("/new")
    public String newProject() {
        return "newProject";
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

        if (param.getSubUsers() != null && !param.getSubUsers().equals("") ){
            String[] subUser = param.getSubUsers().split(",");
            param.setSubMember(subUser);
        }

        if (param.getCreator() != null && !param.getCreator().equals("")){
            if (param.getCreator().equals("sub")){
                Set<String> childs = journalService.getAllSubordinatesUserId(param.getUserId());
                //删除自己的ID
                childs.remove(userId);
                String[] childsArray = new String[childs.size()];
                childs.toArray(childsArray);
                param.setSubMember(childsArray);
            }
        }

        List<Project> projectList = projectService.searchProject(param);
        Iterator<Project> it = projectList.iterator();
        while (it.hasNext()){
            Project project = it.next();
            if (project.getLeader() == null){
                project.setLeader("无");
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
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("projectList", projectList);
        return gsonView;
    }

    @RequestMapping("/applyStart")
    public String startProject(){
        return "applyStartProject";
    }

}
