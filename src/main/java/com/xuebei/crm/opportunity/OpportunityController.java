package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.xuebei.crm.login.LoginController.SUCCESS_FLG;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("/opportunity")
public class OpportunityController {

    @Autowired
    private OpportunityService opportunityService;

    @RequestMapping("")
    public String opportunity() {
        return "opportunity";
    }

    @RequestMapping("newSale")
    public String newSale(){
        return "newSale";
    }

    @RequestMapping("getCustomers")
    public GsonView getCustomers(HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        List<Customer> customerList = opportunityService.getMyCustomers("57259d54f9994209a813e8ad2b297b3a");
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("customerList", customerList);
        return gsonView;
    }

    @RequestMapping("addSale")
    public GsonView addSale(@RequestBody Opportunity opportunity){
        opportunityService.addSale(opportunity);

        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

}
