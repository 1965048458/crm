package com.xuebei.crm.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.xuebei.crm.login.LoginController.SUCCESS_FLG;

/**
 * Created by Administrator on 2018/7/24.
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    //@Autowired
    //private ProjectService projectService;

    @RequestMapping("/projectDetail")
    public String detail(){
        return "projectDetail";
    }



    @Autowired
    private ProjectMapper projectMapper;

    @RequestMapping("new")
    public String newProject() {
        return "newProject";
    }

    @RequestMapping("add")
    public GsonView addProject(@RequestParam("name") String name,
                               @RequestParam("content") String content,
                               @RequestParam("agent") String agent,
                               @RequestParam("person") String person,
                               @RequestParam("background") String background ) {
        GsonView gsonView = new GsonView();
        Project project = new Project();
        project.setProjectId(UUIDGenerator.genUUID());
        project.setProjectNo("123");
        project.setProjectNm(name);
        project.setBackGround(background);
        project.setProjectContent(content);
        project.setStatus("1");
     //   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        project.setDeadLine(new Date());
        project.setPriority(1);
        project.setAgent(agent);
        project.setOpportunityId("123");
        project.setCustomerId("123");
        project.setUserId("001c52e79ee0484ca8158e926b5c05a3");
        projectMapper.insertProject(project);
        gsonView.addStaticAttribute(SUCCESS_FLG, true);
        return gsonView;
    }

    @RequestMapping("apply")
    public String projectSupport() {
        return "applyProjectSupport";
    }

}
