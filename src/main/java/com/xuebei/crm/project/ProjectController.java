package com.xuebei.crm.project;

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
import java.util.List;

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
     * @param name
     * @param content
     * @param agent
     * @param person
     * @param background
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
    public GsonView searchProject(@RequestBody ProjectSearchParam param,
                                  HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        List<Project> projectList = projectService.searchProject(param);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("projectList", projectList);
        return gsonView;
    }

}
