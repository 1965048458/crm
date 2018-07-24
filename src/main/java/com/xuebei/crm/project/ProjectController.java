package com.xuebei.crm.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
