package com.xuebei.crm.opportunity;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.xuebei.crm.login.LoginController.SUCCESS_FLG;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("/opportunity")
public class OpportunityController {

    @Autowired
    private OpportunityMapper opportunityMapper;

    @RequestMapping("")
    public String oppo() {
        return "opportunity";
    }

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
        String id = UUIDGenerator.genUUID();
        opportunityMapper.insertProject(id,name,content,agent,person,background);
        gsonView.addStaticAttribute(SUCCESS_FLG, true);
        return gsonView;
    }



}
