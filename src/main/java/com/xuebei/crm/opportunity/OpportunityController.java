package com.xuebei.crm.opportunity;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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



}
